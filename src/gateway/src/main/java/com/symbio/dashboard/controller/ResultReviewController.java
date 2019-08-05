package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.TestRunVO;
import com.symbio.dashboard.common.CommonAuthService;
import com.symbio.dashboard.setting.service.CommonServiceImpl;
import com.symbio.dashboard.setting.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/getList")
    public Result getList(@RequestParam(value = "token") String token,
                          @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale, @RequestBody TestRunVO testRun) {
        logger.trace("ResultReviewController.getList() Enter");

        Result retResult = new Result();
        testRun.setToken(token);
        testRun.setLocale(locale);

        Integer userId = 0;
        testRun.setUserId(userId);

        if (retResult.hasError()) {
            logger.error(retResult.getErrorInfo());
        }

        logger.trace("ResultReviewController.getList() Exit");
        return retResult;
    }



}
