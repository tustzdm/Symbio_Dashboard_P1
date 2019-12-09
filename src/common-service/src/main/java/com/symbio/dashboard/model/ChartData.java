package com.symbio.dashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName - chart_data
 * @Description - 数据库chart_data表实体类
 * @Date - 2019/7/15 18:39
 * @Version 1.0
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "chart_data")
public class ChartData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "chart_key")
    private String chartKey;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "data")
    private String data;

    @Column(name = "validation", nullable = false)
    private Integer validation = 1;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;
}
