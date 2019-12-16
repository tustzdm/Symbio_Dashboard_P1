package com.symbio.dashboard.data.charts.impl;

import com.symbio.dashboard.enums.ChartsType;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PieRefer extends PieChart {

    public PieRefer() {
        super(ChartsType.PIE_REFER);

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

        if (data.containsKey(EnumDef.CHART_PARAM_KEY.SERIAL_NAME.getValue())) {
            String strSerialName = (String) data.get(EnumDef.CHART_PARAM_KEY.SERIAL_NAME.getValue());
            if (!CommonUtil.isEmpty(strSerialName)) {
                mapData = CommonUtil.setMapKeyValue(mapData, "series.name", strSerialName);
            }
        }

        if (data.containsKey(EnumDef.CHART_PARAM_KEY.SERIAL_DATA.getValue())) {
            List<Map<String, Object>> listData = (List<Map<String, Object>>) data.get(EnumDef.CHART_PARAM_KEY.SERIAL_DATA.getValue());
            if (!CommonUtil.isEmpty(listData)) {
                mapData = CommonUtil.setMapKeyValue(mapData, "series.data", listData);
            }
        } else {
            mapData = CommonUtil.setMapKeyValue(mapData, "series.data", new ArrayList<>());
        }

        return mapData;
    }

}
