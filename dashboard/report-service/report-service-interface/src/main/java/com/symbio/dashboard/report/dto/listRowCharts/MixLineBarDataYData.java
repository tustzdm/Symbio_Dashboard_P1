package com.symbio.dashboard.report.dto.listRowCharts;

import lombok.Data;

/**
 * 此对象用于定义MixLineBarData对象中的具体yData的对象内容
 */
@Data
public class MixLineBarDataYData {

    /**
     *用于从数据库中获取的yData信息name
     */
    private String name;

    /**
     * 用于定义根据name获取的具体内容信息
     */
    private String formatter;

    /**
     * 该内容下的最大值
     */
    private Integer max;

    /**
     * 该内容下的最小值
     */
    private Integer min;

    /**
     *间隔
     */
    private Integer interval;

    /**
     * 为了测试json串，定义构造方法写死成员变量，以后成员变量的具体内容从数据库中获取
     */
    public MixLineBarDataYData(){
        this.setName("水量");
        this.setFormatter("300"+"ml");
    }
}
