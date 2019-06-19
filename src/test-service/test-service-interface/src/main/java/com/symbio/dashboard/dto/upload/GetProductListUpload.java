package com.symbio.dashboard.dto.upload;

import lombok.Data;

@Data
public class GetProductListUpload {

    private String token;

    private String locale;

    private Integer pageIndex;

    private Integer pageSize;
}
