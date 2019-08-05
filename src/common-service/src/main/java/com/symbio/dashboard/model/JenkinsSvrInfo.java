package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName - JenkinsSvrInfo
 * @Author - admin
 * @Description - jenkins_svr_info实体类
 * @Date - 2019/8/5 15:27
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "jenkins_svr_info")
public class JenkinsSvrInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "url")
    private String url;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "jobname", nullable = false)
    private String jobname;

    @Column(name = "jobview")
    private String jobview;

    @Column(name = "jobConfigXml")
    private String jobConfigXml;

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
