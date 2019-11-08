package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.dao.*;
import com.symbio.dashboard.data.repository.TestCaseRep;
import com.symbio.dashboard.data.repository.TestResultRep;
import com.symbio.dashboard.data.repository.TestRunRep;
import com.symbio.dashboard.data.repository.TestSetRep;
import com.symbio.dashboard.dto.ResultReviewUiDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName - ResultReviewServiceImpl
 * @Author - Shawn
 * @Description - Result Review Service
 * @Date - 2019/10/11
 * @Version 1.0
 */

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class ResultReviewServiceImpl implements ResultReviewService {

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

    @Autowired
    private TestResultDao testResultDao;
    @Autowired
    private BugReportDao bugReportDao;

    @Override
    public Result<ResultReviewUiDTO> getList(Integer userId, String locale, Integer testRunId, String trLocale, Integer pageIndex, Integer pageSize) {
        log.trace("ResultReviewServiceImpl.getList() Enter");

        Result<ResultReviewUiDTO> retResult;
        try {
            retResult = testResultDao.getResultReviewList(locale, testRunId, trLocale, pageIndex, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            retResult = commonDao.getResultArgs(locale, "000102", "getting Result Review List");
        }

        log.trace("ResultReviewServiceImpl.getList() Exit");
        return retResult;
    }

    /**
     * Feature: Bug report
     * <p>
     * Get bug info
     *
     * @param userId       User id
     * @param locale       UI locale
     * @param testRunId    Test run /Test result id
     * @param screenshotId Screen shot id
     * @param trLocale     Test Result locale
     * @return
     */
    public Result getBugInfo(Integer userId, String locale, Integer testRunId, Integer screenshotId, String trLocale) {
        String funcName = "ResultReviewServiceImpl.getBugInfo()";

        log.trace(String.format("%s Enter", funcName));

        Result retResult = bugReportDao.getBugInfo(userId, locale, testRunId, screenshotId, trLocale);

        log.trace(String.format("%s Exit", funcName));
        return retResult;
    }


//
//    @Override
//    public TestRun getTestRunById(Integer id) {
//        return testRunDao.getTestRunById(id);
//    }
//
//
//    @Override
//    public TestRun getTestRunByReportFileInfo(Integer testSetId, String strTestCaseId, String locale) {
//        Integer testCaseId = Integer.parseInt(strTestCaseId);
//        if (testCaseId > 0) {
//            return testRunRep.getByTestSetIdAndTestCaseIdAndLocale(testSetId, testCaseId, locale);
//        } else {
//            log.warn("TestRunServiceImpl.getTestRunByReportFileInfo() WARNING!!! TestSetId is illegal. strTestCaseId = " + strTestCaseId);
//            return null;
//        }
//    }
//
//    @Override
//    public Result getTestRunList(String locale, TestRunVO testRun) {
//        log.trace("TestRunServiceImpl.getTestRunList() Enter");
//        log.trace(testRun.toString());
//
//        Result<TestRunUiDTO> retResult;
//
//        try {
//            retResult = testRunDao.getList(locale, testRun);
//        } catch (Exception e) {
//            e.printStackTrace();
//            retResult = commonDao.getResultArgs(locale, "000102", "getting TestRun List");
//        }
//
//        log.trace("TestRunServiceImpl.getTestRunList() Exit");
//        return retResult;
//    }
//
//    @Override
//    public Result runTestRun(String locale, TestRunVO testRun) {
//        log.trace("TestRunServiceImpl.runTestRun() Enter");
//        log.trace(testRun.toString());
//
//        Result<TestRunUiDTO> retResult;
//
//        try {
//            retResult = testRunDao.getList(locale, testRun);
//            if (retResult.isSuccess()) {
//                // Demo result
//                retResult = testRunDao.demoRunResult(retResult);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            retResult = commonDao.getResultArgs(locale, "000102", "run TestRun List");
//        }
//
//        log.trace("TestRunServiceImpl.runTestRun() Exit");
//        return retResult;
//    }
//
//    @Override
//    public Result getTestRunDemoList(String locale, TestRunVO testRun) {
//        Result<TestRunUiDTO> retResult = new Result<TestRunUiDTO>();
//        try {
//            TestRunUiDTO data = getDemoData(testRun);
//            retResult.setCd(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//            retResult = commonDao.getResultArgs(locale, "000102", "getting TestRun List");
//        }
//
//        log.trace("TestRunServiceImpl.getTestRunList() Exit");
//        return retResult;
//    }
//
//    @Override
//    public Result importExcel(String locale, Integer testSetId, String fileName) {
//        Result<List<Map<String, String>>> headerResult;
//        Result<List<TestRunExcelDTO>> importResult;
//        ExcelReadUtil excelReadUtil = new ExcelReadUtil();
//        // 1. Get Excel head settings
//        headerResult = excelImportDao.getExcelHeadSetting(locale, testSetId);
//        if (headerResult.hasError()) {
//            return headerResult;
//        }
//
//        // 2. Get Test case info from excel - List
//        List<Map<String, String>> listNameField = headerResult.getCd();
//        String newFileName = fileName; // CommonDef.FOLDER_PATH_IMPORT_TESTCASE + fileName;
//        importResult = excelReadUtil.read(newFileName, listNameField);
//        if (importResult.hasError()) {
//            return importResult;
//        }
//
//        // 3. Update Test case
//        List<TestRunExcelDTO> listTestCase = importResult.getCd();
//        if (listTestCase == null || listTestCase.isEmpty()) {
//            log.info("TestCase is null or empty");
//            return commonDao.getResult("300302");
//        }
//
//        // 4. Update Test Run
//        Result resUpdateTC = updateTestCase(testSetId, listTestCase);
//        if (resUpdateTC.hasError()) {
//            log.info("TestCase add or update failed");
//            return resUpdateTC;
//        }
//
//        return importResult;
//    }
//
//    @Override
//    public Result updateTestRun(Integer userId, String locale, TestRun testRun) {
//        return null;
//    }
//
//    @Override
//    public TestRun updateTestRun(TestRun testRun) {
//        return testRunRep.saveAndFlush(testRun);
//    }
//
//    private Result<TestRun> saveNewTestRunInfo(Integer testSetId, TestCase testCase, String locale) {
//
//        // Add Test Run
//        TestRun testRun = testRunRep.getByTestSetIdAndTestCaseIdAndLocale(testSetId, testCase.getId(), locale);
//        TestResult testResult = null;
//        try {
//            if (testRun == null) {
//                testRun = TestRunFactory.createNewTestRun(testCase, testSetId, locale);
//                testRun = testRunRep.saveAndFlush(testRun);
//            } else {
//                testResult = testResultRep.getByTestRunId(testRun.getId());
//            }
//
//            if (CommonUtil.isEmpty(testResult)) {
//                testResult = TestResultFactory.createNewTestResult(testSetId, testRun.getId());
//                testResultRep.saveAndFlush(testResult);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new Result<>("000017", "DB save failure");
//        }
//
//        return new Result(testRun);
//    }
//
//    private Result<List<TestRunExcelDTO>> updateTestCase(Integer testSetId, List<TestRunExcelDTO> listTestCase) {
//        Result result = new Result();
//        String funcName = "TestRunServiceImpl.updateTestCase()";
//
//        TestCase testCase;
//        TestCase updatedTestCase = new TestCase();
//        List<TestCase> testCaseList = new ArrayList<>();
//
//        Integer caseType = testSetRep.getTypeById(testSetId);
//        TestCase newTC = null;
//        String trLocale = null;
//        Result retInsertTestRun = null;
//        boolean bInsertNewTestRun = false;
//
//        for (TestRunExcelDTO item : listTestCase) {
//            TestCase t = item.getTestCase();
//            trLocale = item.getLocale();
//            String caseId = t.getCaseId();
//            bInsertNewTestRun = false;
//
//            testCase = testCaseRep.getByCaseIdAndCaseType(caseId, caseType);
//            if (testCase == null) {
//                // Add new test case
//                newTC = TestCaseFactory.createNewTestCaseByExcel(caseType, t);
//                updatedTestCase = testCaseRep.saveAndFlush(newTC);
//
//                // Insert new Test Run
//                retInsertTestRun = saveNewTestRunInfo(testSetId, updatedTestCase, trLocale);
//                if (retInsertTestRun.hasError()) {
//                    log.error(ErrorConst.getErrorLogMsg(funcName, retInsertTestRun));
//                    return new Result(retInsertTestRun);
//                }
//                bInsertNewTestRun = true;
//
//            } else {
//                TestRun tr = null;
////                // Check TestRun
////                if (trLocale.contains(",")) {
////                    String locale;
////                    String[] locales = trLocale.split(",");
////                    for (String loc : locales) {
////                        locale = loc.trim();
////                        tr = testRunRep.getByTestSetIdAndTestCaseIdAndLocale(testSetId, testCase.getId(), locale);
////                        result = saveNewTestRunInfo(testSetId, testCase, locale);
////                        if (result.hasError()) {
////                            return result;
////                        }
////                    }
////                } else {
//////                    tr = testRunRep.getByTestSetIdAndTestCaseIdAndLocale(testSetId, testCase.getId(), trLocale);
//////                    result = saveNewTestRunInfo(testSetId, testCase, trLocale);
//////                    if (result.hasError()) {
//////                        return result;
//////                    }
////                }
//
//                tr = testRunRep.getByTestSetIdAndTestCaseIdAndLocale(testSetId, testCase.getId(), trLocale);
//                if (tr == null) {
//                    retInsertTestRun = saveNewTestRunInfo(testSetId, testCase, trLocale);
//                    if (retInsertTestRun.hasError()) {
//                        log.error(ErrorConst.getErrorLogMsg(funcName, retInsertTestRun));
//                        return new Result(retInsertTestRun);
//                    } else {
//                        bInsertNewTestRun = true;
//                    }
//                }
//
//                String replaceTestRunFlag = commonDao.getConfigValueByKey(ProjectConst.TESTCASE_IMP_REPLACE_SUCC);
//                try {
//                    EnumDef.TESTCASE_IMP_REPLACE_SUCC enumItem = EnumDef.getEnumTypeByCode(EnumDef.TESTCASE_IMP_REPLACE_SUCC.class, Integer.parseInt(replaceTestRunFlag));
//                    switch (enumItem) {
//                        case IGNORE: // Not Update TestRun
//                            updatedTestCase = null;
//                            break;
//                        case UPDATETC: // Only update TC
//                            newTC = TestCaseFactory.mergeTestCaseByExcel(testCase, t);
//                            updatedTestCase = testCaseRep.saveAndFlush(newTC);
//                            break;
//                        case BOTHUPDATE: // Update TC & TRun
//                            newTC = TestCaseFactory.mergeTestCaseByExcel(testCase, t);
//                            updatedTestCase = testCaseRep.saveAndFlush(newTC);
//
//                            if (bInsertNewTestRun == false) {
//                                Result retUpdTestRun = saveNewTestRunInfo(testSetId, updatedTestCase, trLocale);
//                                if (retUpdTestRun.hasError()) {
//                                    return retUpdTestRun;
//                                }
//                            }
//
////                            if (tr != null) {
////                                if (trLocale.contains(",")) {
////                                    String locale;
////                                    String[] locales = trLocale.split(",");
////                                    for (String loc : locales) {
////                                        locale = loc.trim();
////                                        result = saveNewTestRunInfo(testSetId, testCase, locale);
////                                        if (result.hasError()) {
////                                            return result;
////                                        }
////                                    }
////                                }
////                                result = saveNewTestRunInfo(testSetId, updatedTestCase, trLocale);
////                                if (result.hasError()) {
////                                    return result;
////                                }
////                            }
//                            break;
//                    }
//                } catch (Exception e) {
//                    String strMsg = commonDao.getMessage(Locales.EN_US.toString(), "000015", "EnumDef.TESTCASE_IMP_REPLACE_SUCC", replaceTestRunFlag);
//                    log.error(e.getMessage());
//                    log.error(strMsg);
//
//                    result.setEc("000015");
//                    result.setEm(strMsg);
//                    return result;
//                }
//            }
//
//            if (updatedTestCase != null) {
//                testCaseList.add(updatedTestCase);
//            }
//        }
//
//        return new Result(testCaseList);
//    }
//
//    private TestRunUiDTO getDemoData(TestRunVO testRun) {
//        TestRunUiDTO data = new TestRunUiDTO();
//
//        data.setProductId(testRun.getProductId());
//        if (BusinessUtil.isIdEmpty(testRun.getReleaseId())) {
//            data.setReleaseId(0);
//        } else {
//            data.setReleaseId(testRun.getReleaseId());
//        }
//        // TestSetId
//        if (BusinessUtil.isIdEmpty(testRun.getTestSetId())) {
//            data.setTestSetId(0);
//        } else {
//            data.setTestSetId(testRun.getTestSetId());
//        }
//
//        Result retProduct = productDao.getNavigationList(testRun.getLocale(), null);
//        if (retProduct.isSuccess()) {
//            data.setProductList((List<Map<String, Object>>) retProduct.getCd());
//        }
//
//        Result retRelease = releaseDao.getNavigationList(testRun.getLocale(), testRun.getProductId(), null);
//        List<Map<String, Object>> listData = null;
//        if (retRelease.isSuccess()) {
//            listData = (List<Map<String, Object>>) retRelease.getCd();
//            if (BusinessUtil.isIdEmpty(testRun.getReleaseId()) && !listData.isEmpty()) {
//                data.setReleaseId((Integer) listData.get(0).get("id"));
//            }
//            data.setReleaseList(listData);
//        }
//        Result retTestSet = testSetDao.getNavigationList(testRun.getUserId(), testRun.getLocale(), data.getReleaseId(), null);
//        if (retRelease.isSuccess()) {
//            listData = (List<Map<String, Object>>) retTestSet.getCd();
//            if (BusinessUtil.isIdEmpty(testRun.getTestSetId()) && !listData.isEmpty()) {
//                data.setTestSetId((Integer) listData.get(0).get("id"));
//            }
//            data.setTestSetList(listData);
//        }
//
//        return data;
//    }
//
//    @Override
//    public Result<TestResult> updateTestResultJobWeather(JenkinsJobHistoryMain jjhM, EnumDef.JENKINS_JOB_STATUS enumJobStatus) {
//        String funcName = "TestRunServiceImpl.updateTestResultJobWeather()";
//
//        Result<TestResult> retResult = new Result<>();
//        try {
//            if (jjhM != null && !CommonUtil.isEmpty(jjhM.getTestRunId())) {
//                Integer nTestRunId = jjhM.getTestRunId();
//                boolean bHasTRunIdSetting = commonDao.isProjectConfigSettingTrue(ProjectConst.JENKINS_AUTOMATION_REPORT_HAS_TESTRUN_ID);
//
//                if (bHasTRunIdSetting && EnumDef.isUpdateJobWeatherStatus(enumJobStatus)) {
//                    TestRun tr = testRunDao.getTestRunById(nTestRunId);
//                    if (tr != null && EnumDef.isUpdateTestResultByTRunStatus(tr.getStatus())) {
//                        TestResult testResult = testResultRep.getByTestRunId(nTestRunId);
//                        if (testResult != null) {
//                            testResult.setJobWeather(enumJobStatus.getCode());
//                            testResultRep.saveAndFlush(testResult);
//
//                            retResult.setCd(testResult);
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
//            return ErrorConst.getExceptionResult("invoking " + funcName, e);
//        }
//
//        return retResult;
//    }
}
