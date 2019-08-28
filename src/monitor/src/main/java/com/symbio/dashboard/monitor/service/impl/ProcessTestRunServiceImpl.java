package com.symbio.dashboard.monitor.service.impl;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.business.ReportJsonUtil;
import com.symbio.dashboard.business.TestRunFactory;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.constant.ProjectConst;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.dto.FilePathDTO;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.*;
import com.symbio.dashboard.model.json.ReportJson;
import com.symbio.dashboard.model.json.TestMethods;
import com.symbio.dashboard.service.*;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class ProcessTestRunServiceImpl {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ZipProcessServiceImpl zipService;

    @Autowired
    private AutomationReportFileServiceImpl reportFileService;

    @Autowired
    private TestRunServiceImpl testRunService;

    @Autowired
    private TestResultService testResultSevice;

    public Result<String> processTestRun(ParseResultSummary prs, ReportJson jsonReport) {
        String functionName = "ProcessTestRunServiceImpl.processTestRun()";

        Result<String> retResult = new Result<>();

        // Step1 - Get JobHistoryMainInfo
        Result<JenkinsJobHistoryMain> resultLabelInfo = reportFileService.getJobInfoFromZipName(prs.getFileName());
        if (resultLabelInfo.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(functionName, resultLabelInfo));
            return new Result(resultLabelInfo);
        }

        JenkinsJobHistoryMain jjHMain = resultLabelInfo.getCd();
        Integer nTestSetId = jjHMain.getTestSetId();

        String strTestCaseId;
        String locale;
        TestRun testRun = null;

        // Step2 - Parse TestMethod
        List<TestMethods> listTestMethod = jsonReport.getTestMethods();

        Integer nTestMethodCount = listTestMethod.size();
        boolean bOnlyOneTestRun = (nTestMethodCount == 1);
        Integer nUpdateTestResultCount = 0;
        FilePathDTO dtoFilePathInfo = new FilePathDTO();
        for (TestMethods testMethod : listTestMethod) {
            // Get TestCaseId and Local info for each Test Run
            strTestCaseId = getTestCaseId(testMethod);
            locale = getLocale(testMethod);

            testRun = testRunService.getTestRunByReportFileInfo(nTestSetId, strTestCaseId, locale);
            if (CommonUtil.isEmpty(testRun)) {
                String strMsg = String.format("Could not find Test Run data. TestSetId = %d, TestCaseId = %s, locale = %s",
                        nTestSetId, strTestCaseId, locale);
                log.warn(strMsg);
                if (bOnlyOneTestRun) {
                    return commonDao.getResult("500107", nTestSetId, strTestCaseId, locale);
                }
            } else {
                String strRunStatus = testMethod.getStatus();
                Result<Boolean> retNeedUpdateTR = isUpdateTestRunValidation(testRun, strRunStatus);
                if (retNeedUpdateTR.hasError()) {
                    log.warn(ErrorConst.getWarningLogMsg(functionName, retNeedUpdateTR));
                    if (bOnlyOneTestRun) {
                        return new Result(retNeedUpdateTR);
                    }
                    continue;
                }

                // Not need to update test run / test result
                if (!retNeedUpdateTR.getCd()) {
                    continue;
                }

                Result<FilePathDTO> resultFileDTO = testResultSevice.getFilePathDTOByInfo(nTestSetId, strTestCaseId, locale);
                if (resultFileDTO.hasError()) {
                    log.warn(ErrorConst.getWarningLogMsg(functionName, resultFileDTO));
                    if (bOnlyOneTestRun) {
                        return new Result(resultFileDTO);
                    }
                    continue;
                }
                dtoFilePathInfo = resultFileDTO.getCd();

                Result<TestResult> resultTR = testResultSevice.updateTestResultByReportFile(prs, testRun, testMethod, dtoFilePathInfo);
                if (resultTR.hasError()) {
                    log.warn(ErrorConst.getWarningLogMsg(functionName, resultTR));
                    if (bOnlyOneTestRun) {
                        return new Result(resultTR);
                    }
                    continue;
                }

                // Sync up Test Run status with TestResult status
                // ToDo: Sync up Test Run status with TestResult status

                nUpdateTestResultCount++;
            }
        }

        retResult.setCd(String.format("Count:%d, updateTR:%d", nTestMethodCount, nUpdateTestResultCount));

        return retResult;
    }


    /**
     * Logic check for QA status for update or not
     *
     * @param testResult
     * @return
     */
    private boolean isQAPassTest(TestResult testResult) {
        boolean bRet = false;
        String functionName = "ProcessTestRunServiceImpl.isQAPassTest()";

        try {
            String qaStatus = testResult.getQaStatus();
            if (!CommonUtil.isEmpty(qaStatus)) {
                Integer nQAStatus = Integer.parseInt(qaStatus);
                EnumDef.TEST_RESULT_QA_STATUS enumQAStatus = testResultSevice.getQAStatusEnum(nQAStatus);
                if (enumQAStatus == null) {
                    bRet = false;
                } else {
                    if (enumQAStatus == EnumDef.TEST_RESULT_QA_STATUS.PASS || enumQAStatus == EnumDef.TEST_RESULT_QA_STATUS.CONDITION_PASS) {
                        bRet = true;
                    }
                }
            }

            log.info("isQAPassTest = {}, qaStatus = {}", bRet, qaStatus);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(functionName, e));
        }

        return bRet;
    }

    /**
     * Judge the Test Run / Test Result need to update or not
     *
     * @param tr
     * @param status
     * @return
     */
    private Result<Boolean> isUpdateTestRunValidation(TestRun tr, String status) {

        Result<Boolean> retResult = new Result<>();

        boolean bRet = true;
        if (tr == null || CommonUtil.isEmpty(status)) {
            bRet = false;
        }

        // TestRun status is success
        if (tr.getStatus() == EnumDef.TEST_RUN_STATUS.SUCCESS.getCode()) {
            bRet = false;
        }

        TestResult testResult = testResultSevice.getTestResultByTestRunId(tr.getId());
        if (CommonUtil.isEmpty(testResult)) {
            return commonDao.getResult("300508", tr.getId());
        }

        // Logic check
        if (isQAPassTest(testResult)) {
            bRet = false;
        }

        log.info("bUpdateTestRun = {}, reportStatus = {}", bRet, status);

        retResult.setCd(bRet);
        return retResult;
    }

    /**
     * Get Locale info
     *
     * @param testMethod
     * @return
     */
    private String getLocale(TestMethods testMethod) {
        String strLocale = "";

        boolean bJSONFileHasLocale = commonDao.isProjectConfigSettingTrue(ProjectConst.JENKINS_AUTOMATION_REPORT_HAS_LOCALE);
        if (bJSONFileHasLocale) {
            strLocale = TestRunFactory.getTestLocale(testMethod.getConfiguration());
        } else {
            boolean bCompFileHasLocale = commonDao.isProjectConfigSettingTrue(ProjectConst.JENKINS_AUTOMATION_COMPRESS_FILE_HAS_LOCALE);
            if (bCompFileHasLocale) {
                strLocale = getLocalInfo();
            } else {
                strLocale = commonDao.getConfigValueByKey(ProjectConst.JENKINS_AUTOMATION_PARSER_LOCALE_DEFAULT);

            }
        }
        return strLocale;
    }

    private String getLocalInfo() {
        return Locales.EN_US.toString();
    }

    /**
     * Get Test Case Id from testMethod or DB
     *
     * @param testMethod
     * @return
     */
    private String getTestCaseId(TestMethods testMethod) {
        String nTestCaseId = null;
        boolean bHasTestCaseId = commonDao.isProjectConfigSettingTrue(ProjectConst.JENKINS_AUTOMATION_REPORT_HAS_TCID);
        if (bHasTestCaseId) {
            nTestCaseId = ReportJsonUtil.getTestCaseId(testMethod);
        } else {
            RelationTestCaseMethod relTestCaseMethod = getTestCaseMethod(testMethod);
            if (CommonUtil.isEmpty(relTestCaseMethod)) {
                log.warn(ErrorConst.getWarningLogMsg("ProcessTestRunServiceImpl.getTestCaseId()",
                        "Could not find record data in Table 'relation_test_case_method'."));
                nTestCaseId = null;
            } else {
                nTestCaseId = relTestCaseMethod.getTestCaseId().toString();
            }
        }

        return nTestCaseId;
    }

    private RelationTestCaseMethod getTestCaseMethod(TestMethods testMethod) {
        return reportFileService.getRelationTestCaseMethodByInfo(testMethod.getPackageInfo(),
                testMethod.getClassName(), testMethod.getMethodName());
    }
}
