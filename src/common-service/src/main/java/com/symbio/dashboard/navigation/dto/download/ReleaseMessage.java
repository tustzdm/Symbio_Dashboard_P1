package com.symbio.dashboard.navigation.dto.download;

import lombok.Data;

import java.util.List;

/**
 * 导航条release的返回信息
 */
@Data
public class ReleaseMessage {

    /**
     * 语种
     */
    private String locale;

    /**
     * product的id
     */
    private Integer productId;

    /**
     * 权限 1-R 2-W 4-X
     */
    private String role;

    /**
     * 是否显示更多信息 0：false 1：true
     */
    private Boolean isShowMore;

    /**
     * release信息列表
     */
    private List<ReleaseData> data;
}
