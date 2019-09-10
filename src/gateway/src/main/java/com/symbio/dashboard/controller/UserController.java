package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.common.CommonAuthService;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.setting.service.CommonService;
import com.symbio.dashboard.setting.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName - UserController
 * @Author - Shawn
 * @Description - User 控制器
 * @Date - 2019/9/10
 * @Version 1.0
 */
@RequestMapping("/user")
@RestController
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private CommonAuthService commonAuthService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private UserService userService;

    /**
     * @return Result
     * @InterfaceNumber
     * @Author - Shawn
     * @Description -
     * @Date - 2019/9/10
     * @Param -
     * <p>
     * 测试接口：
     * localhost:9090/user/saveUserInfo
     */
    @RequestMapping("/saveUserInfo")
    public Result saveUserInfo(
            @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale, @RequestBody User userInfo) {
        Result result = new Result();
        String funcName = "UserController.saveUserInfo()";

        try {
            result = userService.saveUserInfo(locale, userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return ErrorConst.getExceptionResult(funcName, e);
        }

        return result;
    }
}
