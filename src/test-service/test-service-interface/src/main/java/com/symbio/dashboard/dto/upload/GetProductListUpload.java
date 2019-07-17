package com.symbio.dashboard.dto.upload;

import lombok.Data;

import java.util.Date;

@Data
public class GetProductListUpload {

    private String token;

    private String locale;

    private Integer pageIndex;

    private Integer pageSize;

    private Integer id;

    private String name;

    private Integer owner;

    private Integer manager;

    private Integer qaLead;

    private Integer devLead;

    private Integer logoId;

    private String logoUrl;

    private Integer status;

    private Integer display;

    private String description;

    private Date createTime;

    private Integer createUser;

    private String createUserName;

    private Date updateTime;

    private Integer updateUser;

    private String updateUserName;
}
