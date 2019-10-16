package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.TestRunVO;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.dto.TEPInfoDTO;
import com.symbio.dashboard.dto.TestRunExcelDTO;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.jenkins.JenkinsService;
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

    @RequestMapping("/getList")
    public Result getList(@RequestBody TestRunVO testRun) {
        log.trace("ResultReviewController.getList() Enter");

        Result retResult = new Result();
        try {
            Integer userId = 0;
            testRun.setUserId(userId);
            if (StringUtil.isEmpty(testRun.getLocale())) {
                testRun.setLocale(Locales.EN_US.toString());
            }

            retResult = testRunService.getTestRunList(testRun.getLocale(), testRun);
//            retResult = testRunService.importExcel(1,);

            if (retResult.hasError()) {
                log.error(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.trace("ResultReviewController.getList() Exit");
        return retResult;
    }

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

    @RequestMapping("/uploadReviewZipFile")
    public Result uploadReviewZipFile(@RequestParam(value = "token") String token,
                                      @RequestParam(value = "testRunId") Integer testRunId,
                                      @RequestParam(value = "targetLocale") Integer targetLocale,
                                      @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                      HttpServletRequest request) {
        String funcName = "ResultReviewController.uploadReviewZipFile()";
        log.debug(funcName + " Enter");
        log.info("testSetId = {}, targetLocale ={}", testRunId, targetLocale);

        Result<String> retSaveFile = new Result<String>();

        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return new Result(ErrorConst.getExceptionResult(funcName, e));
        }

        log.debug(funcName + " Exit");
        return retSaveFile;
    }

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