package com.symbio.dashboard.navigation.dto.download;

import lombok.Data;

@Data
public class NavigationInfoMessage {

    private String locale;

    private String role;

    private Integer isShowMore;

    private Integer productId;

    private Integer releaseId;
}
