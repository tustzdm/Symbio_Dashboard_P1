package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName - JenkinsJobParameter
 * @Author - admin
 * @Description - jenkins_job_parameter实体类
 * @Date - 2019/8/5 15:34
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "jenkins_svr_info")
public class JenkinsJobParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "jsiId", nullable = false)
    private Integer jsiId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "refType", nullable = false)
    private String refType;

    @Column(name = "description")
    private String description;

    @Column(name = "defaultValue")
    private String defaultValue;

    @Column(name = "display", nullable = false)
    private Integer display;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "createUserId", nullable = false)
    private Integer createUserId;

    @Column(name = "createUserName")
    private String createUserName;

    @Column(name = "updateTime", nullable = false)
    private Date updateTime;

    @Column(name = "updateUserId")
    private Integer updateUserId;

    @Column(name = "updateUserName")
    private String updateUserName;

}
