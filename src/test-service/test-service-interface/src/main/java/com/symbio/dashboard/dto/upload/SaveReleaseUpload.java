package com.symbio.dashboard.dto.upload;


import lombok.Data;

@Data
public class SaveReleaseUpload {

    private String token;

    private String locale;

    private Integer productId;

    private Integer releaseId;

    private String name;

    private Integer status;

    private String start;

    private String end;

    private String description;
}
