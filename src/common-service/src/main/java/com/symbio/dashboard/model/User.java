package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName - User
 * @Author -
 * @Description - 此类是数据库[user]的实体类
 * @Date - 2019/7/19
 * @Version 1.0
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "`user`")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "`passwd`", nullable = false)
    private String passwd;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "portrait_id")
    private Integer portraitId = 1;

    @Column(name = "mobile")
    private String mobile;
    @Column(name = "locale")
    private String locale;

    //0-inactive，1-active, 4-delete
    @Column(name = "status")
    private Integer status = 1;
    // Account forbidden or not. 0-no，1-yes
    @Column(name = "disable")
    private Integer disable = 0;
    // Channel that user logged in. 0 - local, 1 - LDAP
    @Column(name = "channel")
    private Integer channel = 0;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "level_id")
    private Integer levelId = 0;
    @Column(name = "login")
    private Date login;
    @Column(name = "description")
    private String description;

    @Column(name = "userfld_int1")
    private Integer userfldInt1;
    @Column(name = "userfld_int2")
    private Integer userfldInt2;
    @Column(name = "userfld_int3")
    private Integer userfldInt3;

    @Column(name = "`userfld_str1`")
    private String userfldStr1;
    @Column(name = "`userfld_str2`")
    private String userfldStr2;
    @Column(name = "`userfld_str3`")
    private String userfldStr3;
    @Column(name = "`userfld_str4`")
    private String userfldStr4;
    @Column(name = "`userfld_str5`")
    private String userfldStr5;
    @Column(name = "`userfld_str6`")
    private String userfldStr6;

    @Column(name = "create_time")
    private Date  createTime;
    @Column(name = "update_time")
    private Date  updateTime;

}