package com.symbio.dashboard.data.charts.impl;

import com.symbio.dashboard.enums.ChartsType;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BarCategoryStack extends BarChart {

    public BarCategoryStack() {
        super(ChartsType.BAR_CATEGORY);

        setMapData(initialChartData());
    }

    @Override
    protected Map<String, Object> setChartTitle(String title) {
        Map<String, Object> mapData = getMapData();

        if (!CommonUtil.isEmpty(title)) {
            mapData = CommonUtil.setMapKeyValue(mapData, "title.text", title);
        }
        return mapData;
    }

    @Override
    protected Map<String, Object> setChartColor(String[] color) {
        Map<String, Object> mapData = getMapData();
        if (!CommonUtil.isEmpty(color)) {
            mapData = CommonUtil.setMapKeyValue(mapData, "color", color);
        }
        return mapData;
    }

    @Override
    protected Map<String, Object> setChartData(Map<String, Object> data) {
        Map<String, Object> mapData = getMapData();

        if (data == null || data.isEmpty()) {
            return mapData;
        }

        if (data.containsKey(EnumDef.CHART_PARAM_KEY.LEGEND.getValue())) {
            String[] arrLegendData = (String[]) data.get(EnumDef.CHART_PARAM_KEY.LEGEND.getValue());
            if (!CommonUtil.isEmpty(arrLegendData)) {
                mapData = CommonUtil.setMapKeyValue(mapData, "legend.data", arrLegendData);
            }
        }

        if (data.containsKey(EnumDef.CHART_PARAM_KEY.Y_AXIS.getValue())) {
            String[] arrYAxisData = (String[]) data.get(EnumDef.CHART_PARAM_KEY.Y_AXIS.getValue());
            if (!CommonUtil.isEmpty(arrYAxisData)) {
                mapData = CommonUtil.setMapKeyValue(mapData, "yAxis.data", arrYAxisData);
            } else {
                mapData = CommonUtil.setMapKeyValue(mapData, "yAxis.data", new String[]{});
            }
        }

        if (data.containsKey(EnumDef.CHART_PARAM_KEY.SERIES.getValue())) {
            List<Map<String, Object>> listData = (List<Map<String, Object>>) data.get(EnumDef.CHART_PARAM_KEY.SERIES.getValue());
            if (!CommonUtil.isEmpty(listData)) {
                mapData = CommonUtil.setMapKeyValue(mapData, "series", listData);
            }
        } else {
            mapData = CommonUtil.setMapKeyValue(mapData, "series", new ArrayList<>());
        }

        return mapData;
    }

}
