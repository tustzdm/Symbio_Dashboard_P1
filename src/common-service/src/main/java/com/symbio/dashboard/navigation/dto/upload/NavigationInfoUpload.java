package com.symbio.dashboard.navigation.dto.upload;

import lombok.Data;


/**
 * 得到导航条信息的上送信息
 */

@Data
public class NavigationInfoUpload {

    /**
     * 用户token
     */
    private String token;

    /**
     * 语种
     */
    private String locale;
}
