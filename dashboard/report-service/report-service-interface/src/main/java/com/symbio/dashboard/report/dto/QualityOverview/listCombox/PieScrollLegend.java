package com.symbio.dashboard.report.dto.QualityOverview.listCombox;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 本类用于返回给前端页面的PieScrollLegend具体信息
 *  暂时以json测试串为主
 *
 * @author daizongheng
 */

@Data
public class PieScrollLegend {

    /**
     * 关键字
     */
    private String key;

    /**
     * 类型的名字
     */
    private String name;

    /**
     * 内容为对象列表
     */
    private List<PieScrollLegendCondition> condition;

    /**
     * 此构造方法用于初始化对象内容
     *
     * 为了测试json串暂定将返回的数据写死
     */
    public PieScrollLegend(){
        key = "PieScrollLegend";
        name = "Pie with Scrollable Legend";

        condition = new ArrayList<>();
        condition.add(new PieScrollLegendCondition());
        condition.add(new PieScrollLegendCondition());
        condition.add(new PieScrollLegendCondition());
    }
}
