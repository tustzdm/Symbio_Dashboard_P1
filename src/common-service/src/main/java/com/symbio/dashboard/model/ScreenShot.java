package com.symbio.dashboard.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 本类是数据库 screen_shot 的实体类
 */

@Getter
@Setter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name = "screen_shot")
public class ScreenShot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "testRunId", nullable = false)
    private Integer testRunId;
    @Column(name = "status")
    private Integer status;
    @Column(name = "step")
    private Integer step;

    @Column(name = "source")
    private String source;
    @Column(name = "image")
    private String image;
    @Column(name = "message")
    private String message;
    @Column(name = "httpFilePath")
    private String httpFilePath;
    @Column(name = "sourceFilePath")
    private String sourceFilePath;
    @Column(name = "sourceFileName")
    private String sourceFileName;
    @Column(name = "sourceFileOriginalName")
    private String sourceFileOriginalName;
    @Column(name = "sourceFileSize")
    private Integer sourceFileSize;
    @Column(name = "originalName")
    private String originalName;
    @Column(name = "filePath")
    private String filePath;
    @Column(name = "fileName")
    private String fileName;
    @Column(name = "fileSize")
    private Integer fileSize;
    @Column(name = "thumbnailFilePath")
    private String thumbnailFilePath;
    @Column(name = "thumbnailFileName")
    private String thumbnailFileName;
    @Column(name = "thumbnailFileSize")
    private Integer thumbnailFileSize;
    @Column(name = "description")
    private String description;
    @Column(name = "jiraTicketId")
    private String jiraTicketId;
    @Column(name = "jiraTicket")
    private String jiraTicket;
    @Column(name = "qaComment")
    private String qaComment;

    @Column(name = "ssfield_int1")
    private Integer ssfieldInt1;
    @Column(name = "ssfield_int2")
    private Integer ssfieldInt2;
    @Column(name = "ssfield_int3")
    private Integer ssfieldInt3;
    @Column(name = "ssfield_int4")
    private Integer ssfieldInt4;
    @Column(name = "ssfield_int5")
    private Integer ssfieldInt5;
    @Column(name = "ssfield_str1")
    private String ssfieldStr1;
    @Column(name = "ssfield_str2")
    private String ssfieldStr2;
    @Column(name = "ssfield_str3")
    private String ssfieldStr3;
    @Column(name = "ssfield_str4")
    private String ssfieldStr4;
    @Column(name = "ssfield_str5")
    private String ssfieldStr5;
    @Column(name = "ssfield_review1")
    private String ssfieldReview1;
    @Column(name = "ssfield_review2")
    private String ssfieldReview2;
    @Column(name = "ssfield_review3")
    private String ssfieldReview3;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "updateTime")
    private Date updateTime;
}
