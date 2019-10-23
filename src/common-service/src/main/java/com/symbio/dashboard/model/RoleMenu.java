package com.symbio.dashboard.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 本类作为数据库 role_menu 表的实体类
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "role_menu")
public class RoleMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "roleId", nullable = false)
    private Integer roleId;

    @Column(name = "menuId", nullable = false)
    private Integer menuId;

    @Column(name = "createTime")
    private Date createTime;

}
