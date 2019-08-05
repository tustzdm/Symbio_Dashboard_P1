package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName - JenkinsJobHistoryDetail
 * @Author - admin
 * @Description - jenkins_job_history_detail实体类
 * @Date - 2019/8/5 16:41
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "jenkins_job_history_detail")
public class JenkinsJobHistoryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "mainId", nullable = false)
    private Integer mainId;

    @Column(name = "testSetId", nullable = false)
    private Integer testSetId;

    @Column(name = "testRunId", nullable = false)
    private Integer testRunId;

    @Column(name = "testCaseId", nullable = false)
    private Integer testCaseId;

    @Column(name = "locale")
    private String locale;

    @Column(name = "display", nullable = false)
    private Integer display;

    @Column(name = "updateTime", nullable = false)
    private Date updateTime;

}
