package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.TestRunVO;
import com.symbio.dashboard.service.TestRunServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private static Logger logger = LoggerFactory.getLogger(ResultReviewController.class);

    @Autowired
    private TestRunServiceImpl testRunService;

    @RequestMapping("/getList")
    public Result getList(@RequestBody TestRunVO testRun) {
        logger.trace("ResultReviewController.getList() Enter");

        Result retResult = new Result();
        Integer userId = 0;
        testRun.setUserId(userId);
        retResult = testRunService.getTestRunList(testRun.getLocale(), testRun);

        retResult = testRunService.getTestRunList(testRun.getLocale(), testRun);
        if (retResult.hasError()) {
            logger.error(retResult.getErrorInfo());
        }

        logger.trace("ResultReviewController.getList() Exit");
        return retResult;
    }



}
