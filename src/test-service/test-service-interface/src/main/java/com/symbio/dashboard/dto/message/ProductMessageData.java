package com.symbio.dashboard.dto.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductMessageData {

    private Integer id;

    private String name;

    private String owner;

    private String manager;

    private String qaleader;

    private String devleader;

    private String description;
}
