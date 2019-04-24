package com.symbio.dashboard.report.dto.QualityOverview.listCharts.barLabelRotation;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类表示的是BarSimple类型格式，json测试格式没有定义的前提下，暂时归属此包下，待确定之后可在换
 *
 */

@Data
public class BarSimple {

    /**
     * 用于表示颜色
     */
    private String color;

    /**
     * 用于表示星期
     */
    private List xData;

    /**
     *对应的值
     */
    private List seriesData;

}
