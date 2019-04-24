package com.symbio.dashboard.report.dto.QualityOverview.listList;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 本类用于封装ListProductStatisticsData对象中的columns信息内容，返回给ListProductStatisticsData对象
 */
@Data
public class ListProductStatisticsDataColumns {

    /**
     * 表示字段
     */
    private String key;

    /**
     * 表示标题
     */
    private String lable;

    /**
     * 显示类型
     */
    private String type;

    /**
     * 显示对齐方式
     *  1.左对齐
     *  2.居中（默认）
     *  3.右对齐
     */
    private Integer align = 2;

    /**
     * 表示字段在field中的定义
     */
    private String field;

    /**
     * 格式化方式
     */
    private String formatter;


}
