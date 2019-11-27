package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName - ParseResultSummary
 * @Description - 此类是数据库[parse_result_summary]的实体类
 * @Date - 2019/8/20
 * @Version 1.0
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "parse_result_summary")
public class ParseResultSummary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)

    private Integer id;
    @Column(name = "batchNo")
    private String batchNo;
    @Column(name = "batchId")
    private Integer batchId;
    @Column(name = "filePath")
    private String filePath;
    @Column(name = "fileName")
    private String fileName;
    @Column(name = "fileSize")
    private Integer fileSize;
    @Column(name = "fileWorkPath")
    private String fileWorkPath;
    @Column(name = "fileBackupPath")
    private String fileBackupPath;
    @Column(name = "parseStatus")
    private Integer parseStatus;
    @Column(name = "parseCount")
    private Integer parseCount;
    @Column(name = "parseErrorCode")
    private String parseErrorCode;
    @Column(name = "parseErrorMsg")
    private String parseErrorMsg;
    @Column(name = "description")
    private String description;
    @Column(name = "reportSummary")
    private String reportSummary;
    @Column(name = "receiveTime")
    private Date receiveTime;
}