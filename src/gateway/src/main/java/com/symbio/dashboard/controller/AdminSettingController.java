package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.common.CommonAuthService;
import com.symbio.dashboard.dictionary.dto.upload.DictionaryUpload;
import com.symbio.dashboard.setting.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Autowired
    private CommonAuthService commonAuthService;

    @Autowired
    private CommonService commonService;

    @RequestMapping("/getUiInfoPage")
    public Result getUiInfoList(@RequestBody DictionaryUpload dictionaryUpload) {
        Result result;
        try {
            result = commonAuthService.getPageNamesDictionary(dictionaryUpload.getToken());
            if (result.hasError()) {
                return result;
            }

            result = commonService.getDictionaryInfo(dictionaryUpload);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("10010", "Get page name list interface exception");
        }

        return result;
    }
}
