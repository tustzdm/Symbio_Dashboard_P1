package com.symbio.dashboard.report.service;

import com.alibaba.fastjson.JSONObject;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartCommon;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartOther;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListList;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListRowChart;
import com.symbio.dashboard.report.dto.saveQualityViewLeyout.SaveQualityViewLeyout;
import com.symbio.dashboard.report.modle.ReportChart;
import com.symbio.dashboard.report.modle.SettingLayout;
import com.symbio.dashboard.report.repository.ReportChartRepository;
import com.symbio.dashboard.report.repository.SettingLayoutRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * 本类实现SaveQualityViewLeyoutService接口，主要做用是将页面信息存放到数据库setting_layout中
 *  然后将存储情况返回给前端
 *
 */

@Service
@Data
public class SaveQualityViewLeyoutServiceImpl implements SaveQualityViewLeyoutService {


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
    public SaveQualityViewLeyout getSaveFeedback(String locale,
                                                 List<ListChartCommon> listChartCommon,
                                                 List<ListChartOther> listChartOther,
                                                 List<ListRowChart> listRowChart,
                                                 List<ListList> listList) {


        return setSaveQualityViewLeyout(locale,listChartCommon,listChartOther,listRowChart,listList);
    }


    /**
     *返回一个存储反馈信息对象
     *
     * @return SaveQualityViewLeyout类型的object
     */
    private SaveQualityViewLeyout setSaveQualityViewLeyout(String locale,
                                                           List<ListChartCommon> listChartCommon,
                                                           List<ListChartOther> listChartOther,
                                                           List<ListRowChart> listRowChart,
                                                           List<ListList> listList){

        //创建反馈信息对象
        SaveQualityViewLeyout saveQualityViewLeyout = new SaveQualityViewLeyout();

        //可以进行一些处理，最终确定反馈信息......
        List<String> list = getAllPage(listChartCommon,listChartOther,listRowChart,listList);
        if (list == null){
            saveQualityViewLeyout.setEc("N000001");
            saveQualityViewLeyout.setEm("save fail");
            return saveQualityViewLeyout;
        }else {
            saveUploadInDB(list, locale, listChartCommon, listChartOther, listRowChart, listList);
            saveQualityViewLeyout.setEc("0");
            saveQualityViewLeyout.setEm("success");
            return saveQualityViewLeyout;
        }
    }


    /**
     * 按照上送的save信息，不重复的存放到setting_layout中
     *
     * @param list page的集合
     * @param locale 语种
     * @param listChartCommon listChartCommon集合
     * @param listChartOther listChartOther集合
     * @param listRowChart listRowChart集合
     * @param listList listList集合
     *
     * @return 返回0，表示没存，返回1，表示存了
     */
    private int saveUploadInDB(List<String> list,String locale,
                               List<ListChartCommon> listChartCommon,
                               List<ListChartOther> listChartOther,
                               List<ListRowChart> listRowChart,
                               List<ListList> listList){
        int flag = 0;
        for (String page : list){
            Integer id = settingLayoutRepository.getIdByPageAndTypeAndLocale(page,1,locale);
            String layout = saveLayoutJson(listChartCommon, listChartOther, listRowChart, listList);
            if (id != null){
                System.out.println(id+"----------");
                settingLayoutRepository.save(createSettingLayout(id,page,1,locale,layout));
                continue;
            }else if (id == null){
                settingLayoutRepository.save(createSettingLayout(page,1,locale,layout));
                flag = 1;//表示存放了数据到数据库中
                continue;
            }
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

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        settingLayout.setId(id);
        settingLayout.setPage(page);
        settingLayout.setType(type);
        settingLayout.setLocale(locale);
        settingLayout.setLayout(layout);

        settingLayout.setCreateTime(timestamp);

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
     * @return 根据所有上送的列表中的key返回一个相应的所有的page集合，如果为null表示有异常
     */
    private List<String> getAllPage(List<ListChartCommon> listChartCommon,
                                     List<ListChartOther> listChartOther,
                                     List<ListRowChart> listRowChart,
                                     List<ListList> listList){
        //总的list
        List<String> list = new LinkedList<>();

        List<String> list1 = getListChartCommonPage(listChartCommon);
        List<String> list2 = getListChartOtherPage(listChartOther);
        List<String> list3 = getListRowChartPage(listRowChart);
        List<String> list4 = getListListPage(listList);

        if (list1 == null || list2 == null || list3 == null|| list4 == null){
            return null;
        }else {
            list.addAll(list1);
            list.addAll(list2);
            list.addAll(list3);
            list.addAll(list4);
        }
        return list;
    }



    /**
     * 根据上送的 listChartCommons 中的key得到page，如果找不到page则返回空
     *
     * @param listChartCommons listChartCommons列表
     *
     * @return 根据上送的列表中的key返回一个相应的page集合
     */
    private List<String> getListChartCommonPage(List<ListChartCommon> listChartCommons){
        List<String> list = new LinkedList();
        for (ListChartCommon listChartCommon : listChartCommons){
            String key = listChartCommon.getKey();
            String page = reportChartRepository.getPageByKey(key);
            if (page == null){
                return null;
            }
            list.add(page);
        }
        return list;
    }

    /**
     * 根据上送的 listChartOthers 中的key得到page，如果找不到page则返回空
     *
     * @param listChartOthers listChartCommons列表
     *
     * @return 根据上送的列表中的key返回一个相应的page集合
     */
    private List<String> getListChartOtherPage(List<ListChartOther> listChartOthers){
        List<String> list = new LinkedList<>();
        for (ListChartOther listChartOther : listChartOthers){
            String page = reportChartRepository.getPageByKey(listChartOther.getKey());
            if (page == null){
                return null;
            }
            list.add(page);
        }
        return list;
    }

    /**
     * 根据上送的 listRowCharts 中的key得到page，如果找不到page则返回空
     *
     * @param listRowCharts listChartCommons列表
     *
     * @return 根据上送的列表中的key返回一个相应的page集合
     */
    private List<String> getListRowChartPage(List<ListRowChart> listRowCharts){
        List<String> list = new LinkedList<>();
        for (ListRowChart listRowChart : listRowCharts){
            String page = reportChartRepository.getPageByKey(listRowChart.getKey());
            if (page == null){
                return null;
            }
            list.add(page);
        }
        return list;
    }

    /**
     * 根据上送的 listLists 中的key得到page，如果找不到page则返回空
     *
     * @param listLists listChartCommons列表
     *
     * @return 根据上送的列表中的key返回一个相应的page集合
     */
    private List<String> getListListPage(List<ListList> listLists){
        List<String> list = new LinkedList<>();
        for (ListList listList : listLists){
            String page = reportChartRepository.getPageByKey(listList.getKey());
            if (page == null){
                return null;
            }
            list.add(page);
        }
        return list;
    }



}
