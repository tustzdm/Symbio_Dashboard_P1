package com.symbio.dashboard.report.modle;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 本类是数据库setting_layout的实体类
 */

@Getter
@Setter
@Entity
@DynamicInsert
@NoArgsConstructor
@Table(name = "setting_layout")
public class SettingLayout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "updateTime")
    private Date updateTime;

    @Column(name = "createTime")
    private Date createTime;


}
