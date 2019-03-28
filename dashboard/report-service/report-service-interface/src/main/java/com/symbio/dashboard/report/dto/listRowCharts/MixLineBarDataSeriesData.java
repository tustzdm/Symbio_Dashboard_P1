package com.symbio.dashboard.report.dto.listRowCharts;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类是用于对系列详细数据或者简单数据做出的一个封装，返回给MixLineBarData类中
 *
 * @author daizongheng
 */
@Data
public class MixLineBarDataSeriesData {

    /**
     *主要类型的name
     * 例如：降水量，蒸发量
     */
    private String name;

    /**
     *根据name所得到的详细数据集合
     */
    private List data;

    /**
     * 为了测试json串，定义构造方法写死基本数据，以后从数据库中获取
     */
    public MixLineBarDataSeriesData(){
        super();

        this.setName("降水量");

        data = new ArrayList();
        data.add(1.6);
        data.add(3.8);
        data.add(8.5);

    }
}
