package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.common.CommonAuthService;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.setting.service.CommonServiceImpl;
import com.symbio.dashboard.setting.service.ResultMessageServiceImpl;
import com.symbio.dashboard.setting.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName - BaseController
 * @Author - admin
 * @Description - 控制器
 * @Date - 2019/7/24
 * @Version 1.0
 */
public class BaseController {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private ResultMessageServiceImpl messageService;

    public Result getResult(String code) {
        return messageService.getResultEx(code);
    }

    public Result getResult(String code, Object... args) {
        return messageService.getResultEx(code, args);
    }

    public Result getResultArgs(String locale, String code, Object... args) {
        return messageService.getResult(locale, code, args);
    }
}
