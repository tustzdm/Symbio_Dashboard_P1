package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.TestRunVO;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.service.TestRunService;
import com.symbio.dashboard.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    private TestRunService testRunService;

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

            if (retResult.hasError()) {
                log.error(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        log.trace("ResultReviewController.getList() Exit");
        return retResult;
    }



}
