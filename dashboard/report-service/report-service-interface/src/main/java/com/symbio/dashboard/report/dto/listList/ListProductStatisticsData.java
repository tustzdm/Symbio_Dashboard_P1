package com.symbio.dashboard.report.dto.listList;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * 为了测试json串，定义构造方法写死成员变量，以后成员变量的具体内容从数据库或前台中获取
     */
    public ListProductStatisticsData(){
        this.setPageIndex(0);
        this.setPageSize(5);
        this.setTotalRecord(10);


        columns = new ArrayList<>();
        columns.add(new ListProductStatisticsDataColumns());
        columns.add(new ListProductStatisticsDataColumns());
        columns.add(new ListProductStatisticsDataColumns());

        fields = new ArrayList();
        fields.add("id");
        fields.add("name");
        fields.add("auto");
        fields.add("qa");
        fields.add("progress");
        fields.add("engineer");
        fields.add("comment");


        data = new ArrayList<>();
        data.add(new ListProductStatisticsDataInData());
        data.add(new ListProductStatisticsDataInData());
        data.add(new ListProductStatisticsDataInData());
        data.add(new ListProductStatisticsDataInData());

    }
}
