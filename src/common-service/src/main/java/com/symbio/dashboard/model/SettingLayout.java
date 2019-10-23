package com.symbio.dashboard.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 本类是数据库setting_layout的实体类
 */

@Getter
@Setter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name = "setting_layout")
public class SettingLayout implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false)
    private Integer id;

    @Column(name = "page" , nullable = false)
    private String page;

    /**
     * 0: System support 1: Admin Setting
     * 默认为1
     */
    @Column(name = "type" , nullable = false)
    private Integer type = 1;

    @Column(name = "locale" , nullable = false)
    private String locale;

    @Column(name = "layout")
    private String layout;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_time")
    private Date createTime;


}
