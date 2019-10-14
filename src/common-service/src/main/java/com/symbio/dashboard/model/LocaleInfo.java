package com.symbio.dashboard.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 此类是数据库locales_info的实体类
 */

@Getter
@Setter
@Table(name = "locales_info")
@Entity
public class LocaleInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "language")
    private String language;
    @Column(name = "region")
    private String region;

    @Column(name = "en_us", nullable = false)
    private String enUs;
    @Column(name = "zh_cn")
    private String zhCn;

    @Column(name = "regionInfo")
    private String regionInfo;
    @Column(name = "description")
    private String description;

    @Column(name = "validation", nullable = false)
    private Integer validation;
}
