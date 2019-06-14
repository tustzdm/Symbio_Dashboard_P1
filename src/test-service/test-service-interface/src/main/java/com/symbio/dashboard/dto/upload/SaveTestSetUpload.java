package com.symbio.dashboard.dto.upload;

import lombok.Data;

@Data
public class SaveTestSetUpload {

    private String token;

    private String locale;

    private Integer releaseId;

    private Integer testSetId;

    private String name;

    private Integer type;

    private Integer status;

    private String startDate;

    private String endDate;

    private Integer testOwner;

    private String jiraProduct;

    private Integer butAssignee;

    private String description;

    private String extend;
}
