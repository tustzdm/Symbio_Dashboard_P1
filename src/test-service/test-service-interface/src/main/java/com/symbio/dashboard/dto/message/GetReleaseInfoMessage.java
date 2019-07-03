package com.symbio.dashboard.dto.message;

import lombok.Data;

import java.util.List;

@Data
public class GetReleaseInfoMessage {

    private String locale;

    private String role;

    private MessageUiInfo uiInfo;

    private List<ReleaseMessageData> data;

}
