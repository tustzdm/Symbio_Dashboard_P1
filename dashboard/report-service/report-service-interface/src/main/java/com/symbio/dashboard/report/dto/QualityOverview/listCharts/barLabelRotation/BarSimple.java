package com.symbio.dashboard.report.dto.QualityOverview.listCharts.barLabelRotation;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类表示的是BarSimple类型格式，json测试格式没有定义的前提下，暂时归属此包下，待确定之后可在换
 *
 * @author daizongheng
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

    /**
     * 此构造函数暂时用于初始化对象数据
     *
     * 暂定将数据对象写死
     */
    public BarSimple(){
        super();

        color = "#003666";

        xData = new ArrayList();
        xData.add("Mon");
        xData.add("Tue");

        seriesData = new ArrayList();
        seriesData.add(120);
        seriesData.add(200);


    }
}
