package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.common.CommonAuthService;
import com.symbio.dashboard.setting.service.CommonServiceImpl;
import com.symbio.dashboard.setting.service.SettingService;
import com.symbio.dashboard.setting.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName - AdminSettingController @Description - 控制器 @Date - 2019/7/16 @Version 1.0
 */
@RequestMapping("/setting")
@RestController
@Slf4j
public class AdminSettingController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AdminSettingController.class);

    @Autowired
    private CommonAuthService commonAuthService;

    @Autowired
    private CommonServiceImpl commonService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SettingService settingService;

    @RequestMapping("/getDictionary")
    public Result getDictionary(
            @RequestParam(value = "token") String token, @RequestParam(value = "type") String type) {
        // Step1: Check user token
        Result retUserToken = getUserIdByToken(token);
        if (retUserToken.hasError()) {
            return retUserToken;
        }
        Integer userId = (Integer) retUserToken.getCd();

        return commonService.getDictionaryByType(type);
    }

    @RequestMapping("/getUiInfoPage")
    public Result getUiInfoList(
            @RequestParam(value = "token") String token, @RequestParam(value = "type") String type) {
        Result result;
        try {
            // Step1: Check user token
            Result retUserToken = getUserIdByToken(token);
            if (retUserToken.hasError()) {
                return retUserToken;
            }
            Integer userId = (Integer) retUserToken.getCd();

            result = commonAuthService.getPageNamesDictionary(token);
            if (result.hasError()) {
                return result;
            }

            result = commonService.getDictionaryInfo(type);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult("200001");
        }

        return result;
    }

    @RequestMapping("/getDBFields")
    public Result getDBFields(
            @RequestParam(value = "token") String token,
            @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
            @RequestParam(value = "table") String table) {
        Result result;
        try {
            // Step1: Check user token
            Result retUserToken = getUserIdByToken(token);
            if (retUserToken.hasError()) {
                return retUserToken;
            }
            Integer userId = (Integer) retUserToken.getCd();

            result = commonService.getUserDefinedFields(locale, table);
            if (result.hasError()) {
                logger.error(String.format("ec:%s, em:%s", result.getEc(), result.getEm()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult("001001", table);
        }

        return result;
    }

    /**
     * @param token
     * @param locale
     * @param status
     * @return
     */
    @RequestMapping("/getUserList")
    public Result getUserList(
            @RequestParam(value = "token") String token,
            @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
            @RequestParam(value = "status", required = false, defaultValue = "1") Integer status) {
        Result result;
        try {
            // Step1: Check user token
            Result retUserToken = getUserIdByToken(token);
            if (retUserToken.hasError()) {
                return retUserToken;
            }
            Integer userId = (Integer) retUserToken.getCd();

            result = new Result(userService.getUserListByStatus(locale, status));
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult("000120", "user list");
        }
        return result;
    }

    @GetMapping("/getRole")
    public Result getRole(@RequestParam(value = "token") String token,
                          @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale) {
        Result result = null;

        try {
            // Step1: Check user token
            Result retUserToken = getUserIdByToken(token);
            if (retUserToken.hasError()) {
                return retUserToken;
            }
            Integer userId = (Integer) retUserToken.getCd();

            result = settingService.getRoleInfo(locale, userId);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult("000120", "role info list");
        }
        return result;
    }

    @GetMapping("/getRoleDetailInfo")
    public Result getRoleInfo(@RequestParam(value = "token") String token,
                              @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                              @RequestParam(value = "roleId") Integer roleId,
                              @RequestParam(value = "menuId") Integer menuId) {
        Result result = null;

        try {
            // Step1: Check user token
            Result retUserToken = getUserIdByToken(token);
            if (retUserToken.hasError()) {
                return retUserToken;
            }
            Integer userId = (Integer) retUserToken.getCd();

            result = settingService.getRoleDetailInfo(locale, userId, roleId, menuId);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult("000120", "role detail info");
        }
        return result;
    }

    @PostMapping("/saveRole")
    public Result saveRole(@RequestParam(value = "token") String token,
                           @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                           @RequestParam(value = "roleId") Integer roleId,
                           @RequestParam(value = "menuId") Integer menuId, @RequestParam(value = "value") Integer value) {
        Result result = null;

        try {
            // Step1: Check user token
            Result retUserToken = getUserIdByToken(token);
            if (retUserToken.hasError()) {
                return retUserToken;
            }
            Integer userId = (Integer) retUserToken.getCd();

            result = settingService.saveRoleInfo(locale, userId, roleId, menuId, value);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult("000120", "save role info");
        }
        return result;
    }
}
