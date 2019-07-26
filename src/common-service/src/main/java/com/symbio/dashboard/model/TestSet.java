package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 本类作为数据库test_set表的实体类
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "test_set")
public class TestSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "testset_field_bool1")
    private Short testsetFieldBool1;

    @Column(name = "testset_field_bool2")
    private Short testsetFieldBool2;

    @Column(name = "testset_field_int1")
    private Integer testsetFieldInt1;

    @Column(name = "testset_field_int2")
    private Integer testsetFieldInt2;

    @Column(name = "testset_field_int3")
    private Integer testsetFieldInt3;

    @Column(name = "testset_field_int4")
    private Integer testsetFieldInt4;

    @Column(name = "testset_field_int5")
    private Integer testsetFieldInt5;

    @Column(name = "testset_field_str1")
    private String testsetFieldStr1;

    @Column(name = "testset_field_str2")
    private String testsetFieldStr2;

    @Column(name = "testset_field_str3")
    private String testsetFieldStr3;

    @Column(name = "testset_field_str4")
    private String testsetFieldStr4;

    @Column(name = "testset_field_str5")
    private String testsetFieldStr5;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user",nullable = false)
    private Integer createUser;

    @Column(name = "create_user_name")
    private String createUserName;

    @Column(name = "update_time",nullable = false)
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
