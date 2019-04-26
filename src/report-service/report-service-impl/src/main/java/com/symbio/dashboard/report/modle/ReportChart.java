package com.symbio.dashboard.report.modle;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
/**
 * 本类是数据库report_chart的实体类
 */
@Setter
@Getter
@ToString
@Entity //实体类
@NoArgsConstructor //无参构造方法
@Table(name = "report_chart" , schema = "test")
public class ReportChart {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private Integer id;

    @Column(name = "page",nullable = false)
    private String page;

    @Column(name = "key",nullable = false)
    private String key;

    @Column(name = "name",nullable = false)
    private String name;

    /**
     * 1: common chart,2: other chart,3: list,4: row chart
     */
    @Column(name = "type",nullable = false)
    private Integer type;


    @Column(name = "idx",nullable = false)
    private Integer idx;

    @Column(name = "search")
    private String search = null;

    /**
     * 0: disabel,1: available
     * 默认为1
     */
    @Column(name = "validation",nullable = false)
    private Integer validation = 1;

}
