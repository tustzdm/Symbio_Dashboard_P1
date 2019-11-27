package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName - Comment Info
 * @Description - comment_info 实体类
 * @Date - 2019/11/19
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "comment_info")
public class CommentInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "comment_type")
    private Integer commentType;

    @Column(name = "fk_id")
    private Integer fkId;

    @Column(name = "content")
    private String content;

    @Column(name = "validation", nullable = false)
    private Integer validation;

    @Column(name = "update_user_id")
    private Integer updateUserId;
    @Column(name = "update_user_name")
    private String updateUserName;

    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time", nullable = false)
    private Date updateTime;
}
