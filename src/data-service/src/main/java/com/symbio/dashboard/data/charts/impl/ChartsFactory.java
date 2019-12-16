package com.symbio.dashboard.data.charts.impl;

import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.charts.ChartGenerate;
import com.symbio.dashboard.data.charts.ChartProvider;
import com.symbio.dashboard.enums.ChartsType;
import com.symbio.dashboard.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.util.FileUtil;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class ChartsFactory {

    protected ChartsType m_chart_type;
    protected Map<String, Object> m_chart_data;

    protected Map<String, Object> getMapData() {
        if (m_chart_data == null) {
            m_chart_data = new HashMap<>();
        }
        return this.m_chart_data;
    }

    protected void setMapData(Map<String, Object> data) {
        this.m_chart_data = data;
    }

    abstract Map<String, Object> setChartTitle(String title);

    abstract Map<String, Object> setChartData(Map<String, Object> data);

    private ChartsFactory() {
    }

    public ChartsFactory(ChartsType chartType) {
        this.m_chart_type = chartType;
    }

    public ChartsType getChartType() {
        return this.m_chart_type;
    }

    protected Map initialChartData() {
        Map<String, Object> map = new HashMap<>();
        try {
            ChartsType chartType = getChartType();
            String path = ChartsFactory.class.getResource(chartType.getResourceFileName()).getPath();
            File file = new File(path);
            String content = FileUtil.readAsString(file);
            JSONObject jsonObject = new JSONObject(content);
            map = JSONUtil.toMap(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg("ChartFactory.initialChartData() ChartType = " + getChartType().getValue(), e));
        }
        return map;
    }

    public static void main(String args[]) {

        // Abstract Factory
        ChartProvider chartProvider = new ChartProviderFactory();
        ChartGenerate chartGenerate = chartProvider.produce(ChartsType.PIE_REFER);

        Map<String, Object> mapData = chartGenerate.getChartMapData(getTestMap());
        System.out.println(mapData);
    }

    public static Map<String, Object> getTestMap() {
        Map<String, Object> data = new HashMap<>();

        data.put("title", "Access");

        String[] arrLegendData = new String[]{"", ""};
        data.put("legend", arrLegendData);

        data.put("serialName", "access source");

        List<Map<String, Object>> listData = new ArrayList<>();
        Map<String, Object> serialMap = new HashMap<>();
        serialMap.put("name", "直接访问");
        serialMap.put("value", 355);
        listData.add(serialMap);

        serialMap = new HashMap<>();
        serialMap.put("name", "邮件营销");
        serialMap.put("value", 310);
        listData.add(serialMap);

        data.put("serialData", listData);

        return data;
    }
}
