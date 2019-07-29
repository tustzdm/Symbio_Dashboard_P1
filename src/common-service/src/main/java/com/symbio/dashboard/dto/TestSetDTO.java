package com.symbio.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName - TestSetDTO
 * @Author - admin
 * @Description - TODO
 * @Date - 2019/7/29 14:01
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestSetDTO implements Serializable {

    private List projectName;
    private List releaseName;
    private String testSetName;
    private List testType;
    private List status;
    private Date startDate;
    private Date endDate;
    private String testOwner;
    private String jira;
    private String bugAssignee;
    private String description;
    private String locale;

}
