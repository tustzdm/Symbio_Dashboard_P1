package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.ListQueryVO;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.service.BugService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName - BugController
 * @Description - User 控制器
 * @Date - 2019/11/29
 * @Version 1.0
 */

@RequestMapping("/bug")
@RestController
@Slf4j
public class BugController extends BaseController {

    @Autowired
    private BugService bugService;

    @GetMapping("/getList")
    public Result getList(@RequestParam(value = "token") String token,
                          @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                          @RequestParam(value = "productId", required = false, defaultValue = "") Integer productId,
                          @RequestParam(value = "releaseId", required = false, defaultValue = "") Integer releaseId,
                          @RequestParam(value = "testSetId", required = false, defaultValue = "") Integer testSetId,
                          @RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        String funcName = "BugController.getList()";
        log.info(funcName + " Enter");

        Result retResult = new Result();
        try {
            Result resultUserId = super.getUserIdByToken(token);
            if (resultUserId.hasError()) {
                return resultUserId;
            }
            Integer userId = (Integer) resultUserId.getCd();

            ListQueryVO query = new ListQueryVO(token, locale, productId, releaseId, testSetId, pageIndex, pageSize);
            retResult = bugService.getList(userId, query);
            if (retResult.hasError()) {
                log.error(ErrorConst.getErrorLogMsg(funcName, retResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.trace(funcName + " Exit");
        return retResult;
    }

    @GetMapping("/getPieData")
    public Result getPieData(@RequestParam(value = "token") String token,
                             @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                             @RequestParam(value = "productId", required = false, defaultValue = "") Integer productId,
                             @RequestParam(value = "releaseId", required = false, defaultValue = "") Integer releaseId,
                             @RequestParam(value = "testSetId", required = false, defaultValue = "") Integer testSetId) {
        String funcName = "BugController.getPieData()";
        Result retResult = new Result();

        try {
            Result resultUserId = super.getUserIdByToken(token);
            if (resultUserId.hasError()) {
                return resultUserId;
            }
            Integer userId = (Integer) resultUserId.getCd();


            ListQueryVO query = new ListQueryVO(token, locale, productId, releaseId, testSetId, null, null);
            retResult = bugService.getPieChartData(userId, query);
            if (retResult.hasError()) {
                log.error(ErrorConst.getErrorLogMsg(funcName, retResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.trace(funcName + " Exit");
        return retResult;
    }

    @GetMapping("/getBarData")
    public Result getBarData(@RequestParam(value = "token") String token,
                             @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                             @RequestParam(value = "productId", required = false, defaultValue = "") Integer productId,
                             @RequestParam(value = "releaseId", required = false, defaultValue = "") Integer releaseId,
                             @RequestParam(value = "testSetId", required = false, defaultValue = "") Integer testSetId) {
        String funcName = "BugController.getBarData()";
        Result retResult = new Result();

        try {
            Result resultUserId = super.getUserIdByToken(token);
            if (resultUserId.hasError()) {
                return resultUserId;
            }
            Integer userId = (Integer) resultUserId.getCd();


            ListQueryVO query = new ListQueryVO(token, locale, productId, releaseId, testSetId, null, null);
            retResult = bugService.getBarChartData(userId, query);
            if (retResult.hasError()) {
                log.error(ErrorConst.getErrorLogMsg(funcName, retResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.trace(funcName + " Exit");
        return retResult;
    }

}
