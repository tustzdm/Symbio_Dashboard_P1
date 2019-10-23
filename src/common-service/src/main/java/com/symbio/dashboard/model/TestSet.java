package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 本类作为数据库test_set表的实体类
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "test_set")
public class TestSet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;

    @Column(name = "release_id",nullable = false)
    private Integer releaseId;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "status",nullable = false)
    private Integer status;

    @Column(name = "display", nullable = false)
    private Integer display;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;


    @Column(name = "test_owner")
    private Integer testOwner;

    @Column(name = "jira_project")
    private String jiraProject;

    @Column(name = "bug_assignee")
    private Integer bugAssignee;

    @Column(name = "description")
    private String description;

    /**
     * locales for the test set
     */
    @Column(name = "locales")
    private String locales;

    @Column(name = "tsfield_int1")
    private Integer tsfieldInt1;
    @Column(name = "tsfield_int2")
    private Integer tsfieldInt2;
    @Column(name = "tsfield_int3")
    private Integer tsfieldInt3;
    @Column(name = "tsfield_int4")
    private Integer tsfieldInt4;
    @Column(name = "tsfield_int5")
    private Integer tsfieldInt5;
    @Column(name = "tsfield_str1")
    private String tsfieldStr1;
    @Column(name = "tsfield_str2")
    private String tsfieldStr2;
    @Column(name = "tsfield_str3")
    private String tsfieldStr3;
    @Column(name = "tsfield_str4")
    private String tsfieldStr4;
    @Column(name = "tsfield_str5")
    private String tsfieldStr5;
    @Column(name = "tsfield_str6")
    private String tsfieldStr6;
    @Column(name = "tsfield_str7")
    private String tsfieldStr7;
    @Column(name = "tsfield_str8")
    private String tsfieldStr8;
    @Column(name = "tsfield_str9")
    private String tsfieldStr9;
    @Column(name = "tsfield_str10")
    private String tsfieldStr10;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user")
    private Integer createUser;

    @Column(name = "create_user_name")
    private String createUserName;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "update_user")
    private Integer updateUser;

    @Column(name = "update_user_name")
    private String updateUserName;

    public TestSet(Integer status, Integer display) {
        this.status = status;  // default: 0
        this.display = display; // default: 1
    }
}
