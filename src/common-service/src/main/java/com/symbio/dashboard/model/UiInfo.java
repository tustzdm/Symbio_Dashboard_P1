package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName - UiInfo
 * @Author - Danny
 * @Description - 此类是数据库ui_info的实体类
 * @Date - 2019/7/5 16:50
 * @Version 1.0
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "ui_info")
public class UiInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "page", nullable = false)
    private String page;

    @Column(name = "`key`", nullable = false)
    private String key;

    @Column(name = "`type`", nullable = false)
    private String type = "text";

    @Column(name = "data")
    private String data = null;

    @Column(name = "disp_status")
    private Integer dispStatus = 0;

    @Column(name = "is_required")
    private Integer isRequired = 1;

    @Column(name = "is_disable")
    private Integer isDisable = 0;

    @Column(name = "en_us")
    private String enUs = null;

    @Column(name = "zh_cn")
    private String zhCn = null;

    @Column(name = "place_holder")
    private String placeHolder = "";

    @Column(name = "label", nullable = false)
    private String label = "";

    @Column(name = "default_value")
    private String defaultValue = null;

    @Column(name = "const_condition")
    private String constCondition = null;

    @Column(name = "idx", nullable = false)
    private Integer idx = 99;

    @Column(name = "version")
    private String version = null;

    @Column(name = "validation", nullable = false)
    private Integer validation = 1;

    @Column(name = "display", nullable = false)
    private Integer display = 1;
}