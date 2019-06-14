package com.symbio.dashboard.report.service;

import com.alibaba.fastjson.JSONObject;
import com.symbio.dashboard.Result;
import com.symbio.dashboard.ec.report.SaveErrorCode;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartCommon;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartOther;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListList;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListRowChart;
import com.symbio.dashboard.model.SettingLayout;
import com.symbio.dashboard.report.repository.ReportChartRepository;
import com.symbio.dashboard.report.repository.SettingLayoutRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 本类实现SaveQualityViewLeyoutService接口，主要做用是将页面信息存放到数据库setting_layout中
 *  然后将存储情况返回给前端
 *
 */

@Service
@Data
public class SaveQualityViewLayoutServiceImpl implements SaveQualityViewLeyoutService {


    @Autowired
    private SettingLayoutRepository settingLayoutRepository;

    @Autowired
    private ReportChartRepository reportChartRepository;


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

        //创建反馈信息对象
        Result result = new Result();

        //可以进行一些处理，最终确定反馈信息......
        //TODO 判断所有的key在reportChart能不能找到
        Result list = judgeAllListKey(listChartCommon,listChartOther,listRowChart,listList);
        if (list.hasError()){
            return list;
        }
        int flag = saveUploadInDB(locale, listChartCommon, listChartOther, listRowChart, listList);
        result.setCdAndRightEcAndEm(flag);
        return result;

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
        Integer id = settingLayoutRepository.getIdByPageAndTypeAndLocale("QualityOverview",1,locale);

        String layout = saveLayoutJson(listChartCommon, listChartOther, listRowChart, listList);
        //settingLayoutRepository.saveAndFlush(createSettingLayout(id,page,1,locale,layout));
        if (id != null){
            settingLayoutRepository.saveAndFlush(createSettingLayout(id,"QualityOverview",1,locale,layout));
        }else if (id == null){
            settingLayoutRepository.saveAndFlush(createSettingLayout("QualityOverview",1,locale,layout));
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
        map.put("listList",listList);
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
    private SettingLayout createSettingLayout(String page,Integer type,String locale,String layout){
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
            settingLayout.setCreate_time(time);
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
        Result result = new Result();

        //得到所有的key
        List<String> keyList = reportChartRepository.getKeyByPageAndValidation("QualityOverview", 1);
        if (keyList == null) {
            result.setEc(SaveErrorCode.N00005.toString());
            result.setEm("通过page与validation找不到相应的key");
            return result;
        }


        Result list1 = judgeListChartCommonKey(listChartCommon,keyList);
        Result list2 = judgeListChartOtherKey(listChartOther,keyList);
        Result list3 = judgeListRowChartKey(listRowChart,keyList);
        Result list4 = judgeListListKey(listList,keyList);

        if (list1.hasError()){
            return list1;
        }else if (list2.hasError()){
            return list2;
        }else if (list3.hasError()){
            return list3;
        }else if (list4.hasError()){
            return list4;
        }else {
            result.setCdAndRightEcAndEm(null);
        }
        return result;
    }

    /**
     * 根据上送的 listChartCommons 中的key得到page，如果找不到page则返回空
     *
     * @param listChartCommons listChartCommons列表
     *
     * @return cd中的对象是List<String> 根据上送的列表中的key返回一个相应的page集合
     */
    private Result judgeListChartCommonKey(List<ListChartCommon> listChartCommons,List<String> keyList){
        Result result = new Result();
        //用于判断对象中的pos是不是存放在指定的位置
        List<Integer[]> posList = new LinkedList<>();

        for (ListChartCommon listChartCommon : listChartCommons){
            Integer[] pos = listChartCommon.getPos();
            if (!posListAddCommon(posList, pos)) {
                result.setEc(SaveErrorCode.N10001.toString());
                result.setEm("坐标不合法");
                return result;
            }


            String key = listChartCommon.getKey();
            if (!keyList.contains(key)){
                result.setEc(SaveErrorCode.N00001.toString());
                result.setEm("根据 listChartCommons 中的key找不到相应的page");
                return result;
            }
        }

        result.setCdAndRightEcAndEm(null);
        return result;
    }

    /**
     * 根据上送的 listChartOthers 中的key得到page，如果找不到page则返回空
     *
     * @param listChartOthers listChartCommons列表
     *
     * @return cd中的对象是List<String> 根据上送的列表中的key返回一个相应的page集合
     */
    private Result judgeListChartOtherKey(List<ListChartOther> listChartOthers,List<String> keyList){
        Result result = new Result();
        for (ListChartOther listChartOther : listChartOthers){
            String key = listChartOther.getKey();
            if (!keyList.contains(key)){
                result.setEc(SaveErrorCode.N00002.toString());
                result.setEm("根据 listChartOthers 中的key找不到相应的page");
                return result;
            }
        }

        result.setCdAndRightEcAndEm(null);

        return result;
    }

    /**
     * 根据上送的 listRowCharts 中的key得到page，如果找不到page则返回空
     *
     * @param listRowCharts listChartCommons列表
     *
     * @return cd中的对象是List<String> 根据上送的列表中的key返回一个相应的page集合
     */
    private Result judgeListRowChartKey(List<ListRowChart> listRowCharts,List<String> keyList){
        Result result = new Result();

        List<Integer> posList = new LinkedList<>();

        for (ListRowChart listRowChart : listRowCharts){
            Integer pos = listRowChart.getPos();
            //TODO
            if (isIllegalPosRowChart(posList, pos)) {
                result.setEc(SaveErrorCode.N10002.toString());
                result.setEm("ListRowChart的坐标不合法");
            }


            String key = listRowChart.getKey();

            if (!keyList.contains(key)){
                result.setEc(SaveErrorCode.N00003.toString());
                result.setEm("根据 listRowCharts 中的key找不到相应的page");
                return result;
            }
        }
        result.setCdAndRightEcAndEm(null);
        return result;
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
                result.setEc(SaveErrorCode.N10003.toString());
                result.setEm("ListList的坐标不合法");
            }

            String key = listList.getKey();
            if (!keyList.contains(key)){
                result.setEc(SaveErrorCode.N00004.toString());
                result.setEm("根据 listLists 中的key找不到相应的page");
                return result;
            }
        }
        result.setCdAndRightEcAndEm(null);
        return result;
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
