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


}
