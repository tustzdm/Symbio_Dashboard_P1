package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.TestRunVO;
import com.symbio.dashboard.business.TestCaseFactory;
import com.symbio.dashboard.business.TestResultFactory;
import com.symbio.dashboard.business.TestRunFactory;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.constant.ProjectConst;
import com.symbio.dashboard.data.dao.*;
import com.symbio.dashboard.data.repository.TestCaseRep;
import com.symbio.dashboard.data.repository.TestResultRep;
import com.symbio.dashboard.data.repository.TestRunRep;
import com.symbio.dashboard.data.repository.TestSetRep;
import com.symbio.dashboard.dto.TestRunExcelDTO;
import com.symbio.dashboard.dto.TestRunUiDTO;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.JenkinsJobHistoryMain;
import com.symbio.dashboard.model.TestCase;
import com.symbio.dashboard.model.TestResult;
import com.symbio.dashboard.model.TestRun;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.ExcelReadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName - TestRunServiceImpl
 * @Author - Admin
 * @Description - TestRun Service
 * @Date - 2019/8/5
 * @Version 1.0
 */

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class TestRunServiceImpl implements TestRunService {

    @Autowired
    private CommonDao commonDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ReleaseDao releaseDao;
    @Autowired
    private TestSetDao testSetDao;
    @Autowired
    private TestRunDao testRunDao;
    @Autowired
    private TestCaseRep testCaseRep;
    @Autowired
    private TestSetRep testSetRep;
    @Autowired
    private TestRunRep testRunRep;
    @Autowired
    private ExcelImportDao excelImportDao;
    @Autowired
    private TestResultRep testResultRep;

    @Override
    public TestRun getTestRunById(Integer id) {
        return testRunDao.getTestRunById(id);
    }


    @Override
    public TestRun getTestRunByReportFileInfo(Integer testSetId, String strTestCaseId, String locale) {
        Integer testCaseId = Integer.parseInt(strTestCaseId);
        if (testCaseId > 0) {
            return testRunRep.getByTestSetIdAndTestCaseIdAndLocale(testSetId, testCaseId, locale);
        } else {
            log.warn("TestRunServiceImpl.getTestRunByReportFileInfo() WARNING!!! TestSetId is illegal. strTestCaseId = " + strTestCaseId);
            return null;
        }
    }

    @Override
    public Result getTestRunList(String locale, TestRunVO testRun) {
        log.trace("TestRunServiceImpl.getTestRunList() Enter");
        log.trace(testRun.toString());

        Result<TestRunUiDTO> retResult;

        try {
            retResult = testRunDao.getList(locale, testRun);
        } catch (Exception e) {
            e.printStackTrace();
            retResult = commonDao.getResultArgs(locale,"000102", "getting TestRun List");
        }

        log.trace("TestRunServiceImpl.getTestRunList() Exit");
        return retResult;
    }

    @Override
    public Result getTestRunDemoList(String locale, TestRunVO testRun) {
        Result<TestRunUiDTO> retResult = new Result<TestRunUiDTO>();
        try {
            TestRunUiDTO data = getDemoData(testRun);
            retResult.setCd(data);
        } catch (Exception e) {
            e.printStackTrace();
            retResult = commonDao.getResultArgs(locale, "000102", "getting TestRun List");
        }

        log.trace("TestRunServiceImpl.getTestRunList() Exit");
        return retResult;
    }

    @Override
    public Result importExcel(String locale, Integer testSetId, String fileName) {
        Result<List<Map<String, String>>> headerResult;
        Result<List<TestRunExcelDTO>> importResult;
        ExcelReadUtil excelReadUtil = new ExcelReadUtil();
        // 1. Get Excel head settings
        headerResult = excelImportDao.getExcelHeadSetting(locale, testSetId);
        if (headerResult.hasError()) {
            return headerResult;
        }

        // 2. Get Test case info from excel - List
        List<Map<String, String>> listNameField = headerResult.getCd();
        String newFileName = CommonDef.FOLDER_PATH_IMPORT_TESTCASE + fileName;
        importResult = excelReadUtil.read(newFileName, listNameField);
        if (importResult.hasError()) {
            return importResult;
        }

        // 3. Update Test case
        List<TestRunExcelDTO> listTestCase = importResult.getCd();
        if (listTestCase == null || listTestCase.isEmpty()) {
            log.info("TestCase is null or empty");
            return commonDao.getResult("300302");
        }

        // 4. Update Test Run
        Result resUpdateTC = updateTestCase(testSetId, listTestCase);
        if (resUpdateTC.hasError()) {
            log.info("TestCase add or update failed");
            return resUpdateTC;
        }

        return importResult;
    }

    @Override
    public Result updateTestRun(Integer userId, String locale, TestRun testRun) {
        return null;
    }

    @Override
    public TestRun updateTestRun(TestRun testRun) {
        return testRunRep.saveAndFlush(testRun);
    }

    private Result<TestRun> saveNewTestRunInfo(Integer testSetId, TestCase testCase, String locale) {

        // Add Test Run
        Integer id = testCase.getId();
        TestRun testRun = testRunRep.getByTestCaseIdAndLocale(id, locale);
        TestResult testResult;
        try {
            if (testRun == null) {
                testRun = TestRunFactory.createNewTestRun(testCase, testSetId, locale);
                testRun = testRunRep.saveAndFlush(testRun);

                testResult = TestResultFactory.createNewTestResult(testSetId, testRun.getId());
                testResultRep.saveAndFlush(testResult);
            } else {
                testResult = testResultRep.getByTestRunId(testRun.getId());
                if (CommonUtil.isEmpty(testResult)) {
                    testResult = TestResultFactory.createNewTestResult(testSetId, testRun.getId());
                    testResultRep.saveAndFlush(testResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>("000017", "DB save failure");
        }

        return new Result(testRun);
    }

    private Result<List<TestRunExcelDTO>> updateTestCase(Integer testSetId, List<TestRunExcelDTO> listTestCase) {
        Result result = new Result();
        TestCase testCase;
        TestCase updatedTestCase = new TestCase();
        List<TestCase> testCaseList = new ArrayList<>();

        Integer caseType = testSetRep.getTypeById(testSetId);
        TestCase newTC = null;
        String trLocale = null;
        for (TestRunExcelDTO item : listTestCase) {
            TestCase t = item.getTestCase();
            trLocale = item.getLocale();
            String caseId = t.getCaseId();

            testCase = testCaseRep.getByCaseIdAndCaseType(caseId, caseType);
            if (testCase == null) {
                // Add new test case
                newTC = TestCaseFactory.createNewTestCaseByExcel(caseType, t);
                updatedTestCase = testCaseRep.saveAndFlush(newTC);
            } else {
                TestRun tr = null;
                // Check TestRun
                if (trLocale.contains(",")) {
                    String locale;
                    String[] locales = trLocale.split(",");
                    for (String loc : locales) {
                        locale = loc.trim();
                        tr = testRunRep.getByTestSetIdAndTestCaseIdAndLocale(testSetId, testCase.getId(), locale);
                        result = saveNewTestRunInfo(testSetId, testCase, locale);
                        if (result.hasError()) {
                            return result;
                        }
                    }
                } else {
                    tr = testRunRep.getByTestSetIdAndTestCaseIdAndLocale(testSetId, testCase.getId(), trLocale);
                    result = saveNewTestRunInfo(testSetId, testCase, trLocale);
                    if (result.hasError()) {
                        return result;
                    }
                }

                String replaceTestRunFlag = commonDao.getConfigValueByKey(ProjectConst.TESTCASE_IMP_REPLACE_SUCC);
                try {
                    EnumDef.TESTCASE_IMP_REPLACE_SUCC enumItem = EnumDef.getEnumTypeByCode(EnumDef.TESTCASE_IMP_REPLACE_SUCC.class, Integer.parseInt(replaceTestRunFlag));
                    switch (enumItem) {
                        case IGNORE:
                            updatedTestCase = null;
                            break;
                        case UPDATETC:
                            newTC = TestCaseFactory.mergeTestCaseByExcel(testCase, t);
                            updatedTestCase = testCaseRep.saveAndFlush(newTC);
                            break;
                        case BOTHUPDATE:
                            newTC = TestCaseFactory.mergeTestCaseByExcel(testCase, t);

                            updatedTestCase = testCaseRep.saveAndFlush(newTC);

                            if (tr != null) {
                                if (trLocale.contains(",")) {
                                    String locale;
                                    String[] locales = trLocale.split(",");
                                    for (String loc : locales) {
                                        locale = loc.trim();
                                        result = saveNewTestRunInfo(testSetId, testCase, locale);
                                        if (result.hasError()) {
                                            return result;
                                        }
                                    }
                                }
                                result = saveNewTestRunInfo(testSetId, updatedTestCase, trLocale);
                                if (result.hasError()) {
                                    return result;
                                }
                            }
                            break;
                    }
                } catch (Exception e) {
                    String strMsg = commonDao.getMessage(Locales.EN_US.toString(), "000015", "EnumDef.TESTCASE_IMP_REPLACE_SUCC", replaceTestRunFlag);
                    log.error(e.getMessage());
                    log.error(strMsg);

                    result.setEc("000015");
                    result.setEm(strMsg);
                    return result;
                }
            }

            if (updatedTestCase != null) {
                testCaseList.add(updatedTestCase);
            }
        }

        return new Result(testCaseList);
    }

    private TestRunUiDTO getDemoData(TestRunVO testRun) {
        TestRunUiDTO data = new TestRunUiDTO();

        data.setProductId(testRun.getProductId());
        if(BusinessUtil.isIdEmpty(testRun.getReleaseId())) {
            data.setReleaseId(0);
        } else {
            data.setReleaseId(testRun.getReleaseId());
        }
        // TestSetId
        if(BusinessUtil.isIdEmpty(testRun.getTestSetId())) {
            data.setTestSetId(0);
        } else {
            data.setTestSetId(testRun.getTestSetId());
        }

        Result retProduct = productDao.getNavigationList(testRun.getLocale(), null);
        if(retProduct.isSuccess()) {
            data.setProductList((List<Map<String, Object>>)retProduct.getCd());
        }

        Result retRelease = releaseDao.getNavigationList(testRun.getLocale(), testRun.getProductId(), null);
        List<Map<String, Object>> listData = null;
        if(retRelease.isSuccess()) {
            listData = (List<Map<String, Object>>)retRelease.getCd();
            if(BusinessUtil.isIdEmpty(testRun.getReleaseId()) && !listData.isEmpty()) {
                data.setReleaseId((Integer) listData.get(0).get("id"));
            }
            data.setReleaseList(listData);
        }
        Result retTestSet = testSetDao.getNavigationList(testRun.getUserId(), testRun.getLocale(), data.getReleaseId(), null);
        if(retRelease.isSuccess()) {
            listData = (List<Map<String, Object>>)retTestSet.getCd();
            if(BusinessUtil.isIdEmpty(testRun.getTestSetId()) && !listData.isEmpty()) {
                data.setTestSetId((Integer) listData.get(0).get("id"));
            }
            data.setTestSetList(listData);
        }

        return data;
    }

    @Override
    public Result<TestResult> updateTestResultJobWeather(JenkinsJobHistoryMain jjhM, EnumDef.JENKINS_JOB_STATUS enumJobStatus) {
        String funcName = "TestRunServiceImpl.updateTestResultJobWeather()";

        Result<TestResult> retResult = new Result<>();
        try {
            if (jjhM != null && !CommonUtil.isEmpty(jjhM.getTestRunId())) {
                Integer nTestRunId = jjhM.getTestRunId();
                boolean bHasTRunIdSetting = commonDao.isProjectConfigSettingTrue(ProjectConst.JENKINS_AUTOMATION_REPORT_HAS_TESTRUN_ID);

                if (bHasTRunIdSetting && EnumDef.isUpdateJobWeatherStatus(enumJobStatus)) {
                    TestRun tr = testRunDao.getTestRunById(nTestRunId);
                    if (tr != null && EnumDef.isUpdateTestResultByTRunStatus(tr.getStatus())) {
                        TestResult testResult = testResultRep.getByTestRunId(nTestRunId);
                        if (testResult != null) {
                            testResult.setJobWeather(enumJobStatus.getCode());
                            testResultRep.saveAndFlush(testResult);

                            retResult.setCd(testResult);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return ErrorConst.getExceptionResult("invoking " + funcName, e);
        }

        return retResult;
    }
}
