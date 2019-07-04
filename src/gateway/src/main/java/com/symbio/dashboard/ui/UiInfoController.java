package com.symbio.dashboard.ui;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.ui.dto.upload.UiInfoUpload;
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
    private GetUiInfoListService getUiInfoListService;

    @Autowired
    private UpdateUiElementAuthService updateUiElementAuthService;
    @Autowired
    private UpdateUiElementService updateUiElementService;

    @Autowired
    private RemoveUiElementAuthService removeUiElementAuthService;
    @Autowired
    private RemoveUiElementService removeUiElementService;

    /**
     * @return com.symbio.dashboard.Result
     * @Author - Danny
     * @Description - 返回页面所有UI元素
     * @Date - 2019/7/5
     * @Param - [token, page]
     * <p>
     * 测试接口：
     * localhost:8080/ui/getUiInfoList?token=111&page=product
     */
    @RequestMapping("/getUiInfoList")
    public Result getUiInfoList(@RequestParam(value = "token") String token,
                                @RequestParam(value = "page") String page) {

        Result result = getUiInfoListAuthService.getUiInfoListAuth(token);
        if (!result.isSuccess()) {
            return result;
        }

        UiInfoUpload uiInfoUpload = new UiInfoUpload();
        uiInfoUpload.setPage(page);

        result = getUiInfoListService.getUiInfoList(uiInfoUpload);
        if (!result.isSuccess()) {
            return result;
        }

        return result;
    }

    /**
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
    public Result updateUiElement(@RequestBody UiInfoUpload uiInfoUpload) {

        Result result = updateUiElementAuthService.updateUiElementAuth(uiInfoUpload.getToken());
        if (!result.isSuccess()) {
            return result;
        }

        result = updateUiElementService.updateUiElement(uiInfoUpload);
        if (!result.isSuccess()) {
            return result;
        }

        return result;
    }

    /**
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
                                  @RequestParam(value = "id") Integer id) {

        Result result = removeUiElementAuthService.removeUiElementAuth(token);
        if (!result.isSuccess()) {
            return result;
        }

        UiInfoUpload uiInfoUpload = new UiInfoUpload();
        uiInfoUpload.setId(id);

        result = removeUiElementService.removeUiElement(uiInfoUpload);
        if (!result.isSuccess()) {
            return result;
        }

        return result;
    }
}