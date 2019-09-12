package com.symbio.dashboard.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 本类作为数据库 group_info 表的实体类
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "group_info")
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "isSysGroup", nullable = false)
    private Integer isSysGroup;

    @Column(name = "groupType", nullable = false)
    private Integer groupType;

    @Column(name = "companyId", nullable = false)
    private Integer companyId;

    @Column(name = "productId", nullable = false)
    private Integer productId;

    @Column(name = "description")
    private String description;

    @Column(name = "validation", nullable = false)
    private Integer validation;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "updateTime")
    private Date updateTime;

}
