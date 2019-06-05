package com.symbio.dashboard.navigation.dto.download;

import lombok.Data;

import java.util.List;

/**
 * 导航条信息的返回信息
 */

@Data
public class NavigationInfoMessage {

    /**
     * 语种
     */
    private String locale;

    /**
     * 权限 1-R 2-W 4-X
     */
    private String role;

    /**
     * product id
     */
    private Integer productId;

    /**
     * release id
     */
    private Integer releaseId;

    /**
     * product 信息列表
     */
    private List<ProductData> listProduct;

    /**
     * release 信息列表
     */
    private List<ReleaseData> listRelease;

    /**
     * testset 信息列表
     */
    private List<TestSetData> listTestSet;


}
