package com.symbio.dashboard.monitor.service.impl;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.business.ReportJsonUtil;
import com.symbio.dashboard.business.TestRunFactory;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.constant.ProjectConst;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.model.JenkinsJobHistoryMain;
import com.symbio.dashboard.model.ParseResultSummary;
import com.symbio.dashboard.model.RelationTestCaseMethod;
import com.symbio.dashboard.model.TestRun;
import com.symbio.dashboard.model.json.ReportJson;
import com.symbio.dashboard.model.json.TestMethods;
import com.symbio.dashboard.service.AutomationReportFileServiceImpl;
import com.symbio.dashboard.service.FileServiceImpl;
import com.symbio.dashboard.service.ZipProcessServiceImpl;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProcessTestRunServiceImpl {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ZipProcessServiceImpl zipService;

    @Autowired
    private AutomationReportFileServiceImpl reportFileService;

    public Result processTestRun(ParseResultSummary prs, ReportJson jsonReport) {
        String functionName = "ZipProcessServiceImpl.processTestRun()";

        Result<JenkinsJobHistoryMain> resultLabelInfo = reportFileService.getJobInfoFromZipName(prs.getFileName());
        if (resultLabelInfo.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(functionName, resultLabelInfo));
            return new Result(resultLabelInfo);
        }

        JenkinsJobHistoryMain jjHMain = resultLabelInfo.getCd();
        Integer nTestSetId = jjHMain.getTestSetId();

        Integer nTestCaseId;
        String locale;
        TestRun testRun = null;
        List<TestMethods> listTestMethod = jsonReport.getTestMethods();
        for (TestMethods testMethod : listTestMethod) {
            nTestCaseId = getTestCaseId(testMethod);
            locale = getLocale(testMethod);

            testRun = null;
        }

        return null;
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
        return null;
    }

    /**
     * Get Test Case Id from testMethod or DB
     *
     * @param testMethod
     * @return
     */
    private Integer getTestCaseId(TestMethods testMethod) {
        Integer nTestCaseId = null;
        boolean bHasTestCaseId = commonDao.isProjectConfigSettingTrue(ProjectConst.JENKINS_AUTOMATION_REPORT_HAS_TCID);
        if (bHasTestCaseId) {
            nTestCaseId = ReportJsonUtil.getTestCaseId(testMethod);
        } else {
            RelationTestCaseMethod relTestCaseMethod = getTestCaseMethod(testMethod);
            if (CommonUtil.isEmpty(relTestCaseMethod)) {
                log.warn("Could not find record data in Table 'relation_test_case_method'.");
                nTestCaseId = null;
            } else {
                nTestCaseId = relTestCaseMethod.getTestCaseId();
            }
        }

        return nTestCaseId;
    }

    private RelationTestCaseMethod getTestCaseMethod(TestMethods testMethod) {
        return reportFileService.getRelationTestCaseMethodByInfo(testMethod.getPackageInfo(),
                testMethod.getClassName(), testMethod.getMethodName());
    }
}
