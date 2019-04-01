package com.symbio.dashboard.report.dto.QualityOverview;

import com.symbio.dashboard.report.dto.listCharts.barLabelRotation.BarLabelRotation;
import com.symbio.dashboard.report.dto.listCharts.stackedLineChart.StackedLineChart;
import com.symbio.dashboard.report.dto.listCombox.PieScrollLegend;
import com.symbio.dashboard.report.dto.listList.ListProductStatistics;
import com.symbio.dashboard.report.dto.listRowCharts.MixLineBar;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private List listCombox;

    /**
     * listCharts集合
     */
    private List listCharts;

    /**
     * listList集合
     */
    private List listList;

    /**
     * listRowCharts集合
     */
    private List listRowCharts;

    /**
     * 为了测试json串，定义构造方法写死成员变量，以后成员变量的具体内容从数据库或前台中获取
     */
    public QualityOverviewCd(){
        token = "123";
        locale = "es_EN";

        listCombox = new ArrayList();
        listCombox.add(new PieScrollLegend());

        listCharts = new ArrayList();
        listCharts.add(new StackedLineChart());
        listCharts.add(new BarLabelRotation());

        listList = new ArrayList();
        listList.add(new ListProductStatistics());

        listRowCharts = new ArrayList();
        listRowCharts.add(new MixLineBar());
    }
}
