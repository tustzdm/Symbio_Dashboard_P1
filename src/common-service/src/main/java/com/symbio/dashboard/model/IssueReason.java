package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName - IssueReason
 * @Author - admin
 * @Description
 * @Date - 2019/8/27 16:24
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "issue_reason")
public class IssueReason implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "issue_category_id", nullable = false)
    private Integer issueCategoryId;

    @Column(name = "reason")
    private String reason;

    @Column(name = "issue_level")
    private Integer issueLevel;

    @Column(name = "idx", nullable = false)
    private Integer idx;

    @Column(name = "description")
    private String description;

    @Column(name = "validation")
    private Integer validation;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "createUserId")
    private Integer createUserId;

    @Column(name = "createUserName")
    private String createUserName;

    @Column(name = "updateTime", nullable = false)
    private Date updateTime;
}
