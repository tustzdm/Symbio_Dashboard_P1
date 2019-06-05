package com.symbio.dashboard.navigation.dto.download;

import lombok.Data;

@Data
public class TestSetMessage {

    private String locale;

    private Integer productId;

    private Integer releaseId;

    private String role;

    private TestSetData data;

}
