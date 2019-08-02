package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.common.CommonAuthService;
import com.symbio.dashboard.model.UiInfo;
import com.symbio.dashboard.setting.service.CommonService;
import com.symbio.dashboard.setting.service.PageElementService;
import com.symbio.dashboard.ui.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName - UiInfoController
 * @Author - admin
 * @Description - UI Info 控制器
 * @Date - 2019/7/5 15:30
 * @Version 1.0
 */
@RequestMapping("/ui")
@RestController
@Slf4j
public class UiInfoController {

    @Autowired
    private GetUiInfoListAuthService getUiInfoListAuthService;

    @Autowired
    private CommonAuthService commonAuthService;
    @Autowired
    private CommonService commonService;

    @Autowired
    private UpdateUiElementAuthService updateUiElementAuthService;

    @Autowired
    private RemoveUiElementAuthService removeUiElementAuthService;

    @Autowired
    private PageElementService pageElementService;

    /**
     * @InterfaceNumber 5.1
     *
     * @return com.symbio.dashboard.Result
     * @Author - Danny
     * @Description - 5.1 返回页面所有UI元素
     * @Date - 2019/7/5
     * @Param - [token, page]
     * <p>
     * 测试接口：
     * localhost:8080/ui/getUiInfoList?token=111&page=product
     */
    @RequestMapping("/getUiInfoList")
    public Result getUiInfoList(@RequestParam(value = "token") String token,
                                @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                @RequestParam(value = "page") String page) {
        Result result;
        try {
            result = getUiInfoListAuthService.getUiInfoListAuth(token);
            if (!result.isSuccess()) {
                return result;
            }

//            UiInfoUpload uiInfoUpload = new UiInfoUpload();
//            uiInfoUpload.setPage(page);

            result = pageElementService.getUiInfoList(locale, page);
            if (!result.isSuccess()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("10010", "Get UI Element list Interface Exception");
        }

        return result;
    }

    /**
     * @InterfaceNumber 5.2
     *
     * @return com.symbio.dashboard.Result
     * @Author - Danny
     * @Description - 添加或跟新页面元素接口
     * @Date - 2019/7/9
     * @Param - [uiInfoUpload]
     * <p>
     * 测试接口：
     * localhost:8080/ui/getUiInfoList?token=111
     */
    @RequestMapping("/updateUiElement")
    public Result updateUiElement(@RequestParam(value = "token") String token,
                                  @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                  @RequestBody UiInfo data) {
        Result result;
        try {
//            result = updateUiElementAuthService.updateUiElementAuth(uiInfoUpload.getToken());
//            if (!result.isSuccess()) {
//                return result;
//            }

            Integer userId = 1;
            result = pageElementService.updateUiElement(userId, locale, data);
            if (!result.isSuccess()) {
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result("10010", "Update/Save Info Interface Exception");
        }
        return result;
    }

    /**
     * @InterfaceNumber 5.3
     *
     * @return com.symbio.dashboard.Result
     * @Author - Danny
     * @Description - 删除页面元素接口
     * @Date - 2019/7/9
     * @Param - [token, id]
     * <p>
     * 测试接口：
     * localhost:8080/ui/removeUiElement?token=111&id=20
     */
    @RequestMapping("/removeUiElement")
    public Result removeUiElement(@RequestParam(value = "token") String token,
                                  @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                  @RequestParam(value = "id") Integer id) {
        Result result;
        try {
            result = removeUiElementAuthService.removeUiElementAuth(token);
            if (!result.isSuccess()) {
                return result;
            }

            result = pageElementService.removeUiElement(locale, id);
            if (!result.isSuccess()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("10010", "Remove UI Element Interface Exception");
        }

        return result;
    }
}