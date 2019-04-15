package com.symbio.dashboard.report.dto.QualityOverview.listList;

import lombok.Data;

/**
 * 本类用于返回给前端页面的ListProductStatistics对象信息
 *
 * @author daizongheng
 */
@Data
public class ListProductStatistics {

    /**
     * pos暂定初始化值为0
     */
    private Integer pos = 0;

    /**
     * key为键的名字，表示图表类型
     */
    private String key;

    /**
     * data类型对象
     */
    private ListProductStatisticsData data;


}
