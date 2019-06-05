package com.symbio.dashboard.navigation.dto.download;

import lombok.Data;

@Data
public class ReleaseMessage {
    private String locale;

    private Integer productId;

    private String role;

    private Integer isShowMore;

    private ReleaseData data;
}
