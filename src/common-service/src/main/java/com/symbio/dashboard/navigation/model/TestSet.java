package com.symbio.dashboard.navigation.model;

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
    private Integer release_id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "status",nullable = false)
    private Integer status;

    @Column(name = "start_time")
    private Date start_time;

    @Column(name = "end_time")
    private Date end_time;


    @Column(name = "test_owner")
    private Integer test_owner;

    @Column(name = "jira_project")
    private String jira_project;

    @Column(name = "bug_assignee")
    private Integer bug_assignee;

    @Column(name = "description")
    private String description;

    /**
     * locales for the test set
     */
    @Column(name = "locales")
    private String locales;

    @Column(name = "testset_field_bool1")
    private Short testset_field_bool1;

    @Column(name = "testset_field_bool2")
    private Short testset_field_bool2;

    @Column(name = "testset_field_int1")
    private Integer testset_field_int1;

    @Column(name = "testset_field_int2")
    private Integer testset_field_int2;

    @Column(name = "testset_field_int3")
    private Integer testset_field_int3;

    @Column(name = "testset_field_int4")
    private Integer testset_field_int4;

    @Column(name = "testset_field_int5")
    private Integer testset_field_int5;

    @Column(name = "testset_field_str1")
    private String testset_field_str1;

    @Column(name = "testset_field_str2")
    private String testset_field_str2;

    @Column(name = "testset_field_str3")
    private String testset_field_str3;

    @Column(name = "testset_field_str4")
    private String testset_field_str4;

    @Column(name = "testset_field_str5")
    private String testset_field_str5;

    @Column(name = "create_time")
    private Date create_time;

    @Column(name = "create_user",nullable = false)
    private Integer create_user;

    @Column(name = "create_user_name")
    private String create_user_name;

    @Column(name = "update_time",nullable = false)
    private Date update_time;

    @Column(name = "update_user")
    private Integer update_user;

    @Column(name = "update_user_name")
    private String update_user_name;
}
