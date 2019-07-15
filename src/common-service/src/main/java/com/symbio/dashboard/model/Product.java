package com.symbio.dashboard.model;

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
public class Product extends DBCreateUpdateModel {

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "owner")
    private Integer owner;

    @Column(name = "manager")
    private Integer manager;

    @Column(name = "qa_lead")
    private Integer qaLead;

    @Column(name = "dev_lead")
    private Integer devLead;

    @Column(name = "logo_id")
    private Integer logoId;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "status", nullable = false)
    private Integer status = 0;

    @Column(name = "display", nullable = false)
    private Integer display = 1;

    @Column(name = "description")
    private String description;

    @Column(name = "locale")
    private String locale;

}
