package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName - TestRun
 * @Author - admin
 * @Description - test_run实体类
 * @Date - 2019/8/5 10:24
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "test_run")
public class TestRun implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "testset_id", nullable = false)
    private Integer testsetId;

    @Column(name = "testcase_id", nullable = false)
    private Integer testcaseId;

    @Column(name = "locale")
    private String locale;

    @Column(name = "display")
    private Integer display;

    @Column(name = "description")
    private String description;

    @Column(name = "run_model")
    private Integer runModel;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "screenshot_flag", nullable = false)
    private Integer screenshotFlag;

    @Column(name = "app_path")
    private String appPath;

    @Column(name = "run_engineer_id")
    private Integer runEngineerId;

    @Column(name = "run_qa_id")
    private Integer runQaId;

    @Column(name = "run_tep_id")
    private Integer runTepId;

    @Column(name = "trunfield_bool1")
    private Integer trunfieldBool1;

    @Column(name = "trunfield_bool2")
    private Integer trunfieldBool2;

    @Column(name = "trunfield_bool3")
    private Integer trunfieldBool3;

    @Column(name = "trunfield_int1")
    private Integer trunfieldInt1;

    @Column(name = "trunfield_int2")
    private Integer trunfieldInt2;

    @Column(name = "trunfield_int3")
    private Integer trunfieldInt3;

    @Column(name = "trunfield_int4")
    private Integer trunfieldInt4;

    @Column(name = "trunfield_int5")
    private Integer trunfieldInt5;

    @Column(name = "trunfield_str1")
    private String trunfieldStr1;

    @Column(name = "trunfield_str2")
    private String trunfieldStr2;

    @Column(name = "trunfield_str3")
    private String trunfieldStr3;

    @Column(name = "trunfield_str4")
    private String trunfieldStr4;

    @Column(name = "trunfield_str5")
    private String trunfieldStr5;

    @Column(name = "trunfield_str6")
    private String trunfieldStr6;

    @Column(name = "trunfield_str7")
    private String trunfieldStr7;

    @Column(name = "trunfield_str8")
    private String trunfieldStr8;

    @Column(name = "trunfield_str9")
    private String trunfieldStr9;

    @Column(name = "trunfield_str10")
    private String trunfieldStr10;

    @Column(name = "validation", nullable = false)
    private Integer validation;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user")
    private Integer createUser;

    @Column(name = "create_user_name")
    private String createUserName;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    @Column(name = "update_user")
    private Integer updateUser;

    @Column(name = "update_user_name")
    private String updateUserName;
}
