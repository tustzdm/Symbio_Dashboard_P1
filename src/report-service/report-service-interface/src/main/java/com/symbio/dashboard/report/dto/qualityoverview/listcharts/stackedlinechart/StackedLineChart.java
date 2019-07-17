package com.symbio.dashboard.report.dto.qualityoverview.listcharts.stackedlinechart;

import lombok.Data;

/**
 * 本类是用于返回一个StackedLineChart对象给前端的类
 *
 */

@Data
public class StackedLineChart {

    /**
     * pos是个数组坐标类型
     */
    private Integer pos[];

    /**
     * key为图表类型
     */
    private String key;

    /**
     * data 是返回给前端对象的所有内容
     */
    private StackedLineChartData data;


}
