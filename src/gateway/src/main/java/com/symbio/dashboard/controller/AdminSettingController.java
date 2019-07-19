package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.common.CommonAuthService;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.setting.service.CommonService;
import com.symbio.dashboard.setting.service.CommonServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    private static Logger logger = LoggerFactory.getLogger(AdminSettingController.class);

    @Autowired
    private CommonAuthService commonAuthService;

    @Autowired
    private CommonServiceImpl commonService;

    @RequestMapping("/getDictionary")
    public Result getDictionary(@RequestParam(value = "token") String token,
                                @RequestParam(value = "type") String type) {
        return commonService.getDictionaryByType(type);
    }

    @RequestMapping("/getUiInfoPage")
    public Result getUiInfoList(@RequestParam(value = "token") String token,
                                @RequestParam(value = "type") String type) {
        Result result;
        try {
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
            return new Result("10010", "Get page name list interface exception");
        }

        return result;
    }

    @RequestMapping("/getDBFields")
    public Result getDBFields(@RequestParam(value = "token") String token,
                                @RequestParam(value = "table") String table) {
        Result result;
        try {
            result = new Result(commonService.getUserDefinedFields(table));
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("10010", String.format("Could not get Table[%s] field info", table));
        }

        return result;
    }
}
