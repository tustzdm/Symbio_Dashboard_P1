package com.symbio.dashboard.dto.message;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
public class TestSetMessageData {

    private Integer id;

    private String name;

    private Integer type;

    private Integer status;

    private String startDate;

    private String endDate;

    private String testOwner;

    private String jiraProject;

    private String bugAssignee;

    private String description;

    private String extend;
}
