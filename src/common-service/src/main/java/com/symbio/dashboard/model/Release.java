package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 本类作为数据库release表的实体类
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "`release`")
public class Release implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;

    @Column(name = "product_id",nullable = false)
    private Integer productId;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "status",nullable = false)
    private Integer status;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "display",nullable = false)
    private Integer display;

    @Column(name = "remark")
    private String remark;

    @Column(name = "relfld_int1")
    private Integer relfldInt1;
    @Column(name = "relfld_int2")
    private Integer relfldInt2;
    @Column(name = "relfld_int3")
    private Integer relfldInt3;
    @Column(name = "relfld_int4")
    private Integer relfldInt4;
    @Column(name = "relfld_int5")
    private Integer relfldInt5;

    @Column(name = "relfld_str1")
    private Integer relfldStr1;
    @Column(name = "relfld_str2")
    private Integer relfldStr2;
    @Column(name = "relfld_str3")
    private Integer relfldStr3;
    @Column(name = "relfld_str4")
    private Integer relfldStr4;
    @Column(name = "relfld_str5")
    private Integer relfldStr5;
    @Column(name = "relfld_str6")
    private Integer relfldStr6;
    @Column(name = "relfld_str7")
    private Integer relfldStr7;
    @Column(name = "relfld_str8")
    private Integer relfldStr8;
    @Column(name = "relfld_str9")
    private Integer relfldStr9;
    @Column(name = "relfld_str10")
    private Integer relfldStr10;

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

    public Release(Integer status, Integer display) {
        this.status = status;  // default: 0
        this.display = display; // default: 1
    }
}
