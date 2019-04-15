package com.symbio.dashboard.report.dto.QualityOverview.listList;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 本类用于封装ListProductStatistics对象的图标信息内容，返回给ListProductStatistics对象
 *
 * @author daizongheng
 */
@Data
public class ListProductStatisticsData {

    /**
     * 表示当前页数，从0开始
     */
    private Integer pageIndex;

    /**
     * 表示一页包含的记录数
     */
    private Integer pageSize;

    /**
     * 记录总数
     */
    private Integer totalRecord;

    /**
     * 相应的对象数据
     */
    private List<ListProductStatisticsDataColumns> columns;

    /**
     * fields字段
     */
    private List fields;

    /**
     * 根据fields输出字段
     */
    private List<ListProductStatisticsDataInData> data;


}
