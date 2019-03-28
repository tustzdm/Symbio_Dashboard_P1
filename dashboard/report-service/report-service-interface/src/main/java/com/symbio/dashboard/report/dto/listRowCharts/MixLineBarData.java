package com.symbio.dashboard.report.dto.listRowCharts;


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

    /**
     * 为了测试json串，定义构造方法写死成员变量，以后成员变量的具体内容从数据库或前台中获取
     */
    public MixLineBarData(){
        super();

        legend = new ArrayList();
        legend.add("降水量");
        legend.add("蒸发量");
        legend.add("平均温度");

        xData = new ArrayList();
        xData.add("1月");
        xData.add("2月");
        xData.add("3月");

        yData = new ArrayList<>();
        yData.add(new MixLineBarDataYData());
        yData.add(new MixLineBarDataYData());
        yData.add(new MixLineBarDataYData());

    }

}
