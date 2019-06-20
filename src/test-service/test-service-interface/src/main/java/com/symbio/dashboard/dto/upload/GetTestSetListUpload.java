package com.symbio.dashboard.dto.upload;

import lombok.Data;

@Data
public class GetTestSetListUpload {

    private String token;

    private String locale;

    private Integer releaseId;

    private Integer pageIndex;

    private Integer pageSize;

}
