package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 本类作为数据库 relation_test_case_method 表的实体类
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "relation_test_case_method")
public class RelationTestCaseMethod implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "testCaseId", nullable = false)
    private Integer testCaseId;

    @Column(name = "packageInfo")
    private String packageInfo;

    @Column(name = "className")
    private String className;

    @Column(name = "methodName")
    private String methodName;

    @Column(name = "srcFile")
    private String srcFile;

    @Column(name = "locales")
    private String locales;

    @Column(name = "display")
    private Integer display;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "createUserId")
    private Integer createUserId;

    @Column(name = "createUserName")
    private String createUserName;

    @Column(name = "updateTime")
    private Date updateTime;

    @Column(name = "updateUserId")
    private Integer updateUserId;

    @Column(name = "updateUserName")
    private String updateUserName;

}
