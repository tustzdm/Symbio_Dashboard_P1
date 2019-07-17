package com.symbio.dashboard.report.dto.qualityoverview.listcombox;

import lombok.Data;

import java.util.List;

/**
 * 本类主要为PieScrollLegend中的Condition集合中的对象
 *
 */

@Data
public class ListComboxCondition {

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
    private List<ListComboxConditionData> data;


}
