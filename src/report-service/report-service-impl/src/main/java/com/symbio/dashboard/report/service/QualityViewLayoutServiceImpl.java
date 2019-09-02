package com.symbio.dashboard.report.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.repository.LanguageUIRep;
import com.symbio.dashboard.data.repository.ReportChartRep;
import com.symbio.dashboard.data.repository.SettingLayoutRep;
import com.symbio.dashboard.model.LanguageUi;
import com.symbio.dashboard.model.ReportChart;
import com.symbio.dashboard.model.SettingLayout;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartCommon;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartOther;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListList;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListRowChart;
import com.symbio.dashboard.report.dto.qualityviewleyout.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 本类用于实现QualityViewLayoutService接口，将layout页面布局发送给前端
 *
 */

@Service
@Data
@Component
@SuppressWarnings("unchecked")
public class QualityViewLayoutServiceImpl implements QualityViewLayoutService {

    @Autowired
    private ReportChartRep reportChartRepository;

    @Autowired
    private SettingLayoutRep settingLayoutRepository;

    @Autowired
    private LanguageUIRep languageUIRepository;


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

        QualityViewLayoutCD qualityViewLayoutCD = new QualityViewLayoutCD();

        qualityViewLayoutCD.setLocale(locale);

        //菜单权限暂定写死
        qualityViewLayoutCD.setRole(7);

        //根据语种获得settinglayout中的layout
        Map<String, List> map = getSettingLayoutLayout(locale);
        if (map == null){
            return new Result("N000005", "没有这类语言的layout");
        }

        String jsonMap = JSON.toJSONString(map, true);

        List<ReportChart> reportChartList = getReportChartEntityByPage();
        List<LanguageUi> languageUiList = getLanguageUIEntityByPage();

        Result list;

        list = setListLabel(locale, languageUiList);
        if (list.hasError()) {
            return list;
        }else {
            qualityViewLayoutCD.setListLabel((List) list.getCd());
        }


        list = setListChartCommon(locale, JSON.parseArray(JSON.parseObject(jsonMap).getString("listChartCommon"), ListChartCommon.class),reportChartList);
        if (list.hasError()){
            return list;
        }else {
            qualityViewLayoutCD.setListCharts((List) list.getCd());
        }

        list = setListChartOther(locale,JSON.parseArray(JSON.parseObject(jsonMap).getString("listChartOther"), ListChartOther.class),reportChartList);
        if (list.hasError()){
            return list;
        }else {
            qualityViewLayoutCD.setOtherReport((List) list.getCd());
        }

        list = setListRowChart(locale, JSON.parseArray(JSON.parseObject(jsonMap).getString("listRowChart"), ListRowChart.class),reportChartList);
        if (list.hasError()){
            return list;
        }else {
            qualityViewLayoutCD.setListRowChart((List) list.getCd());
        }

        list = setListList(locale, JSON.parseArray(JSON.parseObject(jsonMap).getString("listlist"), ListList.class), reportChartList);
        if (list.hasError()){
            return list;
        }else {
            qualityViewLayoutCD.setListList((List) list.getCd());
        }


        qualityViewLayoutCD.setListSupport(setListSupport(locale));

