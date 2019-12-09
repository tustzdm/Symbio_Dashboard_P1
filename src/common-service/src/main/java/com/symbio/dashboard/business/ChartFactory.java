package com.symbio.dashboard.business;

import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.JSONUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartFactory {

    public Map<String, Object> getChartMapData(String locale, EnumDef.CHARTS chart, Map<String, List<Object>> data) {
        Map<String, Object> retMap = new HashMap<>();

        switch (chart) {
            default:
                break;
            case BUGS_PIE: // "PieReferer"
                retMap = getPieReferMapData(locale, data);
                break;
            case BUGS_BAR:
                break;
        }

        return retMap;
    }

    public Map<String, Object> getChartMapData(String key) {
        Map<String, Object> retMap = new HashMap<>();

        switch (key) {
            case "PieReferer":
                retMap = getPieReferMapData();
                break;
        }

        return retMap;
    }

    public String getChartData(String key) {
        String retString = ";";

        switch (key) {
            case "PieReferer":
                retString = getPieReferData();
                break;
        }

        return retString;
    }

    private static List<Map<String, Object>> getListNameValueMap() {
        List<Map<String, Object>> listSerialData = new ArrayList<>();

//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("name", "直接访问");
//        map1.put("value", 335);
//        listSerialData.add(map1);
//
//        map1 = new HashMap<>();
//        map1.put("name", "邮件营销");
//        map1.put("value", 310);
//        listSerialData.add(map1);

        return listSerialData;
    }

    private Map<String, Object> getPieReferMapData() {
        Map<String, Object> retMap = new HashMap<>();

//        String[] arrLegendData = "直接访问,邮件营销,联盟广告,视频广告,搜索引擎".split(",");
//        String serialName = "访问来源";
        String[] arrLegendData = {};//"".split(",");
        String serialName = "#serialName#";
        List<Map<String, Object>> listSerialData = getListNameValueMap();

        retMap.put("title", buildTitle(null));
        retMap.put("tooltip", buildToolTip());
        retMap.put("legend", buildLengend(arrLegendData));
        retMap.put("series", getSerials(serialName, listSerialData));

        return retMap;
    }

    private List<String> getListString(List<Object> data) {
        List<String> listData = new ArrayList<>();

        try {
            for (Object obj : data) {
                listData.add(obj.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private List<Map<String, Object>> getSerialData(Map<String, List<Object>> data) {
        List<Map<String, Object>> listSerialData = new ArrayList<>();

        try {
            List<Object> listObjPriority = data.get("priority");
            if (!CommonUtil.isEmpty(listObjPriority) && listObjPriority.size() > 0) {
                List<Object> listObjCount = data.get("count");
                if (!CommonUtil.isEmpty(listObjCount) && listObjCount.size() == listObjPriority.size()) {
                    String strPriority = "";
                    Integer nCount = 0;
                    Map<String, Object> mapData = new HashMap<>();
                    for (int i = 0; i < listObjPriority.size(); i++) {
                        strPriority = listObjPriority.get(i).toString();
                        nCount = Integer.parseInt(listObjCount.get(i).toString());
                        mapData = new HashMap<>();

                        mapData.put("name", strPriority);
                        mapData.put("value", nCount);
                        listSerialData.add(mapData);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listSerialData;
    }

    private Map<String, Object> getPieReferMapData(String locale, Map<String, List<Object>> data) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            String title = "Priority";
            if (Locales.ZH_CN.toString().equals(locale)) {
                title = "优先级";
            }

            List<String> listPriority = getListString(data.get("priority"));
            String[] arrLegendData = {};
            if (!CommonUtil.isEmpty(listPriority)) {
                arrLegendData = listPriority.toArray(new String[listPriority.size()]);
            }
            String serialName = title;
            List<Map<String, Object>> listSerialData = getSerialData(data);

            retMap.put("title", buildTitle(title));
            retMap.put("tooltip", buildToolTip());
            retMap.put("legend", buildLengend(arrLegendData));
            retMap.put("series", getSerials(serialName, listSerialData));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }

    private String getPieReferData() {
        Map<String, Object> retMap = getPieReferMapData();
        return JSONUtil.mapToString(retMap);
    }

    private List<Map<String, Object>> getSerials(String serialName, List<Map<String, Object>> listSerialData) {
        List<Map<String, Object>> retListData = new ArrayList<>();
        Map<String, Object> mapData = new HashMap<>();

        mapData.put("name", serialName);
        mapData.put("type", "pie");
        mapData.put("radius", "55%");
        mapData.put("center", "50%,60%".split(","));
        mapData.put("data", listSerialData);

        Map<String, Object> itemStyle = new HashMap<>();
        Map<String, Object> emphasis = new HashMap<>();

        emphasis.put("shadowBlur", 10);
        emphasis.put("shadowOffsetX", 0);
        emphasis.put("shadowColor", "rgba(0, 0, 0, 0.5)");

        itemStyle.put("emphasis", emphasis);
        mapData.put("itemStyle", itemStyle);

        retListData.add(mapData);
        return retListData;
    }

    private Map<String, Object> buildTitle(String title) {
        Map<String, Object> retMap = new HashMap<>();

        retMap.put("text", CommonUtil.isEmpty(title) ? "#title#" : title);
        retMap.put("subtext", "");
        retMap.put("x", "center");
        return retMap;
    }

    private Map<String, Object> buildToolTip() {
        Map<String, Object> retMap = new HashMap<>();

        retMap.put("trigger", "item");
        retMap.put("formatter", "{a} <br/>{b} : {c} ({d}%)");
        return retMap;
    }

    private Map<String, Object> buildLengend(String[] legendData) {
        Map<String, Object> retMap = new HashMap<>();

        retMap.put("orient", "vertical");
        retMap.put("left", "left");
        retMap.put("data", legendData);
//        retMap.put("x", "20px");
//        retMap.put("y", "10px");
        return retMap;
    }

    public static void main(String[] args) throws Exception {
        ChartFactory cf = new ChartFactory();
        String strResult = cf.getChartData("PieReferer");

        System.out.println(strResult);
    }

}
