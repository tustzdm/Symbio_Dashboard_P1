package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName - JenkinsJobHistoryMain
 * @Author - admin
 * @Description - jenkins_job_history_main实体类
 * @Date - 2019/8/5 16:29
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "jenkins_job_history_main")
public class JenkinsJobHistoryMain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "testSetId", nullable = false)
    private Integer testSetId;

    @Column(name = "tepId")
    private Integer tepId;

    @Column(name = "jsiId", nullable = false)
    private Integer jsiId;

    @Column(name = "jobname")
    private String jobname;

    @Column(name = "jobpath")
    private String jobpath;

    @Column(name = "jenkinsJobParameter")
    private String jenkinsJobParameter;

    @Column(name = "status")
    private String status;

    @Column(name = "buildId")
    private Integer buildId;

    @Column(name = "parseCount")
    private Integer parseCount;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "display", nullable = false)
    private Integer display;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "createUserId", nullable = false)
    private Integer createUserId;

    @Column(name = "createUserName")
    private String createUserName;

}
