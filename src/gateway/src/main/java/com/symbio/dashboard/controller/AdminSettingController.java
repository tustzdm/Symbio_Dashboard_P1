package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.symbio.dashboard.setting.service.CommonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName - AdminSettingController
 * @Author - admin
 * @Description - 控制器
 * @Date - 2019/7/16
 * @Version 1.0
 */
@RequestMapping("/setting")
@RestController
@Slf4j
public class AdminSettingController {

    @Autowired
    private CommonService commonService;


    @RequestMapping("/getDictionary")
    public Result getDictionary(@RequestParam(value = "token") String token,
                                @RequestParam(value = "type") String type) {
        return commonService.getDictionaryByType(type);
    }
}
