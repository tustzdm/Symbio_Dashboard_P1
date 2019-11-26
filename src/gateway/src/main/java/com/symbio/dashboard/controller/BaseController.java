package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.auth.service.AuthUserServiceImpl;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.setting.service.ResultMessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName - BaseController
 * @Author - admin
 * @Description - 控制器
 * @Date - 2019/7/24
 * @Version 1.0
 */
public class BaseController {

    private static Logger log = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private ResultMessageServiceImpl messageService;
    @Autowired
    private AuthUserServiceImpl authUserSvr;


    public Result getResult(String code) {
        return messageService.getResultEx(code);
    }

    public Result getResult(String code, Object... args) {
        return messageService.getResultEx(code, args);
    }

    public Result getResultArgs(String locale, String code, Object... args) {
        return messageService.getResult(locale, code, args);
    }

    public Result<User> checkUserToken(String token) {
        String funcName = "BaseController.checkUserToken()";

        Result<User> authUser = authUserSvr.getUserInfo(token);
        if (authUser.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, authUser));
        }
        return authUser;
    }

    public Result getUserIdByToken(String token) {
        String funcName = "BaseController.getUserIdByToken()";

        Result retUser = new Result<>();

        Result<User> authUser = authUserSvr.getUserInfo(token);
        if (authUser.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, authUser));
            return authUser;
        }

        User userInfo = authUser.getCd();
        return new Result(userInfo.getId());
    }

    public Result<Integer> getUserMenuRole(String page, Integer userId) {
        String funcName = "BaseController.getUserRoleByPage()";

        Result<Integer> resultRole = authUserSvr.getUserRole(page, userId);
        if (resultRole.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, resultRole));
            return resultRole;
        }
        return resultRole;
    }
}
