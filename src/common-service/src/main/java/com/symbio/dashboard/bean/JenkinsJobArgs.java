package com.symbio.dashboard.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class JenkinsJobArgs implements Serializable {

    private Integer id;
    private String name;
    private String type; // enum JenkinsParameter

    private String defaultValue;
    private String description;
    private String lastRunValue;
    private List<String> choiceList;

}
