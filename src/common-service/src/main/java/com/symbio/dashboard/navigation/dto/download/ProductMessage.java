package com.symbio.dashboard.navigation.dto.download;

import lombok.Data;

@Data
public class ProductMessage {

    private String locale;

    private String role;

    private Integer isShowMore;

    private ProductData data;
}
