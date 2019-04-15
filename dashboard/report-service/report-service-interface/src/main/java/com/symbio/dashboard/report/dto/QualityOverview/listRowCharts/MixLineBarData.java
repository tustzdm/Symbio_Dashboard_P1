package com.symbio.dashboard.report.dto.QualityOverview.listRowCharts;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 本类用于封装MixLineBar对象的图标信息内容，返回给MixLineBar对象
 *
 * @author daizongheng
 */
@Data
public class MixLineBarData {

    /**
     * 用于表示要显示的内容信息，比如：降水量，蒸发量，平均温度等
     */
    private List legend;

    /**
     * 用于表示月份
     */
    private List xData;

    /**
     * yData用于表示具体状况,泛型定义为具体yData对象
     */
    private List<MixLineBarDataYData> yData;

    /**
     *表示显示的方式
     */
    private Integer groupby;

    /**
     * 系列数据
     */
    private List<MixLineBarDataSeriesData> seriesData;



}
