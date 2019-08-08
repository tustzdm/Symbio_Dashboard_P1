package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName - TestResult
 * @Author - admin
 * @Description - test_result实体类
 * @Date - 2019/8/5 10:24
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "test_result")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "test_set_id", nullable = false)
    private Integer testSetId;

    @Column(name = "test_run_id", nullable = false)
    private Integer testRunId;

    @Column(name = "auto_run_status")
    private Integer autoRunStatus;

    @Column(name = "job_weather")
    private Integer jobWeather;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "start_utc_time")
    private Date startUtcTime;

    @Column(name = "time_zone")
    private float timeZone;

    @Column(name = "exec_duration")
    private Integer execDuration;

    @Column(name = "note")
    private String note;

    @Column(name = "exception_desc")
    private String exceptionDesc;

    @Column(name = "text_exception")
    private String textException;

    @Column(name = "text_stacktrace")
    private String textStacktrace;

    @Column(name = "qa_status")
    private String qaStatus;

    @Column(name = "browser")
    private Integer browser;

    @Column(name = "browsers_vesion")
    private String browsersVesion;

    @Column(name = "bug_report_id")
    private Integer bugReportId;

    @Column(name = "bug_report_content")
    private String bugReportContent;

    @Column(name = "bug_report_title")
    private String bugReportTitle;

    @Column(name = "issue_category_id")
    private Integer issueCategoryId;

    @Column(name = "issue_reason_id")
    private Integer issueReasonId;

    @Column(name = "mobile_device")
    private String mobileDevice;

    @Column(name = "tresult_field_bool1")
    private Integer tresultFieldBool1;

    @Column(name = "tresult_field_bool2")
    private Integer tresultFieldBool2;

    @Column(name = "tresult_field_bool3")
    private Integer tresultFieldBool3;

    @Column(name = "tresult_field_int1")
    private Integer tresultFieldInt1;

    @Column(name = "tresult_field_int2")
    private Integer tresultFieldInt2;

    @Column(name = "tresult_field_int3")
    private Integer tresultFieldInt3;

    @Column(name = "tresult_field_int4")
    private Integer tresultFieldInt4;

    @Column(name = "tresult_field_int5")
    private Integer tresultFieldInt5;

    @Column(name = "tresult_field_str1")
    private String tresultFieldStr1;

    @Column(name = "tresult_field_str2")
    private String tresultFieldStr2;

    @Column(name = "tresult_field_str3")
    private String tresultFieldStr3;

    @Column(name = "tresult_field_str4")
    private String tresultFieldStr4;

    @Column(name = "tresult_field_str5")
    private String tresultFieldStr5;

    @Column(name = "tresult_field_str6")
    private String tresultFieldStr6;

    @Column(name = "tresult_field_str7")
    private String tresultFieldStr7;

    @Column(name = "tresult_field_str8")
    private String tresultFieldStr8;

    @Column(name = "tresult_field_str9")
    private String tresultFieldStr9;

    @Column(name = "tresult_field_str10")
    private String tresultFieldStr10;

    @Column(name = "last_run_time")
    private Date lastRunTime;

    @Column(name = "display")
    private Integer display;

    @Column(name = "validation", nullable = false)
    private Integer validation;

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
}
