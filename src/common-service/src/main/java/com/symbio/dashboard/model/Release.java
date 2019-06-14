package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 本类作为数据库release表的实体类
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "releases")
public class Release {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private Integer id;

    @Column(name = "product_id",nullable = false)
    private Integer product_id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "status",nullable = false)
    private Integer status = 0;

    @Column(name = "start_time")
    private Date start_time;

    @Column(name = "end_time")
    private Date end_time;

    @Column(name = "display",nullable = false)
    private Integer display = 1;

    @Column(name = "remark")
    private String remark;

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
