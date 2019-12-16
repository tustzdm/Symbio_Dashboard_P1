package com.symbio.dashboard.data.charts.impl;

import com.symbio.dashboard.data.charts.ChartGenerate;
import com.symbio.dashboard.data.charts.ChartProvider;
import com.symbio.dashboard.enums.ChartsType;

public class ChartProviderFactory implements ChartProvider {

    @Override
    public ChartGenerate produce(ChartsType type) {
        ChartGenerate retChart = null;
        switch (type) {
            default:
                break;
            case PIE_REFER:
                retChart = new PieRefer();
                break;
            case BAR_CATEGORY:
                retChart = new BarCategoryStack();
                break;
        }
        return retChart;
    }
}
