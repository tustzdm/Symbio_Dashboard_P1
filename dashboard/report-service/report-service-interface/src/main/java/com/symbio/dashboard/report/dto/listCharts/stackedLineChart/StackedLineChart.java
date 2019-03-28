package com.symbio.dashboard.report.dto.listCharts.stackedLineChart;

import lombok.Data;

/**
 * 本类是用于返回一个StackedLineChart对象给前端的类
 *
 * @author daizongheng
 */

@Data
public class StackedLineChart {

    /**
     * pos的json串是个数组坐标类型，这里暂时写死为字符串类型
     */
    private String pos;

    /**
     * key为图表类型
     */
    private String key;

    /**
     * data 是返回给前端对象的所有内容
     */
    private ChartsData data;

    /**
     * 此构造函数暂时用于初始化对象数据
     *
     * 暂定将数据对象写死
     */
    public StackedLineChart(){
        super();

        this.setPos("[0,1]");
        this.setKey("StackedLine");
        this.setData(new ChartsData());
    }
}
