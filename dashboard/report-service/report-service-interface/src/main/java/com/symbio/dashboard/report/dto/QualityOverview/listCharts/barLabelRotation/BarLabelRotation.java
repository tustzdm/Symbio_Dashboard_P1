package com.symbio.dashboard.report.dto.QualityOverview.listCharts.barLabelRotation;

import lombok.Data;

/**
 * 此类是用于返回BarLabelRotation对象给前端的类
 *
 * @author daizongheng
 */

@Data
public class BarLabelRotation {

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
    private BarLabelRotationData data;

    /**
     * 此构造函数暂时用于初始化对象数据
     *
     * 暂定将数据对象写死
     */
    public BarLabelRotation(){
        this.setPos("[1，0]");
        this.setKey("BarLabRotation");
        this.setData(new BarLabelRotationData());
    }
}
