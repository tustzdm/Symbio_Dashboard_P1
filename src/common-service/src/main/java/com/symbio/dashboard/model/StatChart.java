package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName - Stat Chart
 * @Description - stat_chart 实体类
 * @Date - 2019/11/25
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "stat_chart")
public class StatChart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "page_name")
    private String pageName;

    @Column(name = "chart_id")
    private Integer chartId;

    @Column(name = "content")
    private String content;

    @Column(name = "validation", nullable = false)
    private Integer validation;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;
}
