package com.symbio.dashboard.navigation.dto.upload;


import lombok.Data;

/**
 * 导航条testset的上送信息
 */

@Data
public class TestSetUpload {

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
     * release id
     */
    private Integer releaseId;

    /**
     * testset记录数
     */
    private Integer total;
}
