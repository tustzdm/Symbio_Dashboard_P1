package com.symbio.dashboard.dto.message;

import lombok.Data;

@Data
public class ReleaseMessageData {

    private Integer id;

    private String name;

    private Integer status = 0;

    private String start;

    private String end;

    private String description;

}
