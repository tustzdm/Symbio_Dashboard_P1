package com.symbio.dashboard.report.dto.listCombox;

import lombok.Data;

/**
 * 此类的作用是返回一个key-value形式的对象给PieScrollLegendCondition去
 *
 * @author daizongheng
 */
@Data
public class PieScrollLegendConditionData {

    /**
     * 默认为all
     */
    private String key = "all";

    /**
     * key所对应的值
     */
    private String value = "All";
}
