package com.symbio.dashboard.report.dto.listRowCharts;

import lombok.Data;

/**
 * 本类用于返回给前端页面的MixLineBar对象信息
 *
 * @author daizongheng
 */
@Data
public class MixLineBar {

    /**
     * pos暂定初始化值为0
     */
    private Integer pos;

    /**
     * key为键的名字，表示图表类型
     */
    private String key;

    /**
     * 用于表示图表信息内容
     */
    private MixLineBarData data;

    /**
     * 为了测试json串，定义构造方法写死成员变量，以后成员变量的具体内容从数据库或前台中获取
     */
    public MixLineBar(){
        this.setPos(0);
        this.setKey("MixLineBar");
        this.setData(new MixLineBarData());
    }

}
