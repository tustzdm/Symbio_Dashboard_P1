package com.symbio.dashboard.report.dto.QualityOverview.listCharts.stackedLineChart;


import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 本类为返回给前端的StackedLineChart对象的具体数据内容
 *
 */

@Data
public class StackedLineChartData {
    /**
     * legend用于表示图例，暂定用list接收后台数据库的legend信息
     *
     * 泛型未定义，暂不知道后台存储形式
     */
    private List legend;

    /**
     * name用于表示List内容给前台，暂定用list接收后台数据库name信息
     *
     * 泛型未定义，暂不知道后台存储形式
     */
    private List name;

    /**
     * groupby用于表示图表显示的方向（横纵轴的变换）
     *
     * 暂定的值只能为0或者1，需要处理
     */
    private Integer groupby;


    /**
     * key为表格属性，value为该属性下的具体内容
     *
     * 暂定初始化一个空的hashMap
     */
    private Map data = new HashMap();





}
