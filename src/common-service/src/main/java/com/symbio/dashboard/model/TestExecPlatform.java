package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName - TestExecPlatform
 * @Description - test_exec-platform实体类
 * @Date - 2019/8/5 15:11
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "test_exec_platform")
public class TestExecPlatform implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "productId", nullable = false)
    private Integer productId;

    @Column(name = "testSetType", nullable = false)
    private Integer testSetType;

    @Column(name = "jenkinsId", nullable = false)
    private Integer jenkinsId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "idx", nullable = false)
    private Integer idx;

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
