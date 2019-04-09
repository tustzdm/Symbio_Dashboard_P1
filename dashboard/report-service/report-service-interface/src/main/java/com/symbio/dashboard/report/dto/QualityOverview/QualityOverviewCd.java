package com.symbio.dashboard.report.dto.QualityOverview;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 本类用于一个QualityOverview对象中的cd变量的一个封装
 *
 * @author daizongheng
 */
@Data
public class QualityOverviewCd {

    /**
     * token值
     */
    private String token;

    /**
     *语言
     */
    private String locale;



    /**
     * listCombox集合
     */
    private List listCombox = new ArrayList();

    /**
     * listCharts集合
     */
    private List listCharts = new ArrayList();

    /**
     * listList集合
     */
    private List listList = new ArrayList();

    /**
     * listRowCharts集合
     */
    private List listRowCharts = new ArrayList();

    /**
     * 为了测试json串，定义构造方法写死成员变量，以后成员变量的具体内容从数据库或前台中获取
     */
    public QualityOverviewCd(){
        token = "123";
        locale = "en_US";

  /*      listCombox.add(new PieScrollLegend());

        listCharts.add(new StackedLineChart());
        listCharts.add(new BarLabelRotation());

        listList.add(new ListProductStatistics());

        listRowCharts.add(new MixLineBar());*/
    }
}
