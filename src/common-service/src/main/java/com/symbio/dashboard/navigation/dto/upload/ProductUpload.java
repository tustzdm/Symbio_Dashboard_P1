package com.symbio.dashboard.navigation.dto.upload;

import lombok.Data;

/**
 *导航条的product上送信息
 */

@Data
public class ProductUpload {

    /**
     * 用户权限
     */
    private String token;

    /**
     * 语种
     */
    private String locale;

    /**
     * 取得product的记录数
     */
    private Integer total;
}
