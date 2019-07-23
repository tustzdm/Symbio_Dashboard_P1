package com.symbio.dashboard.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 本类作为数据库product表的实体类
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
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

    // 0-normal,1-abnormal,2-completed,3-archived,4-blocked
    @Column(name = "status", nullable = false)
    private Integer status;

    // 0-hide, 1-show
    @Column(name = "display", nullable = false)
    private Integer display;

    @Column(name = "description")
    private String description;

    @Column(name = "locale")
    private String locale;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user")
    private Integer createUser;

    @Column(name = "create_user_name")
    private String createUserName;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    @Column(name = "update_user")
    private Integer updateUser;

    @Column(name = "update_user_name")
    private String updateUserName;

    public Product(Integer status, Integer display) {
        this.status = status;  // default: 0
        this.display = display; // default: 1
    }
}
