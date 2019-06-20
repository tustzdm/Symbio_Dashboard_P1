package com.symbio.dashboard.dto.message;

import lombok.Data;

import java.util.List;

@Data
public class GetTestSetListMessage {

    private String locale;

    private String role;

    private Integer pageIndex;

    private Integer pageSize;

    private Long totalRecord;

    private Integer releaseId;

    private List<TestSetMessageData> data;
}
