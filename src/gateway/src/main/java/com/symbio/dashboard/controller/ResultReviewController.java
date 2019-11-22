package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.TestRunVO;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.dto.TEPInfoDTO;
import com.symbio.dashboard.dto.TestRunExcelDTO;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.jenkins.JenkinsService;
import com.symbio.dashboard.model.BugInfo;
import com.symbio.dashboard.monitor.service.impl.MonitorServiceImpl;
import com.symbio.dashboard.service.*;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.StringUtil;
import com.symbio.dashboard.validator.ImpTestCaseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName - ResultReviewController
 * @Author - Admin
 * @Description - 控制器
 * @Date - 2019/8/5
 * @Version 1.0
 */
@RequestMapping("/result")
@RestController
@Slf4j
@SuppressWarnings("unchecked")
public class ResultReviewController extends BaseController {

    @Autowired
    private TestRunService testRunService;
    @Autowired
    private FileUploadService fileService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private TestResultServiceImpl testResultService;
    @Autowired
    private JenkinsService jenkinsService;
    @Autowired
    private ResultReviewServiceImpl resultReviewService;

    @Autowired
    private MonitorServiceImpl monitorService;


    /**
     * 得到Test Run List
     * interface: /result/getList
     *
     * @param testRun
     * @return
     */
    @RequestMapping("/getList")
    public Result getList(@RequestBody TestRunVO testRun) {
        log.trace("ResultReviewController.getList() Enter");

        Result retResult = new Result();
        try {
            String token = testRun.getToken();
            if (CommonUtil.isEmpty(token)) {

            }

            Integer userId = 0;
            testRun.setUserId(userId);
            if (StringUtil.isEmpty(testRun.getLocale())) {
                testRun.setLocale(Locales.EN_US.toString());
            }

            retResult = testRunService.getTestRunList(testRun.getLocale(), testRun);

            if (retResult.hasError()) {
                log.error(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.trace("ResultReviewController.getList() Exit");
        return retResult;
    }

    /**
     * 得到 Result Review List 数据
     * interface: /result/getReviewList
     *
     * @param token
     * @param locale
     * @param testRunId
     * @param trlocale
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/getReviewList")
    public Result getReviewList(@RequestParam(value = "token") String token,
                                @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                @RequestParam(value = "testRunId") Integer testRunId,
                                @RequestParam(value = "trlocale") String trlocale,
                                @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.trace("ResultReviewController.getReviewList() Enter");

        Result retResult = new Result();
        try {
            Integer userId = 0;
            retResult = resultReviewService.getList(userId, locale, testRunId, trlocale, pageIndex, pageSize);
            if (retResult.hasError()) {
                log.error(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.trace("ResultReviewController.getReviewList() Exit");
        return retResult;
    }

    @RequestMapping("/run")
    public Result runTestRun(@RequestBody TestRunVO testRun) {
        log.trace("ResultReviewController.runTestRun() Enter");

        Result retResult = new Result();
        try {
            Integer userId = 0;
            testRun.setUserId(userId);
            if (StringUtil.isEmpty(testRun.getLocale())) {
                testRun.setLocale(Locales.EN_US.toString());
            }

            retResult = testRunService.runTestRun(testRun.getLocale(), testRun);

            if (retResult.hasError()) {
                log.error(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.trace("ResultReviewController.runTestRun() Exit");
        return retResult;
    }

    /**
     * 上传 Excel文件，用于导入Test Run / Test Result 数据
     * interface: /result/upload
     * @param token
     * @param testSetId
     * @param locale
     * @param request
     * @return
     */
    @RequestMapping("/upload")
    public Result importTestCaseExcel(@RequestParam(value = "token", required = false, defaultValue = "") String token,
                                      @RequestParam(value = "testSetId", required = false, defaultValue = "0") Integer testSetId,
                                      @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                      HttpServletRequest request) {
        String funcName = "ResultReviewController.upload()";
        log.debug(funcName + " Enter");
        log.info("testSetId = {}", testSetId);

        Result<String> retSaveFile = new Result<String>();

        try {
            retSaveFile = fileService.saveExcel(request, CommonDef.FOLDER_PATH_IMPORT_TESTCASE);
            if (retSaveFile.hasError()) {
                log.info(ErrorConst.getWarningLogMsg(funcName, retSaveFile));
                return retSaveFile;
            }

            Integer nTestTestSetId = 435; // testSetId
            if (testSetId != null && testSetId > 0) {
                nTestTestSetId = testSetId;
                String fileName = retSaveFile.getCd();

                Result<List<TestRunExcelDTO>> retImportExcel = testRunService.importExcel(locale, nTestTestSetId, fileName);
                if (retImportExcel.hasError()) {
                    log.error(ErrorConst.getWarningLogMsg(funcName, retImportExcel));
                    return retImportExcel;
                } else {
                    Integer nTestRunCount = 0;
                    List<TestRunExcelDTO> listTestRun = retImportExcel.getCd();
                    if (!CommonUtil.isEmpty(listTestRun)) {
                        nTestRunCount = listTestRun.size();
                    }
                    retSaveFile.setCd(String.format("Test Run count: " + nTestRunCount));
                }
            } else {
                return getResultArgs(locale, "000018", "TestSet ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return new Result(ErrorConst.getExceptionResult(funcName, e));
        }

        log.debug(funcName + " Exit");
        return retSaveFile;
    }

    /**
     * Just provide zip file
     * interface: /result/uploadTestRunZipFile
     *
     * @param token
     * @param locale
     * @param request
     * @return
     */
    @PostMapping("/uploadTestRunZipFile")
    public Result uploadTestRunZipFile(@RequestParam(value = "token") String token,
                                       @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                       HttpServletRequest request) {
        String funcName = "ResultReviewController.uploadTestRunZipFile()";
        log.debug(funcName + " Enter");

        Result<String> retUploadFile = new Result<String>();

        try {
            retUploadFile = fileService.uploadZipFile(request, CommonDef.FOLDER_PATH_DASHBOARD_ZIP_ROOT);
            if (retUploadFile.hasError()) {
                log.info(ErrorConst.getWarningLogMsg(funcName, retUploadFile));
                return retUploadFile;
            }

            // Let zip file be parsed ASAP
            Result resultTask = monitorService.checkZipRoot();
            if (resultTask.hasError()) {
                log.error(ErrorConst.getWarningLogMsg(funcName, resultTask));
                retUploadFile = new Result(resultTask);
            } else {
                Result<String> result = monitorService.parseZip();
                if (result.isSuccess()) {
                    if (!CommonUtil.isEmpty(result.getCd())) {
                        log.info(funcName + " Success. Return :" + result.getCd());
                    }
                } else {
                    log.warn(ErrorConst.getWarningLogMsg(funcName, result));
                    retUploadFile = new Result(result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return new Result(ErrorConst.getExceptionResult(funcName, e));
        }

        log.debug(funcName + " Exit");
        return retUploadFile;
    }

    /**
     * Zip file is only supported for Test Run which has the TestSet & CaseId's locale
     * testRunId
     *
     * @param token
     * @param testRunId    Neighbour Test Run Id
     * @param targetLocale Test Run which has same TestSet and Test Case of testRunId and the locale specified
     * @param locale
     * @param request
     * @return
     */
    @PostMapping("/uploadReviewZipFile")
    public Result uploadReviewZipFile(@RequestParam(value = "token") String token,
                                      @RequestParam(value = "testRunId") Integer testRunId,
                                      @RequestParam(value = "targetLocale") Integer targetLocale,
                                      @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                      HttpServletRequest request) {
        String funcName = "ResultReviewController.uploadReviewZipFile()";
        log.debug(funcName + " Enter");
        log.info("testSetId = {}, targetLocale ={}", testRunId, targetLocale);

        Result<String> retSaveFile = new Result<String>();

//        try {
//            retSaveFile = fileService.uploadReviewZipFile(request, CommonDef.FOLDER_PATH_IMPORT_TESTCASE);
//            if (retSaveFile.hasError()) {
//                log.info(ErrorConst.getWarningLogMsg(funcName, retSaveFile));
//                return retSaveFile;
//            }

//            Integer nTestTestSetId = 435; // testSetId
//            if (testSetId != null && testSetId > 0) {
//                nTestTestSetId = testSetId;
//                String fileName = retSaveFile.getCd();
//
//                Result<List<TestRunExcelDTO>> retImportExcel = testRunService.importExcel(locale, nTestTestSetId, fileName);
//                if (retImportExcel.hasError()) {
//                    log.error(ErrorConst.getWarningLogMsg(funcName, retImportExcel));
//                    return retImportExcel;
//                } else {
//                    Integer nTestRunCount = 0;
//                    List<TestRunExcelDTO> listTestRun = retImportExcel.getCd();
//                    if (!CommonUtil.isEmpty(listTestRun)) {
//                        nTestRunCount = listTestRun.size();
//                    }
//                    retSaveFile.setCd(String.format("Test Run count: " + nTestRunCount));
//                }
//            } else {
//                return getResultArgs(locale, "000018", "TestSet ID");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
//            return new Result(ErrorConst.getExceptionResult(funcName, e));
//        }

        log.debug(funcName + " Exit");
        return retSaveFile;
    }

    /**
     * interface: /result/getTestResultInfo
     *
     * @param token
     * @param locale
     * @param testRunId
     * @return
     */
    @GetMapping("/getTestResultInfo")
    public Result getTestResultInfo(@RequestParam(value = "token") String token,
                                    @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                    @RequestParam(value = "testRunId") Integer testRunId) {
        String funcName = "ResultReviewController.getTestResultInfo()";
        log.debug(funcName + " Enter");
        log.debug("token = {}, locale = {}, testRunId = {}", token, locale, testRunId);

        Integer userId = 0;

        Result retTestResult = testResultService.getTestResultInfoByTestRunId(userId, locale, testRunId);
        if (retTestResult.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, retTestResult));
            return retTestResult;
        }

        log.debug(funcName + " Exit");
        return retTestResult;
    }

    /**
     * interface: /result/getTEPInfo
     *
     * @param token
     * @param locale
     * @param testSetId
     * @param tepId
     * @return
     */
    @RequestMapping("/getTEPInfo")
    public Result getTEPInfo(@RequestParam(value = "token") String token,
                             @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                             @RequestParam(value = "testSetId") Integer testSetId,
                             @RequestParam(value = "tepId", required = false, defaultValue = "") Integer tepId) {
        Result retResult = new Result();
        try {
            Integer userId = 1;
            Result<TEPInfoDTO> resultJenkins = jenkinsService.getTEPInfo(userId, locale, testSetId, tepId);
            if (resultJenkins.hasError()) {
                retResult = new Result(resultJenkins);
            } else {
                retResult = resultJenkins;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retResult;
    }


    @GetMapping("/getBugInfo")
    public Result getBugInfo(@RequestParam(value = "token") String token,
                             @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                             @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
                             @RequestParam(value = "screenshotId", required = false, defaultValue = "") Integer screenshotId) {
        String funcName = "ResultReviewController.getBugInfo()";
        Result retResult = new Result();

        Integer userId = 1;

        retResult = resultReviewService.getBugInfo(userId, locale, id, screenshotId);
        if (retResult.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, retResult));
        }

        return retResult;
    }

    @PostMapping("/saveBugInfo")
    public Result saveBugInfo(@RequestParam(value = "token") String token,
                              @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                              @RequestBody BugInfo data) {
        String funcName = "ResultReviewController.saveBugInfo()";
        Integer userId = 1;

        Result retResult = resultReviewService.saveBugInfo(userId, locale, data);
        if (retResult.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, retResult));
        }

        return retResult;
    }

    /**
     * interface: /screenShot/{id}?token=1&status=Pass/Failed/Conditional Pass
     *
     * @param token
     * @param locale
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/screenShot/{id}")
    public Result changeScreenShotStatus(@RequestParam(value = "token") String token,
                                         @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                         @PathVariable Integer id,
                                         @RequestParam String status) {
        String funcName = "ResultReviewController.changeScreenShotStatus()";
        Integer userId = 1;

        Result retResult = resultReviewService.changeScreenShotStatus(userId, locale, id, status);
        if (retResult.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, retResult));
        }

        return retResult;
    }

    @PostMapping("/screenShot/comment")
    public Result updateComment(@RequestParam(value = "token") String token,
                                @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
                                @RequestParam(value = "screenShotId", required = false, defaultValue = "") Integer screenShotId,
                                @RequestParam(value = "content") String content) {
        String funcName = "ResultReviewController.updateComment()";
        Integer userId = 1;
        Result retResult = resultReviewService.updateScreenShotComment(userId, locale, screenShotId, content);
        if (retResult.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, retResult));
        }
        return retResult;
    }

    @PostMapping("/screenShot/removeComment")
    public Result removeComment(@RequestParam(value = "token") String token,
                                @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                @RequestParam(value = "id") Integer id) {
        String funcName = "ResultReviewController.removeComment()";
        Integer userId = 1;
        Result retResult = resultReviewService.removeScreenShotComment(userId, locale, id);
        if (retResult.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, retResult));
        }
        return retResult;
    }

    @GetMapping("/screenShot/getComment")
    public Result getCommentInfo(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                 @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
                                 @RequestParam(value = "screenShotId", required = false, defaultValue = "") Integer screenShotId) {
        String funcName = "ResultReviewController.getCommentInfo()";
        Integer userId = 1;

        Result retResult = resultReviewService.getScreenShotComment(userId, locale, id, screenShotId);
        if (retResult.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, retResult));
        }
        return retResult;
    }

    //==================================================================================================================

    /**
     * 实现文件上传
     */
    @RequestMapping("fileUpload")
    // @ResponseBody
    public String fileUpload(@RequestParam("fileName") MultipartFile file) {
        log.info("ResultReviewController.fileUpload() Enter.");
        if (file.isEmpty()) {
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        String path = CommonDef.FOLDER_PATH_IMPORT;
        File dest = new File(path + "/" + fileName);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return "true";
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }

    @RequestMapping("/impExcel")
    public Result impTestCase(@Valid ImpTestCaseValidator cmd, BindingResult result, HttpServletRequest request) {
        log.info("ResultReviewController.impExcel() Enter.testSetId = {}", cmd.getTestSetId());

        Result retResult = new Result();

        String strFileName = "";
        boolean bUpload = false;
        //FileUploadService fileService = new FileUploadService();
        Result<String> retSaveFile = fileService.saveExcel(request, CommonDef.FOLDER_PATH_IMPORT);
        if (retSaveFile.isSuccess()) {
            strFileName = retSaveFile.getCd();
            //testCaseService.importData(cmd.getTestSetId(), strFileName);
        } else {
            retResult.setEc(retSaveFile.getEc());
            retResult.setEm(retSaveFile.getEm());
        }
        return retResult;
    }

    @RequestMapping("/importExcel")
    public Result importExcel(@RequestParam(value = "testSetId") Integer testSetId,
                              @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                              @RequestParam(value = "fileName") String fileName) {
        log.trace("ResultReviewController.importExcel() Enter");

        Result<String> retResult = new Result();
        try {
            Result<List<TestRunExcelDTO>> resultImportExcel = testRunService.importExcel(locale, testSetId, fileName);

            if (resultImportExcel.hasError()) {
                log.error(String.format("ec:%s, em:%s", resultImportExcel.getEc(), resultImportExcel.getEm()));
            }
            Integer nTestRunCount = 0;
            List<TestRunExcelDTO> listTestRun = resultImportExcel.getCd();

            if (!CommonUtil.isEmpty(listTestRun)) {
                nTestRunCount = listTestRun.size();
            }
            retResult.setCd(String.format("Test Run count: " + nTestRunCount));
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.trace("ResultReviewController.importExcel() Exit");
        return retResult;
    }

    @Deprecated
    @RequestMapping("/addProductIssue")
    public Result addProductIssue(@RequestParam(value = "productId") Integer productId) {
        log.trace("ResultReviewController.addBug() Enter");
        Result<String> retResult = new Result();

        if (productId != 0) {
            retResult = issueService.addProductIssue(productId);
        }

        return retResult;
    }

    @Deprecated
    @RequestMapping("/addNewCategory")
    public Result addNewCategory(@RequestParam(value = "productId") Integer productId) {
        log.trace("ResultReviewController.addBug() Enter");
        Result<String> retResult = new Result();

        if (productId != 0) {

        }

        return retResult;
    }
}