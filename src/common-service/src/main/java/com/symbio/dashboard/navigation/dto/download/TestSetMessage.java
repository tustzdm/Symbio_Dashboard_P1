package com.symbio.dashboard.navigation.dto.download;

import lombok.Data;

/**
 * 导航条testset的返回信息
 */

@Data
public class TestSetMessage {

    /**
     * 语种
     */
    private String locale;

    /**
     *product id
     */
    private Integer productId;

    /**
     * release id
     */
    private Integer releaseId;

    /**
     * 是否显示更多信息
     */
    private Boolean isShowMore;

    /**
     * 权限 1-R 2-W 3-X
     */
    private String role;

    /**
     * TestSet 的列表信息
     */
    private TestSetData data;

}
