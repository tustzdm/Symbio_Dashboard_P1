package com.symbio.dashboard.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 本类作为数据库 role_setting 表的实体类
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "role_setting")
public class RoleSetting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private Integer code;

    @Column(name = "role", nullable = false)
    private Integer role;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "validation", nullable = false)
    private Integer validation;

    @Column(name = "description")
    private String description;

}
