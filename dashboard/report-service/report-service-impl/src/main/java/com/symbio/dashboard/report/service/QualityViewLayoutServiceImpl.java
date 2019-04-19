package com.symbio.dashboard.report.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartCommon;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartOther;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListList;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListRowChart;
import com.symbio.dashboard.report.dto.qualityViewLeyout.*;
import com.symbio.dashboard.report.modle.ReportChart;
import com.symbio.dashboard.report.repository.ReportChartRepository;
import com.symbio.dashboard.report.repository.SettingLayoutRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 本类用于实现QualityViewLayoutService接口，将layout页面布局发送给前端
 *
 */

@Service
@Data
public class QualityViewLayoutServiceImpl implements QualityViewLayoutService {

    @Autowired
    private ReportChartRepository reportChartRepository;
    @Autowired
    private SettingLayoutRepository settingLayoutRepository;


    /**
     * 用于保存不需要返回的supportList中的key值
     */
    private List<String> keyList = new LinkedList<>();

    @Override
    public QualityViewLayout getQualityViewLayout(String locale) {
        return setQualityViewLayout(locale);
    }


    /**
     * 用于创建一个QualityViewLayout对象并返回
     *
     * @param locale 语种 language
     *
     * @return 返回一个创建好的QualityViewLayout对象
     */
    private QualityViewLayout setQualityViewLayout(String locale){
        QualityViewLayout qualityViewLayout = new QualityViewLayout();

        QualityViewLayoutCD qualityViewLayoutCD = setQualityViewLayoutCD(locale);
        if (qualityViewLayoutCD == null){
            qualityViewLayout.setEc("N00002");
            qualityViewLayout.setEm("null point exception");
            return qualityViewLayout;
        }

        //暂时没有错误的返回信息
        qualityViewLayout.setEc("0");
        qualityViewLayout.setEm("success");

        qualityViewLayout.setCd(setQualityViewLayoutCD(locale));
        return qualityViewLayout;
    }


    /**
     * 用于创建QualityViewLayout类中的cd对象并返回
     *
     * @param locale 语种
     *
     * @return 返回一个cd对象
     */
    private QualityViewLayoutCD setQualityViewLayoutCD(String locale){
        QualityViewLayoutCD qualityViewLayoutCD = new QualityViewLayoutCD();

        qualityViewLayoutCD.setLocale(locale);

        //菜单权限暂定写死
        qualityViewLayoutCD.setRole(7);



        //根据语种获得settinglayout中的layout
        Map<String, List> map = getSettingLayoutLayout(locale);
        if (map == null){
            return null;
        }

        String jsonMap = JSON.toJSONString(map, true);


        //后面的list暂定写死
//        qualityViewLayoutCD.setListSupport(setListSupport(locale));

        List list;
//        list = setListChartCommon(locale,map.get("listChartCommon"));
        list = setListChartCommon(locale, JSON.parseArray(JSON.parseObject(jsonMap).getString("listChartCommon"), ListChartCommon.class));
        System.out.println(list);
        if (list == null){
            return null;
        }else {
            qualityViewLayoutCD.setListChartCommon(list);
        }

        list = setListChartOther(locale,JSON.parseArray(JSON.parseObject(jsonMap).getString("listChartOther"), ListChartOther.class));
        if (list == null){
            return null;
        }else {
            qualityViewLayoutCD.setListChartOther(list);
        }

        list = setListRowChart(locale, JSON.parseArray(JSON.parseObject(jsonMap).getString("listRowChart"), ListRowChart.class));
        if (list == null){
            return null;
        }else {
            qualityViewLayoutCD.setListRowChartUsed(list);
        }

        list = setListList(locale,JSON.parseArray(JSON.parseObject(jsonMap).getString("listList"), ListList.class));
        if (list == null){
            return null;
        }else {
            qualityViewLayoutCD.setListListUsed(list);
        }

        qualityViewLayoutCD.setListSupport(setListSupport(locale));

        return qualityViewLayoutCD;
    }



    /**
     * 根据语种获得setting_layout数据库中的layout的json串,并转为map返回
     *
     * @param locale 语种
     *
     * @return 返回layout的json串转换后的map
     */
    private Map<String,List> getSettingLayoutLayout(String locale){
        String layout = settingLayoutRepository.getLayoutByLocale(locale);
        JSONObject jsonObject = JSONObject.parseObject(layout);
        Map<String,List> map = (Map<String, List>) JSONObject.toJSON(jsonObject);
        return map;
    }


    /**
     * 根据语种和setting_layout中layout里的的 ListChartCommon 列表
     * 封装一个返回到前台的 listChartCommon 的集合
     *
     * @param locale 语种
     * @param listChartCommons listChartCommon的列表
     *
     * @return 返回一个可以返回到前台的布局信息中的ListChartCommon集合
     */
    private List<QualityViewLayoutCDChartCommon> setListChartCommon(String locale,List<ListChartCommon> listChartCommons){
        List<QualityViewLayoutCDChartCommon> list = new LinkedList<>();

        System.out.println(listChartCommons.get(1));


//        JSONObject userJson = JSONObject.parseObject(listChartCommons.get(1));

//        ListChartCommon listChartCommon1 = listChartCommons.get(1);
//        System.out.println(listChartCommons.get(1).getPos());


        for (ListChartCommon listChartCommon : listChartCommons){
            Integer pos[] = listChartCommon.getPos();
            String key = listChartCommon.getKey();

            ReportChart reportChart = reportChartRepository.getByKey(key);
            if (reportChart == null){
                return null;
            }

            keyList.add(key);

            QualityViewLayoutCDChartCommon q = new QualityViewLayoutCDChartCommon();
            q.setPos(pos);
            q.setIdx(reportChart.getIdx());
            q.setKey(key);
            q.setName(getNameByLocale(locale,reportChart.getName()));
            q.setType(reportChart.getType());

            list.add(q);
        }
        return list;
    }


