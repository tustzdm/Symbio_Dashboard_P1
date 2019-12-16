package com.symbio.dashboard.data.charts.impl;

import com.symbio.dashboard.data.charts.ChartGenerate;
import com.symbio.dashboard.enums.ChartsType;
import com.symbio.dashboard.enums.EnumDef;

import java.util.HashMap;
import java.util.Map;

public abstract class PieChart extends ChartsFactory implements ChartGenerate {

    public PieChart(ChartsType chartType) {
        super(chartType);
    }

    protected String getChartKey() {
        return getChartType().getValue();
    }

    @Override
    public Map<String, Object> getChartMapData(Map<String, Object> data) {

        Map<String, Object> mapData = new HashMap<>();

        if (data.containsKey(EnumDef.CHART_PARAM_KEY.TITLE.getValue())) {
            setChartTitle(data.get(EnumDef.CHART_PARAM_KEY.TITLE.getValue()).toString());
        }

        setChartData(data);

        mapData.put("data", getMapData());
        mapData.put("key", getChartKey());

        return mapData;
    }

}
