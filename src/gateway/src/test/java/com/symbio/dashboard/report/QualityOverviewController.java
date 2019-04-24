package com.symbio.dashboard.report;

import com.symbio.dashboard.report.dto.QualityOverview.listCharts.stackedLineChart.StackedLineChart;
import org.junit.Test;

/**
 * 本类用于测试QualityOverviewController中返回的子json串进行一个测试
 */


public class QualityOverviewController {

    private StackedLineChart stackedLineChart = new StackedLineChart();

    @Test
    public void getStackedLineChart(){


        System.out.println(stackedLineChart);
    }
}
