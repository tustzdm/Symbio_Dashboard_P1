package com.symbio.dashboard.dto.upload;

import lombok.Data;

@Data
public class GetReleaseInfoUpload {

    private String token;

    private String locale;

    private Integer uiInfo;

    private Integer productId;

    private Integer releaseId;
}
