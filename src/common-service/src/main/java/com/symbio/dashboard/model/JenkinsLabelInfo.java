package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 本类作为数据库 jenkins_label_info 表的实体类
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "jenkins_label_info")
public class JenkinsLabelInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "jjhMainId", nullable = false)
    private Integer jjhMainId;

    @Column(name = "name")
    private String name;

    @Column(name = "labelFormat")
    private String labelFormat;

    @Column(name = "labelStatus")
    private Integer labelStatus;

    @Column(name = "testRunCount")
    private Integer testRunCount;

    @Column(name = "locales")
    private String locales;

    @Column(name = "startTime")
    private Date startTime;

    @Column(name = "endTime")
    private Date endTime;

    @Column(name = "description")
    private String description;

    @Column(name = "display")
    private Integer display;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "createUserId")
    private Integer createUserId;

    @Column(name = "createUserName")
    private String createUserName;

    @Column(name = "updateTime")
    private Date updateTime;

    @Column(name = "updateUserId")
    private Integer updateUserId;

    @Column(name = "updateUserName")
    private String updateUserName;

}
