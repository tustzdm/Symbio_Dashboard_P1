package com.symbio.dashboard.report.dto.QualityOverview.listCombox;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 本类主要为PieScrollLegend中的Condition集合中的对象
 *
 */

@Data
public class PieScrollLegendCondition {

    /**
     * 名字
     */
    private String name;

    /**
     * 图表类型
     */
    private String type;

    /**
     * 展示方式
     */
    private String value;

    /**
     *data集合中每一项都是一个key-value的对象
     */
    private List<PieScrollLegendConditionData> data;


}
