package com.symbio.dashboard.report.dto.QualityOverview.listCombox;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 本类主要为PieScrollLegend中的Condition集合中的对象
 *
 * @author daizongheng
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

    /**
     * 此构造函数暂时用于初始化对象数据
     *
     * 暂定将数据对象写死
     */
    public PieScrollLegendCondition(){
        name = "product";
        type = "list";
        value = "all";

        data = new ArrayList<>();
        data.add(new PieScrollLegendConditionData());
        data.add(new PieScrollLegendConditionData());
        data.add(new PieScrollLegendConditionData());
    }
}
