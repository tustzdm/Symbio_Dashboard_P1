package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 本类作为数据库 sys_list_setting 表的实体类
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "sys_list_setting")
public class SysListSetting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "`key`", nullable = false)
    private String key;

    @Column(name = "`label`", nullable = false)
    private String label;

    @Column(name = "`type`", nullable = false)
    private String type = "text";

    // 0-undisplay, 1-show, 2-hide
    @Column(name = "display", nullable = false)
    private Integer display = 1;

    // 1-left,2-center,3-right
    @Column(name = "align", nullable = false)
    private Integer align = 1;

    @Column(name = "`field`", nullable = false)
    private String field = "";

    @Column(name = "formatter")
    private String formatter;

    @Column(name = "idx")
    private Integer idx = 99;

    @Column(name = "is_entity")
    private Integer isEntity = 1;

    @Column(name = "attribute_field")
    private Integer attributeField = 1;

    @Column(name = "custfld_int1")
    private Integer custfldIntd;
    @Column(name = "custfld_int2")
    private Integer custfldIntd2;
    @Column(name = "custfld_int3")
    private Integer custfldIntd3;

    @Column(name = "custfld_str1")
    private String custfldStr1;
    @Column(name = "custfld_str2")
    private String custfldStr2;
    @Column(name = "custfld_str3")
    private String custfldStr3;
    @Column(name = "custfld_str4")
    private String custfldStr4;
    @Column(name = "custfld_str5")
    private String custfldStr5;
    @Column(name = "custfld_str6")
    private String custfldStr6;

    @Column(name = "validation", nullable = false)
    private Integer validation = 1;

    @Column(name = "description")
    private String description;
}
