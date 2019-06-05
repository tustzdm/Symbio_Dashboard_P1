package com.symbio.dashboard.navigation.dto.upload;


import lombok.Data;

/**+
 * 导航条release的上送信息
 */

@Data
public class ReleaseUpload {

    /**
     * 用户token
     */
    private String token;

    /**
     * 语种
     */
    private String locale;

    /**
     * product id
     */
    private Integer productId;

    /**
     * 取得release的记录数
     */
    private Integer total;
}