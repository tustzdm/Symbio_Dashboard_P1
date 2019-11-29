package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.business.ScreenShotFactory;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.dao.*;
import com.symbio.dashboard.dto.FilePathDTO;
import com.symbio.dashboard.dto.TestResultUiDTO;
import com.symbio.dashboard.enums.DictionaryType;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.*;
import com.symbio.dashboard.model.json.Logs;
import com.symbio.dashboard.model.json.TestMethods;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.DateUtil;
import com.symbio.dashboard.util.ImageSizer;
import com.symbio.dashboard.util.TimeConverterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName - TestResultServiceImpl
 * @Description - TestResult Service
 * @Date - 2019/8/23
 * @Version 1.0
 */

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class TestResultServiceImpl implements TestResultService {

    @Autowired
    private UserDao userDao;
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
    private TestResultDao testResultDao;
    @Autowired
    private TestCaseDao testCaseDao;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private DictionaryDao dictionaryDao;

    @Override
    public TestResult getTestResultByTestRunId(Integer testRunId) {
        return testResultDao.getTestResultByTestRunId(testRunId);
    }

    @Override
    public Result<TestResult> updateTestResultByReportFile(ParseResultSummary prs, TestRun tr, TestMethods testMethod, FilePathDTO dtoFilePathInfo) {
        Result<TestResult> retResult = new Result<>();
        String functionName = "TestResultServiceImpl.updateTestResultByReportFile()";

        try {
            TestResult testResult = getTestResultByTestRunId(tr.getId());
            if (CommonUtil.isEmpty(testResult)) {
                return commonDao.getResult("300508", tr.getId());
            }

            String trStatus = testMethod.getStatus();
            EnumDef.JENKINS_REPORT_FILE_STATUS enumTestNG = EnumDef.getEnumTypeByValue(EnumDef.JENKINS_REPORT_FILE_STATUS.class, trStatus);
            if (enumTestNG == null) {
                log.warn(String.format("Could not match JENKINS_TESTNG_STATUS by value [%s]", trStatus));
                enumTestNG = EnumDef.JENKINS_REPORT_FILE_STATUS.UN_KNOWN;
            }
            testResult.setAutoRunStatus(enumTestNG.getCode());
            testResult.setStartTime(TimeConverterUtil.utc2Date(testMethod.getStartTime()));
            testResult.setEndTime(TimeConverterUtil.utc2Date(testMethod.getEndTime()));
            Integer nDiffSecs = DateUtil.getDifferSeconds(testResult.getStartTime(), testResult.getEndTime());
            testResult.setExecDuration(nDiffSecs);
            testResult.setTimeZone(TimeConverterUtil.getUTCTimeZone(testMethod.getStartTime()));
            testResult.setScreenShotFlag(EnumDef.ENTITY_BOOL.NO.getCode());

            TestResult updTR = testResultDao.updateTestResult(testResult);

            processScreenShot(prs, updTR.getId(), testMethod.getLogs(), dtoFilePathInfo);

            if (testMethod.getLogs().size() > 0) {
                updTR.setScreenShotFlag(EnumDef.ENTITY_BOOL.YES.getCode());
                updTR = testResultDao.updateTestResult(testResult);
            }

            retResult.setCd(updTR);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(functionName, e));
            return ErrorConst.getExceptionResult(functionName, e);
        }

        return retResult;
    }

    public Integer removeScreenShot(Integer testRunId) {
        testRunDao.removeScreenShotByTestResultId(testRunId);
        return 0;
    }

    /**
     * Save or Update relevant logs info for the Test Run
     *
     * @param prs
     * @param testResultId
     * @param logs
     * @return
     */
    protected Result<Integer> processScreenShot(ParseResultSummary prs, Integer testResultId, List<Logs> logs, FilePathDTO dtoFilePathInfo) {

        Result<Integer> retResult = new Result();

        if (CommonUtil.isEmpty(logs)) {
            return retResult;
        }

        // Step1 - Remove screenshot if possible
        //Integer testRunId = testRun.getId();
        testRunDao.removeScreenShotByTestResultId(testResultId);

        // Step2 - insert new screen shot
        Logs log = null;
        ScreenShot ss = null;
        String workPath = prs.getFileWorkPath();
        int nTreatCount = 0;

        for (int i = 0; i < logs.size(); i++) {
            log = logs.get(i);
            String img = log.getImage();
            if (CommonUtil.isEmpty(img)) {
                continue;
            }

            // Step3 - Get ready
            ss = ScreenShotFactory.createNew(workPath, testResultId, i + 1, log, dtoFilePathInfo);

            // Step4 - upload file
            processScreenshotFiles(ss);
            nTreatCount++;
        }

        retResult.setCd(nTreatCount);

        return retResult;
    }

    public static void main(String[] args) {
        DateUtil dateUtil;

        TimeConverterUtil timeConvUtil;

        String startTime = "2019-08-19T17:42:59.336-08:30";

        try {
            String newDate = TimeConverterUtil.utc2Local(startTime, "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", "yyyy-MM-dd HH:mm:ss");
            System.out.println(newDate);

            System.out.println(TimeConverterUtil.utc2Date(startTime));
            System.out.println(TimeConverterUtil.getUTCTimeZone(startTime));

            String dateStr = "2017-06-27T07:26:58.147+0800";
            Long unix = 1386665666777L;
            String iso = "2013-12-10T16:54:26.777Z";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            // UTC转换
            Date date = sdf.parse(dateStr);
            String formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            System.out.println(formatStr);
            // unix时间转换
            Date unixDate = new Date(unix);
            String unixTime = format.format(unixDate);
            System.out.println(unixTime);
            // unix时间转换
            unix = unix + 1000 * 60 * 60 * 8L;
            System.out.println(unix);
            // iso时间转换
            Date isoDate = format.parse(iso);
            long isoTime = isoDate.getTime();
            System.out.println(isoTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Seldom used
    @Override
    public EnumDef.TEST_RESULT_QA_STATUS getQAStatusEnum(Integer qaStatus) {
        EnumDef.TEST_RESULT_QA_STATUS enumQAStatus = null;
        try {
            enumQAStatus = EnumDef.getEnumTypeByCode(EnumDef.TEST_RESULT_QA_STATUS.class, qaStatus);
            if (enumQAStatus == null) {
                log.warn(String.format("Could not match TEST_RESULT_QA_STATUS by code [%d]", qaStatus));
            } else {
                enumQAStatus = EnumDef.TEST_RESULT_QA_STATUS.UN_KNOWN;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enumQAStatus;
    }

    @Override
    public EnumDef.TEST_RESULT_STATUS getTestResultStatusEnum(Integer status) {
        EnumDef.TEST_RESULT_STATUS enumTestResultStatus = null;
        try {
            enumTestResultStatus = EnumDef.getEnumTypeByCode(EnumDef.TEST_RESULT_STATUS.class, status);
            if (enumTestResultStatus == null) {
                log.warn(String.format("Could not match TEST_RESULT_STATUS by code [%d]", status));
            } else {
                enumTestResultStatus = EnumDef.TEST_RESULT_STATUS.NOT_RUN;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enumTestResultStatus;
    }

    /**
     * Get FilePathInfo for next operation
     *
     * @param testSetId
     * @param testCaseId
     * @param locale
     * @return
     */
    @Override
    public Result<FilePathDTO> getFilePathDTOByInfo(Integer testSetId, String testCaseId, String locale) {
        Result<FilePathDTO> retResult = new Result<FilePathDTO>();
        String functionName = "TestResultServiceImpl.getFilePathDTOByInfo()";

        try {
            FilePathDTO dtoFilePath = new FilePathDTO();

            dtoFilePath.setCompanyId(0); // Fixed, not support

            dtoFilePath.setTestCaseId(testCaseId);
            if (CommonUtil.isEmpty(locale)) {
                dtoFilePath.setLocale(Locales.EN_US.toString());
            } else {
                dtoFilePath.setLocale(locale);
            }

            Map<String, Object> mapData = testSetDao.getKeyInfoByTestSetId(testSetId);
            if (CommonUtil.isEmpty(mapData)) {
                return commonDao.getResult("500106", testSetId);
            }

            dtoFilePath.setProductId(getIntValue(mapData, "productId"));
            dtoFilePath.setReleaseId(getIntValue(mapData, "releaseId"));
            dtoFilePath.setTestSetId(getIntValue(mapData, "testSetId"));
            dtoFilePath.setCaseType(getIntValue(mapData, "caseType"));

            retResult.setCd(dtoFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(functionName, e));
            return commonDao.getExceptionArgsResult("invoking " + functionName + ". ErrorMsg: " + e.getMessage());
        }

        return retResult;
    }

    @Override
    public Result<FilePathDTO> getFilePathDTOByInfo(TestRun testRun) {
        if (CommonUtil.isEmpty(testRun)) {
            return commonDao.getFuncArgsMissingResult("TestResultServiceImpl.getFilePathDTOByInfo()", "TestRun");
        }

        Integer testSetId = testRun.getTestsetId();
        String testCaseId = testRun.getTestcaseId().toString();
        String locale = testRun.getLocale();

        return this.getFilePathDTOByInfo(testSetId, testCaseId, locale);
    }

    private Integer getIntValue(Map<String, Object> map, String key) {
        return Integer.parseInt((String) map.get(key));
    }

    public Map<String, Object> getKeyInfoByTestSetId(Integer testSetId) {
        return testSetDao.getKeyInfoByTestSetId(testSetId);
    }

    private Result<ScreenShot> processScreenshotFiles(ScreenShot screenShot) {
        String funcName = "TestResultServiceImpl.processScreenshotFiles()";
        if (CommonUtil.isEmpty(screenShot)) {
            return new Result();
        }

        Result<ScreenShot> retResult = new Result();

        try {
            boolean bNeedUpdateSS = false;
            if (!CommonUtil.isEmpty(screenShot.getFilePath())) {
                String strThumbFile = ScreenShotFactory.getThumbnailFileName(screenShot.getOriginalName());

                File ssFile = new File(screenShot.getOriginalName());
                File fileThumbnail = new File(strThumbFile);
                ImageSizer.resize(ssFile, fileThumbnail, 240, CommonDef.PNG);

                fileService.moveFileToDirectory(screenShot.getOriginalName(), screenShot.getFilePath());
                fileService.moveFileToDirectory(strThumbFile, screenShot.getThumbnailFilePath());

                screenShot.setFileSize((int) ssFile.length());
                screenShot.setThumbnailFileSize((int) fileThumbnail.length());
                bNeedUpdateSS = true;
            }

            if (!CommonUtil.isEmpty(screenShot.getSourceFilePath())) {
                fileService.moveFileToDirectory(screenShot.getSourceFileOriginalName(), screenShot.getSourceFilePath());

                File sourceFile = new File(screenShot.getSourceFileOriginalName());
                screenShot.setFileSize((int) sourceFile.length());
                bNeedUpdateSS = true;
            }

            if (bNeedUpdateSS) {
                screenShot.setCreateTime(new Date());
                screenShot = testResultDao.updateScreenShot(screenShot);
            }

            retResult.setCd(screenShot);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return commonDao.getExceptionArgsResult("invoking " + funcName);
        }
        return retResult;
    }

    @Override
    public Result getTestResultInfoByTestRunId(Integer userId, String locale, Integer testRunId) {
        String funcName = "TestResultServiceImpl.getTestResultInfoByTestRunId()";

        Result<TestResultUiDTO> retResult = new Result();

        // Get TestRun record
        Result<TestRun> resultTestRun = testRunDao.getTestRunById(locale, testRunId);
        if (resultTestRun.hasError()) {
            return resultTestRun;
        }
        TestRun testRun = resultTestRun.getCd();

        // Get TestResult record
        Result<TestResult> resultTestResult = testResultDao.getTestResultByTestRunId(locale, testRunId);
        if (resultTestResult.hasError()) {
            return resultTestResult;
        }
        TestResult tResult = resultTestResult.getCd();

        // Get TestCase record
        Result<TestCase> resultTestCase = testCaseDao.getById(locale, testRun.getTestcaseId());
        if (resultTestCase.hasError()) {
            return resultTestCase;
        }
        TestCase testCase = resultTestCase.getCd();

        TestResultUiDTO testResultDTO = new TestResultUiDTO(locale, testRunId);

        //testResultDTO.setRole(7);
        testResultDTO.setRole(commonDao.getUserMenuRole(userId));
        testResultDTO.setTestRunId(testRunId);
        testResultDTO.setTestResult(tResult);
        testResultDTO.setTestCase(testCase);
        testResultDTO.setTestRun(testRun);

        testResultDTO.setListAutoStatus(dictionaryDao.getDicMapDataByType(DictionaryType.TestRunStatus.getType()));
        testResultDTO.setListQAStatus(dictionaryDao.getDicMapDataByTypeLocale(DictionaryType.TEST_RESULT_STATUS_LOCALE.getType(), locale));

        List<ScreenShot> listScreenShots = testResultDao.getScreenShotsByTestResultId(tResult.getId());
        testResultDTO.setListScreenShots(ScreenShotFactory.getScreenshotUIList(listScreenShots));

        retResult.setCd(testResultDTO);

        return retResult;
    }

    @Override
    public Result updateTestResultInfo(Integer userId, String locale, Integer testRunId, Integer autoStatus, String qaStatus) {
        Result retResult = new Result();
        String funcName = "TestResultServiceImpl.updateTestResultInfo()";

        // Validation
        Result<Boolean> resultValidation = validationUpdateTestResult(locale, autoStatus, qaStatus);
        if (resultValidation.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, resultValidation));
            return resultValidation;
        }

        // Get Record
        Result<TestRun> resultTestRun = testRunDao.getTestRunById(locale, testRunId);
        if (resultTestRun.hasError()) {
            return resultTestRun;
        }
        TestRun tr = resultTestRun.getCd();

        // TestResult info
        Result<TestResult> resultTestResult = testResultDao.getTestResultByTestRunId(locale, testRunId);
        if (resultTestResult.hasError()) {
            return resultTestResult;
        }
        TestResult tResult = resultTestResult.getCd();

        // User info
        Result<User> resultUser = userDao.getById(userId);
        if (resultUser.hasError()) {
            return resultUser;
        }
        User userInfo = resultUser.getCd();

        // Sync up data if need
        if (autoStatus != tResult.getAutoRunStatus()) {
            tr.setStatus(autoStatus);
            tr.setUpdateUser(userId);
            tr.setUpdateUserName(userInfo.getFullName());
            testRunDao.updateTestRun(tr);
        }

        if (autoStatus != tResult.getAutoRunStatus() || !qaStatus.equals(tResult.getQaStatus())) {
            tResult.setAutoRunStatus(autoStatus);
            tResult.setQaStatus(qaStatus);
            tResult.setUpdateUser(userId);
            tResult.setUpdateUserName(userInfo.getFullName());
            testResultDao.updateTestResult(tResult);
        }
        return retResult;
    }

    private Result<Boolean> validationUpdateTestResult(String locale, Integer autoStatus, String qaStatus) {
        Result<Boolean> retResult = new Result<>();
        String funcName = "TestResultServiceImpl.validationUpdateTestResult()";

        EnumDef.TEST_RUN_STATUS enumAutoStatus = null;
        EnumDef.TEST_RESULT_QA_STATUS enumQAStatus = null;

        try {
            enumAutoStatus = EnumDef.getEnumTypeByCode(EnumDef.TEST_RUN_STATUS.class, autoStatus);
            if (enumAutoStatus == null) {
                log.warn(commonDao.getMessage(locale, ErrorConst.EXCEPTION_ENUM_CONVERT_ERROR_CODE, "TEST_RUN_STATUS", autoStatus.toString()));
                return commonDao.getResult(ErrorConst.EXCEPTION_ENUM_CONVERT_ERROR_CODE, "TEST_RUN_STATUS", autoStatus);
            }

            enumQAStatus = EnumDef.getEnumTypeByCode(EnumDef.TEST_RESULT_QA_STATUS.class, Integer.parseInt(qaStatus));
            if (enumQAStatus == null) {
                log.warn(commonDao.getMessage(locale, ErrorConst.EXCEPTION_ENUM_CONVERT_ERROR_CODE, "TEST_RESULT_QA_STATUS", qaStatus));
                return commonDao.getResult(ErrorConst.EXCEPTION_ENUM_CONVERT_ERROR_CODE, "TEST_RESULT_QA_STATUS", qaStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return ErrorConst.getExceptionResult(funcName, e);
        }

        return retResult;
    }
}
