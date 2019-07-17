package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 本类作为数据库 sys_list_setting 表的实体类
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "sys_list_setting")
public class SysListSetting extends DBModel {

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
    private Integer is_entity = 1;

    @Column(name = "attribute_field")
    private Integer attribute_field = 1;

    @Column(name = "custfld_int1")
    private Integer custfld_int1;
    @Column(name = "custfld_int2")
    private Integer custfld_int2;
    @Column(name = "custfld_int3")
    private Integer custfld_int3;

    @Column(name = "`custfld_str1`")
    private String custfld_str1;
    @Column(name = "`custfld_str2`")
    private String custfld_str2;
    @Column(name = "`custfld_str3`")
    private String custfld_str3;
    @Column(name = "`custfld_str4`")
    private String custfld_str4;
    @Column(name = "`custfld_str5`")
    private String custfld_str5;
    @Column(name = "`custfld_str6`")
    private String custfld_str6;

    @Column(name = "validation", nullable = false)
    private Integer validation = 1;

    @Column(name = "description")
    private String description;
}
