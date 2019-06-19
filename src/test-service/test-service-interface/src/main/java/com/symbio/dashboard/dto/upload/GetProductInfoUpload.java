package com.symbio.dashboard.dto.upload;

import lombok.Data;

@Data
public class GetProductInfoUpload {

    private String token;

    private String locale;

    private Integer uiInfo;

    private Integer productId;
}
