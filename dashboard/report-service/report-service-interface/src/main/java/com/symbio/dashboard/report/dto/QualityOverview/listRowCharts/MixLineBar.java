package com.symbio.dashboard.report.dto.QualityOverview.listRowCharts;

import lombok.Data;

/**
 * 本类用于返回给前端页面的MixLineBar对象信息
 *
 */
@Data
public class MixLineBar {

    /**
     * pos暂定初始化值为0
     */
    private Integer pos;

    /**
     * key为键的名字，表示图表类型
     */
    private String key;

    /**
     * 用于表示图表信息内容
     */
    private MixLineBarData data;


}
