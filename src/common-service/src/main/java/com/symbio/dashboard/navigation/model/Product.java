package com.symbio.dashboard.navigation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 本类作为数据库product表的实体类
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private Integer id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "owner")
    private Integer owner;

    @Column(name = "manager")
    private Integer manager;

    @Column(name = "qa_lead")
    private Integer qa_lead;

    @Column(name = "dev_lead")
    private Integer dev_lead;

    @Column(name = "logo_id")
    private Integer logo_id;

    @Column(name = "logo_url")
    private String logo_url;

    @Column(name = "status",nullable = false)
    private Integer status;

    @Column(name = "display",nullable = false)
    private Integer display;

    @Column(name = "description")
    private String description;

    @Column(name = "locale")
    private String locale;

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
