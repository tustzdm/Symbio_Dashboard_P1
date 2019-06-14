package com.symbio.dashboard.dto.upload;

import lombok.Data;

@Data
public class SaveProductUpload {

    private String token;

    private String locale;

    private Integer productId;

    private String name;

    private Integer owner;

    private Integer manager;

    private Integer qaleader;

    private Integer devleader;

    private String description;

}
