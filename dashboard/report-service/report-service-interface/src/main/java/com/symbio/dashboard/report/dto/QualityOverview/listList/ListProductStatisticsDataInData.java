package com.symbio.dashboard.report.dto.QualityOverview.listList;


import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;

import java.util.List;

/**
 * 本类用于封装ListProductStatisticsData中的data信息内容，返回给ListProductStatisticsData对象
 *
 * 根据ListProductStatisticsData中的fields列表返回相应的数据信息
 *
 * @author daizongheng
 */
@Data
@JSONType(orders = {"name","id","auto","qa","engineer","comment","progress"})
public class ListProductStatisticsDataInData {

    /**
     * id号
     */
    private Integer id = 5;

    /**
     * 类型名称
     */
    private String name = "uality Overview report";

    /**
     * 查看测试接口，暂时定义Interger
     */
    private Integer auto = 1;

    /**
     * 查看测试接口，暂时定义Interger
     */
    private Integer qa = 100;

    /**
     * 查看测试接口，暂时定义Double
     */
    private Double progress = 3.11;

    /**
     * 其实是一个engineer类，暂时没有具体实例，先用string写死
     */
    private String engineer = "User";

    /**
     * 查看测试接口，暂时定义Double
     */
    private Double comment = 9.88;



}
