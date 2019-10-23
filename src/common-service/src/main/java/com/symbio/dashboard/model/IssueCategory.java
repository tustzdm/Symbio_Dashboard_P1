package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName - IssueCategory
 * @Author - admin
 * @Description
 * @Date - 2019/8/27 16:23
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "issue_category")
public class IssueCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "case_type", nullable = false)
    private Integer caseType;

    @Column(name = "category")
    private String category;

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