    /**
     * 根据语种和setting_layout中layout里的的 listChartOther 列表
     *   封装一个返回到前台的 listChartOther 的集合
     *
     * @param locale 语种
     * @param listChartOthers listChartOther的列表
     *
     * @return 返回一个可以返回到前台的布局信息中的 listChartOther 集合
     */
    private List<QualityViewLayoutCDChartOther> setListChartOther(String locale, List<ListChartOther> listChartOthers){
        List<QualityViewLayoutCDChartOther> list = new LinkedList<>();

        for (ListChartOther listChartOther : listChartOthers){
            String key = listChartOther.getKey();

            ReportChart reportChart = reportChartRepository.getByKey(key);
            if (reportChart == null){
                return null;
            }

            keyList.add(key);

            QualityViewLayoutCDChartOther q = new QualityViewLayoutCDChartOther();
            q.setIdx(reportChart.getIdx());
            q.setKey(key);
            q.setName(getNameByLocale(locale,reportChart.getName()));
            q.setType(reportChart.getType());

            list.add(q);
        }


        return list;
    }

    /**
     * 根据语种和setting_layout中layout里的的 listRowChart 列表
     *   封装一个返回到前台的 listRowChart 的集合
     *
     * @param locale 语种
     * @param listRowCharts listRowChart 的列表
     *
     * @return 返回一个可以返回到前台的布局信息中的 listRowChart 集合
     */
    private List<QualityViewLayoutCDRowChart> setListRowChart(String locale, List<ListRowChart> listRowCharts){
        List<QualityViewLayoutCDRowChart> list = new LinkedList<>();

        for (ListRowChart listRowChart : listRowCharts){
            Integer pos = listRowChart.getPos();
            String key = listRowChart.getKey();

            ReportChart reportChart = reportChartRepository.getByKey(key);
            if (reportChart == null){
                return null;
            }

            keyList.add(key);

            QualityViewLayoutCDRowChart q = new QualityViewLayoutCDRowChart();
            q.setPos(pos);
            q.setIdx(reportChart.getIdx());
            q.setKey(key);
            q.setName(getNameByLocale(locale,reportChart.getName()));
            q.setType(reportChart.getType());

            list.add(q);
        }

        return list;
    }

    /**
     * 根据语种和setting_layout中layout里的的 listList 列表
     *   封装一个返回到前台的 listList 的集合
     *
     * @param locale 语种
     * @param listLists listList 的列表
     *
     * @return 返回一个可以返回到前台的布局信息中的 listList 集合
     */
    private List<QualityViewLayoutCDList> setListList(String locale, List<ListList> listLists){
        List<QualityViewLayoutCDList> list = new LinkedList<>();

        for(ListList listList : listLists){
            Integer pos = listList.getPos();
            String key = listList.getKey();

            ReportChart reportChart = reportChartRepository.getByKey(key);
            if (reportChart == null){
                return null;
            }

            keyList.add(key);

            QualityViewLayoutCDList q = new QualityViewLayoutCDList();
            q.setPos(pos);
            q.setIdx(reportChart.getIdx());
            q.setKey(key);
            q.setName(getNameByLocale(locale,reportChart.getName()));

            list.add(q);
        }

        return list;
    }












    /**
     * 方法用于建立listSupport集合
     *
     * @param locale 语种
     *
     * @return 返回一个ListSupport列表，最终用来返回给前台
     */
    private List<QualityViewLayoutInformation> setListSupport(String locale){
        //获得实体类list
        List<ReportChart> list = reportChartRepository.findAll();
        //最终要生成的列表
        List<QualityViewLayoutInformation> lastList = new ArrayList<QualityViewLayoutInformation>();

        for(ReportChart reportChart : list){
            String key = reportChart.getKey();
            if (keyList.contains(key)){
                continue;
            }
            QualityViewLayoutInformation qualityViewLayoutInformation = new QualityViewLayoutInformation();
            //这几个是从数据库中能直接读取的
            qualityViewLayoutInformation.setIdx(reportChart.getIdx());
            qualityViewLayoutInformation.setKey(key);
            qualityViewLayoutInformation.setType(reportChart.getType());
            //做一些key的判断，判断是否需要添加pos
            //TODO

            //根据locale获得相应的name值
            qualityViewLayoutInformation.setName(getNameByLocale(locale,reportChart.getName()));

            lastList.add(qualityViewLayoutInformation);

        }


        return lastList;
    }


    /**
     * 从数据库获得的json串，根据语种返回相应的name
     * @param locale 语种
     * @param name 从数据库中获得的json串
     * @return 返回一个根据语种的name值
     */
    private String getNameByLocale(String locale,String name){
        JSONObject jsonObject = JSONObject.parseObject(name);
        Map<String, String> map = (Map<String, String>) JSONObject.toJSON(jsonObject);
        return map.get(locale);
    }



}
