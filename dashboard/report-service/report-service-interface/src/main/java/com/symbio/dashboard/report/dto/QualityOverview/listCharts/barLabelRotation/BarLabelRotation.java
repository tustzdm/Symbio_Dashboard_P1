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

}
