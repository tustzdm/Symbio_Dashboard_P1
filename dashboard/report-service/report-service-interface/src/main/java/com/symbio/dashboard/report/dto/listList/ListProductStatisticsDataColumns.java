package com.symbio.dashboard.report.dto.listList;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 本类用于封装ListProductStatisticsData对象中的columns信息内容，返回给ListProductStatisticsData对象
 *
 * @author daizongheng
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

    /**
     * 为了测试json串，定义构造方法写死成员变量，以后成员变量的具体内容从数据库或前台中获取
     */
    public ListProductStatisticsDataColumns(){
        this.setKey("ProductIDLink");
        this.setLable("Test Lable");
        this.setType("text");
        this.setField("auto");
        this.setFormatter(null);
    }
}
