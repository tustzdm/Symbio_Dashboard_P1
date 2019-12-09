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

    public Map<String, Object> getChartMapData(String locale, EnumDef.CHARTS chart, Object data) {
        Map<String, Object> retMap = new HashMap<>();

        switch (chart) {
            default:
                break;
            case BUGS_PIE: // "PieReferer"
                Map<String, List<Object>> bugPieData = (Map<String, List<Object>>) data;
                retMap = getPieReferMapData(locale, bugPieData);
                break;
            case BUGS_BAR: // SimpleBar
                List<Map<String, Object>> bugBarData = (List<Map<String, Object>>) data;
                retMap = getSimpleBarMapData(locale, chart, bugBarData);
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

    private Map<String, Object> getSimpleBarMapData(String locale, EnumDef.CHARTS chart, List<Map<String, Object>> data) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            String category = "Priority";
            String title = "Priority per Status";
            if (Locales.ZH_CN.toString().equals(locale)) {
                title = "优先级 / 状态";
                category = "优先级";
            }

            retMap.put("title", buildTitle(chart, title));
            retMap.put("color", buildColor(chart));
            retMap.put("legend", buildLegend(chart, null));
            retMap.put("tooltip", buildToolTip(chart));

            Map<String, Object> mapData = new HashMap<>();
            List<List<Object>> listSource = buildDataSetSource(chart, data, category);
            mapData.put("source", listSource);
            retMap.put("dataset", mapData);

            retMap.put("grid", buildGrid(chart));

            mapData = new HashMap<>();
            retMap.put("yAxis", mapData);
            mapData = new HashMap<>();
            mapData.put("type", "category");
            retMap.put("xAxis", mapData);

            retMap.put("series", buildSeries(chart, listSource));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retMap;
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

        String[] arrColor = {"#7A85A1", "#9EADC5", "#C9D4E3", "#E8E8E8", "#F3D1CD", "#F9E8E0"};
        retMap.put("color", arrColor);
        retMap.put("title", buildTitle(EnumDef.CHARTS.BUGS_PIE, null));
        retMap.put("tooltip", buildToolTip(EnumDef.CHARTS.BUGS_PIE));
        retMap.put("legend", buildLegend(EnumDef.CHARTS.BUGS_PIE, arrLegendData));
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

            retMap.put("title", buildTitle(EnumDef.CHARTS.BUGS_PIE, title));
            retMap.put("tooltip", buildToolTip(EnumDef.CHARTS.BUGS_PIE));
            // color: ['#7A85A1', '#9EADC5', '#C9D4E3', '#E8E8E8', '#F3D1CD', '#F9E8E0'],
            //String[] arrColor = {"#7A85A1", "#9EADC5", "#C9D4E3", "#E8E8E8", "#F3D1CD", "#F9E8E0"};
            retMap.put("color", buildColor(EnumDef.CHARTS.BUGS_PIE));
            retMap.put("legend", buildLegend(EnumDef.CHARTS.BUGS_PIE, arrLegendData));
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

    private Map<String, Object> buildTitle(EnumDef.CHARTS chart, String title) {
        Map<String, Object> retMap = new HashMap<>();

        switch (chart) {
            default:
                break;
            case BUGS_PIE: // "PieReferer"
                retMap.put("text", CommonUtil.isEmpty(title) ? "#title#" : title);
                retMap.put("subtext", "");
                retMap.put("x", "center");
                break;
            case BUGS_BAR: // SimpleBar
                retMap.put("text", CommonUtil.isEmpty(title) ? "#title#" : title);
                retMap.put("x", "20px");
                retMap.put("x", "5px");
                break;
        }

        return retMap;
    }

    private String[] buildColor(EnumDef.CHARTS chart) {
        String[] arrColor = {};

        switch (chart) {
            default:
                break;
            case BUGS_PIE: // "PieReferer"
                arrColor = new String[]{"#7A85A1", "#9EADC5", "#C9D4E3", "#E8E8E8", "#F3D1CD", "#F9E8E0"};
                break;
            case BUGS_BAR: // SimpleBar
                arrColor = new String[]{"#7A85A1", "#C9D4E3", "#E8E8E8", "#9EADC5", "#F3D1CD", "#F9E8E0"};
                break;
        }

        return arrColor;
    }

    private Map<String, Object> buildToolTip(EnumDef.CHARTS chart) {
        Map<String, Object> retMap = new HashMap<>();

        switch (chart) {
            default:
                break;
            case BUGS_PIE: // "PieReferer"
                retMap.put("trigger", "item");
                retMap.put("formatter", "{a} <br/>{b} : {c} ({d}%)");
                break;
            case BUGS_BAR: // SimpleBar
                break;
        }

        return retMap;
    }

    private Map<String, Object> buildLegend(EnumDef.CHARTS chart, String[] legendData) {
        Map<String, Object> retMap = new HashMap<>();

        switch (chart) {
            default:
                break;
            case BUGS_PIE: // "PieReferer"
                retMap.put("orient", "vertical");
                retMap.put("left", "left");
                retMap.put("data", legendData);
                //retMap.put("x", "20px");
                //retMap.put("y", "10px");
                break;
            case BUGS_BAR: // SimpleBar
                retMap.put("y", "30px");
                retMap.put("right", "20px");
                break;
        }
        return retMap;
    }

    private Map<String, Object> buildGrid(EnumDef.CHARTS chart) {
        Map<String, Object> retMap = new HashMap<>();

        switch (chart) {
            default:
                break;
            case BUGS_BAR: // SimpleBar
                retMap.put("left", "3%");
                retMap.put("right", "3%");
                retMap.put("bottom", "3%");
                retMap.put("containLabel", true);
                break;
        }
        return retMap;

    }

    private Object buildSeries(EnumDef.CHARTS chart, Object data) {
        Object retObject = null;

        try {
            switch (chart) {
                default:
                    break;
                case BUGS_BAR: // SimpleBar
                    List<Map<String, String>> listSimpleBar = new ArrayList<>();

                    List<List<Object>> listData = (List<List<Object>>) data;
                    if (!CommonUtil.isEmpty(listData)) {
                        List<Object> listCategory = listData.get(0);
                        if (!CommonUtil.isEmpty(listCategory) && listCategory.size() > 1) {
                            Integer nCount = listCategory.size() - 1;
                            for (int i = 0; i < nCount; i++) {
                                Map<String, String> mapData = new HashMap<>();
                                mapData.put("type", "bar");
                                listSimpleBar.add(mapData);
                            }
                        }
                    }

                    retObject = listSimpleBar;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retObject;
    }

    private Integer getValueByCategoryAndStatus(List<Map<String, Object>> data, String priority, String status) {
        Integer nCount = 0;
        try {
            for (Map<String, Object> mapData : data) {
                String strPriority = mapData.get("priority").toString();
                String strStatus = mapData.get("status").toString();
                if (priority.equals(strPriority) && status.equals(strStatus)) {
                    nCount = Integer.parseInt(mapData.get("count").toString());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nCount;
    }

    private List<Object> getListStatusData(List<Map<String, Object>> data, List<Object> listCategory, String status) {
        List<Object> retData = new ArrayList<>();

        try {
            for (Object category : listCategory) {
                String priority = category.toString();
                Integer nCount = getValueByCategoryAndStatus(data, priority, status);
                retData.add(nCount);
            }

            if (retData.size() > 0) {
                retData.add(0, status);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return retData;
    }

    private List<List<Object>> buildDataSetSource(EnumDef.CHARTS chart, List<Map<String, Object>> data, String category) {
        List<List<Object>> retData = new ArrayList<>();

        switch (chart) {
            default:
                break;
            case BUGS_BAR: // SimpleBar
                List<Object> listHeader = new ArrayList<>();
                List<String> listStatus = new ArrayList<>();
                for (Map<String, Object> mapItem : data) {
                    listHeader.add(mapItem.get("priority").toString());
                    listStatus.add(mapItem.get("status").toString());
                }
                if (listHeader.size() > 0) {
//                    listHeader.add(0, category);
//                    retData.add(listHeader);

                    List<Object> listStatusData = new ArrayList<>();
                    for (String status : listStatus) {
                        listStatusData = getListStatusData(data, listHeader, status);

                        if (listStatusData.size() > 0) {
                            retData.add(listStatusData);
                        }
                    }

                    if (retData.size() > 0) {
                        listHeader.add(0, category);
                        retData.add(0, listHeader);
                    }
                }
                break;
        }
        return retData;

    }

    public static void main(String[] args) throws Exception {
        ChartFactory cf = new ChartFactory();
        String strResult = cf.getChartData("PieReferer");
        System.out.println(strResult);

        //Map<String, Object> mapData = getChartMapData("en_US", , null);

        List<Map<String, Object>> simpleData = new ArrayList<>();
        Map<String, Object> mapBar = new HashMap<>();
        mapBar.put("count", 1);
        mapBar.put("priority", "P0");
        mapBar.put("status", "Closed");
        simpleData.add(mapBar);
        mapBar = new HashMap<>();
        mapBar.put("count", 1);
        mapBar.put("priority", "P1");
        mapBar.put("status", "Fixed");
        simpleData.add(mapBar);

        mapBar = new HashMap<>();
        mapBar.put("count", 1);
        mapBar.put("priority", "P3");
        mapBar.put("status", "New");
        simpleData.add(mapBar);

        System.out.println(JSONUtil.mapToString(cf.getChartMapData("en_US", EnumDef.CHARTS.BUGS_BAR, simpleData)));
    }

}
