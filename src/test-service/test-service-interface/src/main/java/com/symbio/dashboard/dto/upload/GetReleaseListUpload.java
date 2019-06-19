package com.symbio.dashboard.dto.upload;

import lombok.Data;

@Data
public class GetReleaseListUpload {

    private String token;

    private String locale;

    private Integer productId;

    private Integer pageIndex;

    private Integer pageSize;

}
