package com.symbio.dashboard.report.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartCommon;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartOther;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListList;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListRowChart;
import com.symbio.dashboard.report.dto.qualityViewLeyout.*;
import com.symbio.dashboard.report.modle.ReportChart;
import com.symbio.dashboard.report.repository.ReportChartRepository;
import com.symbio.dashboard.report.repository.SettingLayoutRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@Component
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
    public Result getQualityViewLayout(String locale) {
        Result result = setQualityViewLayout(locale);
        return result;
    }


    /**
     * 用于创建一个QualityViewLayout对象并返回
     *
     * @param locale 语种 language
     *
     * @return 返回一个创建好的QualityViewLayout对象
     */
    private Result setQualityViewLayout(String locale){
        Result result = setQualityViewLayoutCD(locale);
        return result;

    }


    /**
     * 用于创建QualityViewLayout类中的cd对象并返回
     *
     * @param locale 语种
     *
     * @return cd里存放的是一个QualityViewLayoutCD对象用于返回给前台的一个cd对象里的所有集合
     */
    private Result setQualityViewLayoutCD(String locale){
        Result result = new Result();

        QualityViewLayoutCD qualityViewLayoutCD = new QualityViewLayoutCD();

        qualityViewLayoutCD.setLocale(locale);

        //菜单权限暂定写死
        qualityViewLayoutCD.setRole(7);



        //根据语种获得settinglayout中的layout
        Map<String, List> map = getSettingLayoutLayout(locale);
        if (map == null){
            result.setEc("N000009");
            result.setEm("没有这类语言的layout");
            return result;
        }

        String jsonMap = JSON.toJSONString(map, true);

        Result list;
//        list = setListChartCommon(locale,map.get("listChartCommon"));
        list = setListChartCommon(locale, JSON.parseArray(JSON.parseObject(jsonMap).getString("listChartCommon"), ListChartCommon.class));
        System.out.println(list);
        if (list.hasError()){
            return list;
        }else {
            qualityViewLayoutCD.setListCharts((List) list.getCd());
        }

        list = setListChartOther(locale,JSON.parseArray(JSON.parseObject(jsonMap).getString("listChartOther"), ListChartOther.class));
        if (list.hasError()){
            return list;
        }else {
            qualityViewLayoutCD.setOtherReport((List) list.getCd());
        }

        list = setListRowChart(locale, JSON.parseArray(JSON.parseObject(jsonMap).getString("listRowChart"), ListRowChart.class));
        if (list.hasError()){
            return list;
        }else {
            qualityViewLayoutCD.setListRowChart((List) list.getCd());
        }

        list = setListList(locale,JSON.parseArray(JSON.parseObject(jsonMap).getString("listList"), ListList.class));
        if (list.hasError()){
            return list;
        }else {
            qualityViewLayoutCD.setListList((List) list.getCd());
        }

        qualityViewLayoutCD.setListSupport(setListSupport(locale));

        result.setCdAndRightEcAndEm(qualityViewLayoutCD);

        return result;
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
     * @return cd里面的对象是一个List<QualityViewLayoutCDChartCommon> 返回一个可以返回到前台的布局信息中的ListChartCommon集合
     */
    private Result setListChartCommon(String locale,List<ListChartCommon> listChartCommons){
        Result result = new Result();
        List<QualityViewLayoutCDChartCommon> list = new LinkedList<>();


        for (ListChartCommon listChartCommon : listChartCommons){
            Integer pos[] = listChartCommon.getPos();
            String key = listChartCommon.getKey();

            ReportChart reportChart = reportChartRepository.getByKey(key);
            if (reportChart == null){
                result.setEc("N00008");
                result.setEm("ListChartCommon 根据key找不到相应的reportChart对象");
                return result;
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
        result.setCdAndRightEcAndEm(list);
        return result;
    }


    /**
     * 根据语种和setting_layout中layout里的的 listChartOther 列表
     *   封装一个返回到前台的 listChartOther 的集合
     *
     * @param locale 语种
     * @param listChartOthers listChartOther的列表
     *
     * @return cd里面的对象是一个List<QualityViewLayoutCDChartOther> 返回一个可以返回到前台的布局信息中的 listChartOther 集合
     */
    private Result setListChartOther(String locale, List<ListChartOther> listChartOthers){
        Result result = new Result();
        List<QualityViewLayoutCDChartOther> list = new LinkedList<>();

        for (ListChartOther listChartOther : listChartOthers){
            String key = listChartOther.getKey();

            ReportChart reportChart = reportChartRepository.getByKey(key);
            if (reportChart == null){
                result.setEc("N00007");
                result.setEm("listChartOther 根据key找不到相应的reportChart对象");
                return result;
            }

            keyList.add(key);

            QualityViewLayoutCDChartOther q = new QualityViewLayoutCDChartOther();
            q.setIdx(reportChart.getIdx());
            q.setKey(key);
            q.setName(getNameByLocale(locale,reportChart.getName()));
            q.setType(reportChart.getType());

            list.add(q);
        }

        result.setCdAndRightEcAndEm(list);

        return result;
    }

    /**
     * 根据语种和setting_layout中layout里的的 listRowChart 列表
     *   封装一个返回到前台的 listRowChart 的集合
     *
     * @param locale 语种
     * @param listRowCharts listRowChart 的列表
     *
     * @return cd里面的对象是一个List<QualityViewLayoutCDRowChart> 返回一个可以返回到前台的布局信息中的 listRowChart 集合
     */
    private Result setListRowChart(String locale, List<ListRowChart> listRowCharts){
        Result result = new Result();
        List<QualityViewLayoutCDRowChart> list = new LinkedList<>();

        for (ListRowChart listRowChart : listRowCharts){
            Integer pos = listRowChart.getPos();
            String key = listRowChart.getKey();

            ReportChart reportChart = reportChartRepository.getByKey(key);
            if (reportChart == null){
                result.setEc("N00006");
                result.setEm("listRowChart 根据key找不到相应的reportChart对象");
                return result;
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

        result.setCdAndRightEcAndEm(list);

        return result;
    }

    /**
     * 根据语种和setting_layout中layout里的的 listList 列表
     *   封装一个返回到前台的 listList 的集合
     *
     * @param locale 语种
     * @param listLists listList 的列表
     *
     * @return cd里面的对象是一个List<QualityViewLayoutCDList>  返回一个可以返回到前台的布局信息中的 listList 集合
     */
    private Result setListList(String locale, List<ListList> listLists){
        Result result = new Result();
        List<QualityViewLayoutCDList> list = new LinkedList<>();

        for(ListList listList : listLists){
            Integer pos = listList.getPos();
            String key = listList.getKey();

            ReportChart reportChart = reportChartRepository.getByKey(key);
            if (reportChart == null){
                result.setEc("N00005");
                result.setEm("listList 根据key找不到相应的reportChart对象");
                return result;
            }

            keyList.add(key);

            QualityViewLayoutCDList q = new QualityViewLayoutCDList();
            q.setPos(pos);
            q.setIdx(reportChart.getIdx());
            q.setKey(key);
            q.setName(getNameByLocale(locale,reportChart.getName()));

            list.add(q);
        }
        result.setCdAndRightEcAndEm(list);
        return result;
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
