package com.symbio.dashboard.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 此类是数据库language_ui的实体类
 */

@Getter
@Setter
@Table(name = "language_ui")
@Entity
public class LanguageUI {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id" , nullable = false)
    private Integer id;

    @Column(name = "page" , nullable = false)
    private String page;

    @Column(name = "key" , nullable = false)
    private String key;

    @Column(name = "type" , nullable = false)
    private String type;

    @Column(name = "data")
    private String data = null;

    @Column(name = "label" , nullable = false)
    private String label;

    @Column(name = "default_value")
    private String default_value = null;

    @Column(name = "idx" , nullable = false)
    private Integer idx;

    @Column(name = "version" )
    private String version = null;

    @Column(name = "validation" , nullable = false)
    private Integer validation = 1;
}
