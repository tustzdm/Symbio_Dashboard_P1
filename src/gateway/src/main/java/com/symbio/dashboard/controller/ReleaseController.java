package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.Release;
import com.symbio.dashboard.service.ReleaseService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName - ReleaseController
 * @Author - admin
 * @Description
 * @Date - 2019/7/26
 * @Version 1.0
 */

@RequestMapping("/testmgmt")
@RestController
@Slf4j
public class ReleaseController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ReleaseController.class);

    @Autowired
    private ReleaseService releaseService;

    @RequestMapping("/getReleaseInfo")
    public Result getReleaseInfo(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                 @RequestParam(value = "id") Integer id) {
        Integer userId = 0;
        Result result;
        try {
            result = releaseService.getReleaseInfo(userId, id);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult("000102", "Release Info");
        }
        return result;
    }

    @RequestMapping("/updateRelease")
    public Result updateRelease(@RequestParam(value = "token") String token,
                                @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                @RequestBody Release release) {
        Result result;
        try {
            result = releaseService.updateRelease(release);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult("000102", "Update Release");
        }
        return result;
    }

    @RequestMapping("/removeRelease")
    public Result removeRelease(@RequestParam(value = "token") String token,
                                @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                @RequestParam(value = "id") Integer id) {
        Result result;
        try {
            result = releaseService.removeRelease(id);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult("000102", "Remove Release");
        }
        return result;
    }

    @RequestMapping("/getReleaseList")
    public Result getReleaseList(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "id") Integer id,
                                 @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                 @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Integer userId = 0;
        Result result = null; // releaseService.getReleaseList(userId, locale, id, pageIndex, pageSize);
        if (result.hasError()) {
            return result;
        }
        return result;
    }
}
