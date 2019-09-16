package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName - Menu
 * @Author -
 * @Description - 此类是数据库[menu]的实体类
 * @Date - 2019/8/2
 * @Version 1.0
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "menu")
public class Menu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "groupName")
    private String groupName;

    @Column(name = "logo")
    private String logo;

    @Column(name = "url")
    private String url;

    @Column(name = "idx", nullable = false)
    private Integer idx;

    @Column(name = "description")
    private String description;

    @Column(name = "parentId")
    private Integer parentId;

    @Column(name = "validation", nullable = false)
    private Integer validation;

    @Column(name = "createTime")
    private Date  createTime;

    @Column(name = "updateTime")
    private Date  updateTime;
}