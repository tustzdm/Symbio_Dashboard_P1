package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.TestSet;
import com.symbio.dashboard.service.TestSetService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName - TestSetController
 * @Author - Admin
 * @Description
 * @Date - 2019/7/26 15:39
 * @Version 1.0
 */

@RequestMapping("/testmgmt")
@RestController
@Slf4j
public class TestSetController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(TestSetController.class);

    @Autowired
    private TestSetService testSetService;

    @RequestMapping("/getTestSetInfo")
    public Result getTestSetInfo(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                 @RequestParam(value = "id") Integer id) {
        Result result;
        try {
            Result retUserToken = getUserIdByToken(token);
            if (retUserToken.hasError()) {
                return retUserToken;
            }
            Integer userId = (Integer) retUserToken.getCd();

            result = testSetService.getTestSetInfo(userId, id);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult("000102", "TestSet Info");
        }
        return result;
    }

    @RequestMapping("/updateTestSet")
    public Result updateTestSet(@RequestParam(value = "token") String token,
                                @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                @RequestBody TestSet testSet) {
        Result result;
        try {
            // Set update user id
            Result retUserToken = getUserIdByToken(token);
            if (retUserToken.hasError()) {
                return retUserToken;
            }
            Integer userId = (Integer) retUserToken.getCd();
            testSet.setUpdateUser(userId);

            result = testSetService.updateTestSet(userId, locale, testSet);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult("000102", "Update TestSet");
        }
        return result;
    }

    @RequestMapping("/removeTestSet")
    public Result removeTestSet(@RequestParam(value = "token") String token,
                                @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                @RequestParam(value = "id") Integer id) {
        Result result;
        try {
            Result retUserToken = getUserIdByToken(token);
            if (retUserToken.hasError()) {
                return retUserToken;
            }
            Integer userId = (Integer) retUserToken.getCd();

            result = testSetService.removeTestSet(id);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult("000102", "Remove TestSet");
        }
        return result;
    }

    @RequestMapping("/getTestSetList")
    public Result getTestSetList(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "releaseId") Integer releaseId,
                                 @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                 @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Result retUserToken = getUserIdByToken(token);
        if (retUserToken.hasError()) {
            return retUserToken;
        }
        Integer userId = (Integer) retUserToken.getCd();

        Result result = testSetService.getTestSetList(userId, locale, releaseId, pageIndex, pageSize);
        if (result.hasError()) {
            return result;
        }
        return result;
    }

    @RequestMapping("/getTestCaseList")
    public Result getTestCaseList(@RequestParam(value = "token") String token,
                                  @RequestParam(value = "testSetId") Integer testSetId,
                                  @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                  @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Result retUserToken = getUserIdByToken(token);
        if (retUserToken.hasError()) {
            return retUserToken;
        }
        Integer userId = (Integer) retUserToken.getCd();

        Result result = testSetService.getTestCaseList(userId, locale, testSetId, pageIndex, pageSize);
        if (result.hasError()) {
            return result;
        }
        return result;
    }

    @RequestMapping("/getTestSetUiInfo")
    public Result getTestSetUiInfo(@RequestParam(value = "token") String token,
                                   @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                   @RequestParam(value = "uiInfo", required = false, defaultValue = "1") Integer uiInfo,
                                   @RequestParam(value = "id", required = false, defaultValue = "0") Integer id) {
        Result result;
        try {
            Result retUserToken = getUserIdByToken(token);
            if (retUserToken.hasError()) {
                return retUserToken;
            }
            Integer userId = (Integer) retUserToken.getCd();

            result = testSetService.getTestSetUiInfo(userId, locale, uiInfo, id);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult("000102", "TestSet UI Info");
        }

        return result;
    }

    @RequestMapping("/getTestSetChart")
    public Result getTestSetChart(@RequestParam(value = "token") String token,
                                  @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale) {
        Result retUserToken = getUserIdByToken(token);
        if (retUserToken.hasError()) {
            return retUserToken;
        }
        Integer userId = (Integer) retUserToken.getCd();

        return testSetService.getTestSetChart(userId, locale);
    }
}
