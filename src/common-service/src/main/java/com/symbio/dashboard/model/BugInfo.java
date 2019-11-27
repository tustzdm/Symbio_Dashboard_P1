package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName - BugInfo
 * @Description - bug_info 实体类
 * @Date - 2019/11/08
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "bug_info")
public class BugInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "test_result_id")
    private Integer testResultId;

    @Column(name = "screen_shot_id")
    private Integer screenShotId;

    @Column(name = "title")
    private String title;
    @Column(name = "bug_priority")
    private String bugPriority;
    @Column(name = "bug_type")
    private String bugType;

    @Column(name = "issue_category_id")
    private Integer issueCategoryId;
    @Column(name = "issue_reason_id")
    private Integer issueReasonId;

    @Column(name = "jira_project_id")
    private Integer jiraProjectId;
    @Column(name = "jira_labels")
    private String jiraLabels;
    @Column(name = "locale")
    private String locale;

    @Column(name = "affection_version")
    private String affectionVersion;
    @Column(name = "dev_feature")
    private String devFeature;
    @Column(name = "component")
    private String component;

    @Column(name = "assignee")
    private Integer assignee;
    @Column(name = "verifier")
    private Integer verifier;
    @Column(name = "description")
    private String description;

    @Column(name = "file_path")
    private String filePath;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "thumbnail_file_path")
    private String thumbnailFilePath;
    @Column(name = "thumbnail_file_name")
    private String thumbnailFileName;
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    /*
    @Column(name = "")
    private Integer;
    @Column(name = "")
    private Integer;
    @Column(name = "")
    private Integer;
    @Column(name = "")
    private Integer;
    @Column(name = "")
    private Integer;

    @Column(name = "")
    private String;
    @Column(name = "")
    private String;
    @Column(name = "")
    private String;
    @Column(name = "")
    private String;
    @Column(name = "")
    private String;
    @Column(name = "")
    private String;
*/

    @Column(name = "validation", nullable = false)
    private Integer validation;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;
}