        return new Result(qualityViewLayoutCD);
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
    private Result setListChartCommon(String locale,List<ListChartCommon> listChartCommons,List<ReportChart> reportChartList){
        List<QualityViewLayoutCDChartCommon> list = new LinkedList<>();

        for (ListChartCommon listChartCommon : listChartCommons){
            Integer pos[] = listChartCommon.getPos();
            String key = listChartCommon.getKey();

            ReportChart reportChart = null;
            for (ReportChart r : reportChartList) {
                if (r.getKey().equals(key)) {
                    reportChart = r;
                }
            }

            if (reportChart == null){
                return new Result("N000006", "ListChartCommon 根据key找不到相应的reportChart对象");
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
        return new Result(list);
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
    private Result setListChartOther(String locale, List<ListChartOther> listChartOthers,List<ReportChart> reportChartList){
        List<QualityViewLayoutCDChartOther> list = new LinkedList<>();

        for (ListChartOther listChartOther : listChartOthers){
            String key = listChartOther.getKey();

            ReportChart reportChart = null;
            for (ReportChart r : reportChartList) {
                if (r.getKey().equals(key)) {
                    reportChart = r;
                }
            }

            if (reportChart == null){
                return new Result("N000007", "listChartOther 根据key找不到相应的reportChart对象");
            }

            keyList.add(key);

            QualityViewLayoutCDChartOther q = new QualityViewLayoutCDChartOther();
            q.setIdx(reportChart.getIdx());
            q.setKey(key);
            q.setName(getNameByLocale(locale,reportChart.getName()));
            q.setType(reportChart.getType());

            list.add(q);
        }

        return new Result(list);
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
    private Result setListRowChart(String locale, List<ListRowChart> listRowCharts,List<ReportChart> reportChartList){
        List<QualityViewLayoutCDRowChart> list = new LinkedList<>();

        for (ListRowChart listRowChart : listRowCharts){
            Integer pos = listRowChart.getPos();
            String key = listRowChart.getKey();

            ReportChart reportChart = null;
            for (ReportChart r : reportChartList) {
                if (r.getKey().equals(key)) {
                    reportChart = r;
                }
            }

            if (reportChart == null){
                return new Result("N000008", "listRowChart 根据key找不到相应的reportChart对象");
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

        return new Result(list);
    }

    /**
     * 根据语种和setting_layout中layout里的的 listlist 列表
     *   封装一个返回到前台的 listlist 的集合
     *
     * @param locale 语种
     * @param listLists listlist 的列表
     *
     * @return cd里面的对象是一个List<QualityViewLayoutCDList>  返回一个可以返回到前台的布局信息中的 listlist 集合
     */
    private Result setListList(String locale, List<ListList> listLists,List<ReportChart> reportChartList){
        List<QualityViewLayoutCDList> list = new LinkedList<>();

        for(ListList listList : listLists){
            Integer pos = listList.getPos();
            String key = listList.getKey();

            ReportChart reportChart = null;
            for (ReportChart r : reportChartList) {
                if (r.getKey().equals(key)) {
                    reportChart = r;
                }
            }

            if (reportChart == null){
                return new Result("N000009", "listlist 根据key找不到相应的reportChart对象");
            }

            keyList.add(key);

            QualityViewLayoutCDList q = new QualityViewLayoutCDList();
            q.setPos(pos);
            q.setIdx(reportChart.getIdx());
            q.setKey(key);
            q.setName(getNameByLocale(locale,reportChart.getName()));

            list.add(q);
        }
        return new Result(list);
    }


    /**
     * 本方法用于根据语种和language_ui中的信息，组装一个listLabel列表
     *
     * @param locale 语种
     * @param languageUis language_ui所有有用的对象
     *
     * @return 返回一个listLabel列表对象
     */
    private Result setListLabel(String locale, List<LanguageUi> languageUis) {

        List list = new LinkedList();

        for (LanguageUi l : languageUis) {
            String key = l.getKey();
            QualityViewLayoutCDLabel qualityViewLayoutCDLabel = new QualityViewLayoutCDLabel();
            qualityViewLayoutCDLabel.setKey(key);

            String label = l.getLabel();
            JSONObject jsonObject = JSONObject.parseObject(label);
            Map<String,String> map = (Map<String, String>) JSONObject.toJSON(jsonObject);

            qualityViewLayoutCDLabel.setLabel(map.get(locale));

            list.add(qualityViewLayoutCDLabel);

        }

        return new Result(list);
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


    /**
     * 在getLayout接口中，页面定死为QualityOverview，通过page返回reportchart的集合
     * @return 返回page为QualityOverview的所有实体类
     */
    private List<ReportChart> getReportChartEntityByPage() {
        List<ReportChart> list = reportChartRepository.getByPageAndValidation("qualityoverview", 1);
        return list;
    }


    /**
     * 在数据库language_ui中，通过page和validation获得LanguageUI集合
     * @return 返回page为 QualityOverviewLayout 中的所有实体类
     */
    private List<LanguageUi> getLanguageUIEntityByPage() {
        List<LanguageUi> list = languageUIRepository.getByPageAndValidation("QualityOverviewLayout", 1);
        return list;
    }

    /**
     * 实现接口，将前端页面布局存储到数据库中，然后根据存储结果将反馈信息返回给前端
     *
     * @return 将反馈对象返回给前端
     */
    @Override
    public Result getSaveFeedback(String locale,
                                  List<ListChartCommon> listChartCommon,
                                  List<ListChartOther> listChartOther,
                                  List<ListRowChart> listRowChart,
                                  List<ListList> listList) {

        Result result = setSaveQualityViewLeyout(locale, listChartCommon, listChartOther, listRowChart, listList);
        return result;
    }


    /**
     *返回一个存储反馈信息对象
     *
     * @return SaveQualityViewLeyout类型的object
     */
    private Result setSaveQualityViewLeyout(String locale,
                                            List<ListChartCommon> listChartCommon,
                                            List<ListChartOther> listChartOther,
                                            List<ListRowChart> listRowChart,
                                            List<ListList> listList){

        // 可以进行一些处理，最终确定反馈信息......
        // 判断所有的key在reportChart能不能找到
        Result list = judgeAllListKey(listChartCommon,listChartOther,listRowChart,listList);
        if (list.hasError()){
            return list;
        }
        int flag = saveUploadInDB(locale, listChartCommon, listChartOther, listRowChart, listList);
        return new Result(flag);

    }

    /**
     * 按照上送的save信息，不重复的存放到setting_layout中
     *
     * @param locale 语种
     * @param listChartCommon listChartCommon集合
     * @param listChartOther listChartOther集合
     * @param listRowChart listRowChart集合
     * @param listList listList集合
     *
     * @return 返回0，表示没增加，返回其他数字，则表示增加的行数
     */
    private int saveUploadInDB(String locale,
                               List<ListChartCommon> listChartCommon,
                               List<ListChartOther> listChartOther,
                               List<ListRowChart> listRowChart,
                               List<ListList> listList){
        int flag = 0;
        Integer id = settingLayoutRepository.getIdByPageAndTypeAndLocale("qualityoverview", 1, locale);

        String layout = saveLayoutJson(listChartCommon, listChartOther, listRowChart, listList);
        //settingLayoutRepository.saveAndFlush(createSettingLayout(id,page,1,locale,layout));
        if (id != null){
            settingLayoutRepository.saveAndFlush(createSettingLayout(id, "qualityoverview", 1, locale, layout));
        }else if (id == null){
            settingLayoutRepository.saveAndFlush(createSettingLayout("qualityoverview", 1, locale, layout));
            flag++;//表示存放了数据到数据库中
        }
        return flag;
    }


    /**
     * 将上送信息列表转换成json串，用于存放到数据库的setting_chart表中
     *
     * @param listChartCommon listChartCommon集合
     * @param listChartOther listChartOther集合
     * @param listRowChart listRowChart集合
     * @param listList listList集合
     *
     * @return 返回一个String类型的json串
     */

    private String saveLayoutJson(List<ListChartCommon> listChartCommon,
                                  List<ListChartOther> listChartOther,
                                  List<ListRowChart> listRowChart,
                                  List<ListList> listList){

        Map<String,List> map = new HashMap<>();
        map.put("listChartCommon",listChartCommon);
        map.put("listChartOther",listChartOther);
        map.put("listRowChart",listRowChart);
        map.put("listlist", listList);
        return JSONObject.toJSONString(map);
    }


    /**
     * 此方法用于封装和返回一个新建的实体类
     *
     * @param page page
     * @param type type
     * @param locale locale
     * @param layout layout
     *
     * @return 返回一个创建的实体类
     */
    private SettingLayout createSettingLayout(String page, Integer type, String locale, String layout){
        SettingLayout settingLayout = new SettingLayout();

        settingLayout.setPage(page);
        settingLayout.setType(type);
        settingLayout.setLocale(locale);
        settingLayout.setLayout(layout);

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowtime = simpleDateFormat.format(date);
        try {
            Date time = simpleDateFormat.parse(nowtime);
            settingLayout.setCreateTime(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return settingLayout;
    }


    /**
     * 此方法用于封装和返回一个新建的实体类
     *
     * @param id id
     * @param page page
     * @param type type
     * @param locale locale
     * @param layout layout
     *
     * @return 返回一个创建的实体类
     */
    private SettingLayout createSettingLayout(Integer id,String page,Integer type,String locale,String layout){
        SettingLayout settingLayout = new SettingLayout();

        settingLayout.setId(id);
        settingLayout.setPage(page);
        settingLayout.setType(type);
        settingLayout.setLocale(locale);
        settingLayout.setLayout(layout);

        return settingLayout;
    }


    /**
     * 得到上送信息所有列表中的所有对象的key中对应的page
     *
     * @param listChartCommon listChartCommon集合
     * @param listChartOther listChartOther集合
     * @param listRowChart listRowChart集合
     * @param listList listList集合
     *
     * @return cd中的对象是List<String> 根据所有上送的列表中的key返回一个相应的所有的page集合
     */
    private Result judgeAllListKey(List<ListChartCommon> listChartCommon,
                                   List<ListChartOther> listChartOther,
                                   List<ListRowChart> listRowChart,
                                   List<ListList> listList){

        //得到所有的key
        List<String> keyList = reportChartRepository.getKeyByPageAndValidation("qualityoverview", 1);
        if (keyList == null) {
            return new Result("N00005", "通过page与validation找不到相应的key");
        }


        Result list1 = judgeListChartCommonKey(listChartCommon,keyList);
        Result list2 = judgeListChartOtherKey(listChartOther,keyList);
        Result list3 = judgeListRowChartKey(listRowChart,keyList);
        Result list4 = judgeListListKey(listList,keyList);

        if (list1.hasError()) {
            return list1;
        } else if (list2.hasError()) {
            return list2;
        } else if (list3.hasError()) {
            return list3;
        } else if (list4.hasError()) {
            return list4;
        }
        return new Result(null);
    }

    /**
     * 根据上送的 listChartCommons 中的key得到page，如果找不到page则返回空
     *
     * @param listChartCommons listChartCommons列表
     *
     * @return cd中的对象是List<String> 根据上送的列表中的key返回一个相应的page集合
     */
    private Result judgeListChartCommonKey(List<ListChartCommon> listChartCommons,List<String> keyList){
        //用于判断对象中的pos是不是存放在指定的位置
        List<Integer[]> posList = new LinkedList<>();

        for (ListChartCommon listChartCommon : listChartCommons){
            Integer[] pos = listChartCommon.getPos();
            if (!posListAddCommon(posList, pos)) {
                return new Result("N10001", "坐标不合法");
            }


            String key = listChartCommon.getKey();
            if (!keyList.contains(key)){
                return new Result("N00001", "根据 listChartCommons 中的key找不到相应的page");
            }
        }

        return new Result();
    }

    /**
     * 根据上送的 listChartOthers 中的key得到page，如果找不到page则返回空
     *
     * @param listChartOthers listChartCommons列表
     *
     * @return cd中的对象是List<String> 根据上送的列表中的key返回一个相应的page集合
     */
    private Result judgeListChartOtherKey(List<ListChartOther> listChartOthers,List<String> keyList){
        for (ListChartOther listChartOther : listChartOthers){
            String key = listChartOther.getKey();
            if (!keyList.contains(key)){
                return new Result("N00002", "根据 listChartOthers 中的key找不到相应的page");
            }
        }

        return new Result();
    }

    /**
     * 根据上送的 listrowcharts 中的key得到page，如果找不到page则返回空
     *
     * @param listRowCharts listChartCommons列表
     *
     * @return cd中的对象是List<String> 根据上送的列表中的key返回一个相应的page集合
     */
    private Result judgeListRowChartKey(List<ListRowChart> listRowCharts,List<String> keyList){

        List<Integer> posList = new LinkedList<>();

        for (ListRowChart listRowChart : listRowCharts){
            Integer pos = listRowChart.getPos();
            if (isIllegalPosRowChart(posList, pos)) {
                return new Result("N10002", "ListRowChart的坐标不合法");
            }


            String key = listRowChart.getKey();

            if (!keyList.contains(key)){
                return new Result("N00003", "根据 listrowcharts 中的key找不到相应的page");
            }
        }
        return new Result();
    }

    /**
     * 根据上送的 listLists 中的key得到page，如果找不到page则返回空
     *
     * @param listLists listChartCommons列表
     *
     * @return cd中的对象是List<String> 根据上送的列表中的key返回一个相应的page集合
     */
    private Result judgeListListKey(List<ListList> listLists,List<String> keyList){
        Result result = new Result();

        List<Integer> posList = new LinkedList<>();

        for (ListList listList : listLists){
            Integer pos = listList.getPos();
            if (isIllegalPosList(posList, pos)) {
                return new Result("N10003", "ListList的坐标不合法");
            }

            String key = listList.getKey();
            if (!keyList.contains(key)){
                return new Result("N00004", "根据 listLists 中的key找不到相应的page");
            }
        }
        return new Result();
    }


    /**
     * 此方法用于判断listCommonChart中的pos坐标数组是否合法，长度不为2不合法，坐标一样不合法
     * @param pos1 第一个pos
     * @param pos2 第二个pos
     * @return 返回true表示违法，返回false表示合法
     */
    private boolean isIllegalPosCommon(Integer pos1[], Integer pos2[]) {
        if (pos1.length != 2 || pos2.length != 2) {
            return true;
        }
        for (int i = 0; i < pos1.length; i++) {
            if (pos1[i] != pos2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 此方法用于将pos坐标放入坐标集合中，要求坐标合法，不存在现有坐标才能存入
     * @param list 现有坐标集合
     * @param pos 要插入的坐标
     * @return 返回false则表示插入失败，不合法或者冲突，返回true则表示成功
     */
    private boolean posListAddCommon(List<Integer[]> list,Integer pos[]) {
        for (Integer[] a : list) {
            if (isIllegalPosCommon(a, pos)) {
                return false;
            }
        }
        list.add(pos);
        return true;
    }


    /**
     * 此方法用于判断ListRowChart列表中的pos是否合法，大于三不合法，posList存在不合法
     *
     * @param posList 已存在的pos集合
     * @param pos 新的pos
     *
     * @return 返回true表示违法，返回false表示合法
     */
    private boolean isIllegalPosRowChart(List<Integer> posList, Integer pos) {
        if (pos > 3) {
            return true;
        }
        if (posList.contains(pos)) {
            return true;
        }
        posList.add(pos);
        return false;
    }


    /**
     * 此方法用于判断ListList列表中的pos是否合法，大于三不合法，posList存在不合法
     *
     * @param posList 已存在的pos集合
     * @param pos 新的pos
     *
     * @return 返回true表示违法，返回false表示合法
     */
    private boolean isIllegalPosList(List<Integer> posList, Integer pos) {
        if (pos > 3) {
            return true;
        }
        if (posList.contains(pos)) {
            return true;
        }
        posList.add(pos);
        return false;
    }
}
