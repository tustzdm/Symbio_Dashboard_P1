package com.symbio.dashboard.dto.message;

import lombok.Data;

import java.util.List;

@Data
public class GetProductInfoMessage {

    private String locale;

    private String role;

    private MessageUiInfo uiInfo;

    private List<ProductMessageData> data;
}
