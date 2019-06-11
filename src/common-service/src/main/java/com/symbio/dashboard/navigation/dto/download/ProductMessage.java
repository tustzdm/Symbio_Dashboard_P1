package com.symbio.dashboard.navigation.dto.download;

import lombok.Data;

import java.util.List;

/**
 * 导航条product的返回信息
 */

@Data
public class ProductMessage {

    /**
     * 语种
     */
    private String locale;

    /**
     * 权限 1-R 2-W 4-X
     */
    private String role;

    /**
     * 是否显示更多信息 0：false，1：true
     */
    private Boolean isShowMore;

    /**
     * product信息列表
     */
    private List<ProductData> data;
}
