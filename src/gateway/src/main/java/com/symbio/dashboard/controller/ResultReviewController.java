package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.TestRunVO;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.dto.TestRunExcelDTO;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.service.FileUploadService;
import com.symbio.dashboard.service.TestRunService;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.StringUtil;
import com.symbio.dashboard.validator.ImpTestCaseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
public class ResultReviewController extends BaseController {

    @Autowired
    private TestRunService testRunService;
    @Autowired
    private FileUploadService fileService;

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

    @RequestMapping("/upload")
    public Result upload(HttpServletRequest request) {
        log.debug("ResultReviewController.upload() Enter.");

        Result<String> retsaveFile = fileService.saveExcel(request, CommonDef.FOLDER_PATH_IMPORT_TESTCASE);
        return retsaveFile;
    }

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
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            // TODO Auto-generated catch block
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

            if (retResult.hasError()) {
                log.error(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
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
}