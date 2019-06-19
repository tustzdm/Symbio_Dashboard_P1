package com.symbio.dashboard.dto.message;


import lombok.Data;

import java.util.List;

@Data
public class GetReleaseListMessage {

    private String locale;

    private String role;

    private Integer pageIndex;

    private Integer pageSize;

    private Long totalRecord;

    private Integer productId;

    private List<ReleaseMessageData> data;

}
