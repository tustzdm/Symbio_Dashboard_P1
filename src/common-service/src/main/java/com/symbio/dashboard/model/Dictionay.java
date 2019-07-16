package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName - Dictionay
 * @Author - Shawn
 * @Description - 此类是数据库dictionary的实体类
 * @Date - 2019/7/16
 * @Version 1.0
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "dictionary")
public class Dictionay extends DBModel{

    @Column(name = "`type`")
    private String type;

    @Column(name = "`code`", nullable = false)
    private String code;

    @Column(name = "`value`")
    private String value;

    @Column(name = "validation", nullable = false)
    private Integer validation = 1;

    @Column(name = "description")
    private String description = "";
}