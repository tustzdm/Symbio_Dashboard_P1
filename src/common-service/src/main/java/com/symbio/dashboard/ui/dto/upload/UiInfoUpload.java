package com.symbio.dashboard.ui.dto.upload;

import lombok.Data;

/**
 * @ClassName - UiInfoUpload
 * @Author - Danny
 * @Description - 得到页面元素信息的上送信息
 * @Date - 2019/7/5 11:23
 * @Version 1.0
 */

@Data
public class UiInfoUpload {

    private String token;

    private Integer id;

    private String page;

    private String key;

    private String type;

    private String data;

    private Integer dispStatus = 0;

    private Integer isRequired = 1;

    private Integer isDisable = 0;

    private String enUs = null;

    private String zhCn = null;

    private String placeHolder = "";

    private String label = "";

    private String defaultValue = null;

    private String constCondition = null;

    private Integer idx = 99;

    private String version = null;

    private Integer validation = 1;

    private Integer display = 1;
}