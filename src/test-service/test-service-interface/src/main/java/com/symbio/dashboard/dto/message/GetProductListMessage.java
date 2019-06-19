package com.symbio.dashboard.dto.message;

import lombok.Data;

import java.util.List;

@Data
public class GetProductListMessage {

    private String locale;

    private String role;

    private Integer pageIndex;

    private Integer pageSize;

    /**
     *总记录数
     */
    private Long totalRecord;

    private List<ProductMessageData> data;
}
