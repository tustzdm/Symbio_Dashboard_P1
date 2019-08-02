package com.symbio.dashboard.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class JenkinsBean implements Serializable {

    private String name;
    private String type; // enum JenkinsParameter
    private String description;
    private String defaultValue;
    private boolean isDelete = true ;

    public JenkinsBean() {
    }

    public JenkinsBean(String name, String description, String defaultValue, String type) {
        super();
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
        this.type = type;
    }
}
