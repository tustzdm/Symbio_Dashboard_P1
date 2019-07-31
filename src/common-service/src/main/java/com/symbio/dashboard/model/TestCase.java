package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 本类作为数据库test_case表的实体类
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "test_case")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "case_id", nullable = false)
    private String caseId;

    @Column(name = "case_type", nullable = false)
    private Integer caseType;

    @Column(name = "summary")
    private String summary;

    @Column(name = "priority")
    private String priority;

    @Column(name = "feature_area")
    private String featureArea;
    @Column(name = "sub_feature_area")
    private String subFeatureArea;

    @Column(name = "case_status")
    private Integer caseStatus;

    @Column(name = "class_name")
    private String className;

    @Column(name = "case_parameter")
    private String caseParameter;

    @Column(name = "template_id")
    private String templateId;

    @Column(name = "detail_steps")
    private String detailSteps;

    @Column(name = "expected_results")
    private String expectedResults;

    @Column(name = "support_locales")
    private String supportLocales;

    @Column(name = "display")
    private Integer display;

    @Column(name = "case_owner")
    private String caseOwner;

    @Column(name = "description")
    private String description;

    @Column(name = "validation")
    private Integer validation;

    @Column(name = "tcfield_bool1")
    private Integer tcfieldBool1;
    @Column(name = "tcfield_bool2")
    private Integer tcfieldBool2;
    @Column(name = "tcfield_bool3")
    private Integer tcfieldBool3;
    @Column(name = "tcfield_int1")
    private Integer tcfieldInt1;
    @Column(name = "tcfield_int2")
    private Integer tcfieldInt2;
    @Column(name = "tcfield_int3")
    private Integer tcfieldInt3;
    @Column(name = "tcfield_int4")
    private Integer tcfieldInt4;
    @Column(name = "tcfield_int5")
    private Integer tcfieldInt5;

    @Column(name = "tcfield_str1")
    private String tcfieldStr1;
    @Column(name = "tcfield_str2")
    private String tcfieldStr2;
    @Column(name = "tcfield_str3")
    private String tcfieldStr3;
    @Column(name = "tcfield_str4")
    private String tcfieldStr4;
    @Column(name = "tcfield_str5")
    private String tcfieldStr5;
    @Column(name = "tcfield_str6")
    private String tcfieldStr6;
    @Column(name = "tcfield_str7")
    private String tcfieldStr7;
    @Column(name = "tcfield_str8")
    private String tcfieldStr8;
    @Column(name = "tcfield_str9")
    private String tcfieldStr9;
    @Column(name = "tcfield_str10")
    private String tcfieldStr10;

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

}
