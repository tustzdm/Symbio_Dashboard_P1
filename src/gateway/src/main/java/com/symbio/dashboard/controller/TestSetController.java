package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.TestSet;
import com.symbio.dashboard.service.TestSetService;
import com.symbio.dashboard.service.TestSetServiceImpl;
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
        Integer userId = 0;
        Result result;
        try {
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
            result = testSetService.updateTestSet(testSet);
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
        Integer userId = 0;
        Result result = testSetService.getTestSetList(userId, locale, releaseId, pageIndex, pageSize);
        if (result.hasError()) {
            return result;
        }
        return result;
    }
}