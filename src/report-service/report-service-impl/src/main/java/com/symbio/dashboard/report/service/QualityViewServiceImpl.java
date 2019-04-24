package com.symbio.dashboard.report.service;

import com.symbio.dashboard.report.dro.getQualityOverview.Search;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartCommon;
import com.symbio.dashboard.report.dto.QualityOverview.QualityOverview;
import com.symbio.dashboard.report.dto.QualityOverview.QualityOverviewCd;
import com.symbio.dashboard.report.dto.QualityOverview.listCharts.barLabelRotation.BarLabelRotation;
import com.symbio.dashboard.report.dto.QualityOverview.listCharts.stackedLineChart.StackedLineChart;
import com.symbio.dashboard.report.dto.QualityOverview.listCombox.ListCombox;
import com.symbio.dashboard.report.dto.QualityOverview.listList.ListProductStatistics;
import com.symbio.dashboard.report.dto.QualityOverview.listOther.OtherReport;
import com.symbio.dashboard.report.dto.QualityOverview.listRowCharts.MixLineBar;
import com.symbio.dashboard.report.dto.qualityViewLeyout.*;
import com.symbio.dashboard.report.repository.ReportChartRepository;
import com.symbio.dashboard.report.repository.SettingLayoutRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 本类用于实现QualityViewService接口，返回相应的QualityView信息给前台
 */
@Service
@Data
public class QualityViewServiceImpl implements QualityViewService {


    @Autowired
    private QualityViewLayoutService qualityViewLayout;

    @Override
    public QualityOverview getQualityOverview(Map<String,Integer> role, String locale, Search search) {

        //1 auth
        //2 role

        //

        return setQualityOverview(role,locale,search);

    }

    /**
     * 此方法用于生成一个QualityOverview对象
     *
     * @param role 权限
     * @param locale 语种
     * @param search search对象
     *
     * @return 返回一个QualityOverview对象
     */
    private QualityOverview setQualityOverview(Map<String,Integer> role, String locale, Search search){

        QualityOverview qualityOverview  = new QualityOverview();
        QualityOverviewCd qualityOverviewCd = setQualityOverviewCd(role,locale,search);

        if (qualityOverviewCd == null){
            qualityOverview.setEc("N000003");
            qualityOverview.setEm("null exception");
        }else{
            qualityOverview.setEc("0");
            qualityOverview.setEm("");
            qualityOverview.setCd(qualityOverviewCd);
        }

        return qualityOverview;
    }

    /**
     * 本方法用于生成QualityOverview中的QualityOverviewCd对象
     * @param role 权限
     * @param locale 语种
     * @param search search对象
     * @return 返回一个拼装好的QualityOverviewCd对象
     */
    private QualityOverviewCd setQualityOverviewCd(Map<String,Integer> role, String locale, Search search){
        QualityOverviewCd qualityOverviewCd = new QualityOverviewCd();

        qualityOverviewCd.setLocale(locale);
        qualityOverviewCd.setRole(role);
        qualityOverviewCd.setSearch(search);
        qualityOverviewCd.setSelectedItem(createSeletedItem(search));


        // 调用getLayout接口
        QualityViewLayout qualityViewLayout = this.qualityViewLayout.getQualityViewLayout(locale);


        if (qualityViewLayout == null){
            return null;
        }

        // 4个list
        QualityViewLayoutCD cd = qualityViewLayout.getCd();
        if (cd == null){
            return null;
        }else {
            qualityOverviewCd.setListCharts(createListCharts(search,cd.getListCharts()));
            qualityOverviewCd.setListList(createListList(search,cd.getListList()));
            qualityOverviewCd.setListRowCharts(createListRowCharts(search,cd.getListRowChart()));
            qualityOverviewCd.setOtherReport(createListChartOther(search,cd.getOtherReport()));
        }


        //listCombox
        qualityOverviewCd.setListCombox(createListCombox(search,qualityViewLayout.getCd().getOtherReport()));





        return qualityOverviewCd;
    }


    /**
     * 此方法用于组装selectedItem
     * @param search 上送的search对象
     * @return 返回组装好的selectedItem列表
     */
    private List createSeletedItem(Search search){
        List list = new LinkedList();
        if (new Integer(0).equals(search.getIsCommon())){ // 0, "0", null
            list.add("common");
        }else if (new Integer(1).equals(search.getIsCommon())){
            list.add("common");
            //TODO 根据search中other里面的内容创建list
        }

//        (Integer.parseInt(search.getIsCommon()) == 0 )


        return list;
    }


    private List createListCombox(Search search,List<QualityViewLayoutCDChartOther> cdChartOthers){
        List list = new LinkedList();
        ListCombox listComboxNo1 = new ListCombox();
        listComboxNo1.setName("common");
        listComboxNo1.setKey("common");
        list.add(listComboxNo1);

        for (QualityViewLayoutCDChartOther q : cdChartOthers){
            ListCombox listCombox = new ListCombox();
            listCombox.setKey(q.getKey());
            listCombox.setName(q.getName());

            if (new Integer(1).equals(search.getIsCommon())){
                //TODO 根据search创建相应的condition
            }

            list.add(listCombox);

        }
        return list;
    }

    /**
     * 本方法用于组装ListChart
     * @param search 上送的search对象
     * @param cdChartCommonshartCommonList 调用getLayout接口得到的ListChartCommon集合
     * @return 组装好的listChart列表
     */
    private List createListCharts(Search search, List<QualityViewLayoutCDChartCommon> cdChartCommonshartCommonList){

        List list = new LinkedList();
        for (QualityViewLayoutCDChartCommon q : cdChartCommonshartCommonList){
            String key = q.getKey();
            if ("StackedLine".equals(key)){
                StackedLineChart stackedLineChart = new StackedLineChart();
                stackedLineChart.setKey(key);
                stackedLineChart.setPos(q.getPos());

                //TODO 通过search封装data
                stackedLineChart.setData(null);

                list.add(stackedLineChart);
            }else if ("BarLabRotation".equals(key)){
                BarLabelRotation barLabelRotation = new BarLabelRotation();
                barLabelRotation.setKey(key);
                barLabelRotation.setPos(q.getPos());

                barLabelRotation.setData(null);

                list.add(barLabelRotation);
            }
        }
        return list;
    }


    /**
     * 本方法用于组装ListList
     * @param search 上送的search对象
     * @param cdLists 调用getLayout接口得到的ListList集合
     * @return 返回一个组装好的listlist列表
     */
    private List createListList(Search search, List<QualityViewLayoutCDList> cdLists){
        List list = new LinkedList();

        for (QualityViewLayoutCDList q : cdLists){
            String key = q.getKey();
            if ("listProductStatistics".equals(key)){
                ListProductStatistics listProductStatistics = new ListProductStatistics();
                listProductStatistics.setPos(q.getPos());
                listProductStatistics.setKey(key);

                listProductStatistics.setData(null);

                list.add(listProductStatistics);
            }
        }

        return list;
    }


    /**
     * 本方法用于组装ListRowChart
     * @param search 上送的search对象
     * @param cdRowCharts 调用getLayout接口得到的ListRowCharts集合
     * @return 返回一个组装好的ListRowChart列表
     */
    private List createListRowCharts(Search search, List<QualityViewLayoutCDRowChart> cdRowCharts){
        List list = new LinkedList();
        for (QualityViewLayoutCDRowChart q : cdRowCharts){
            String key = q.getKey();
            if("MixLineBar".equals(key)){
                MixLineBar mixLineBar = new MixLineBar();
                mixLineBar.setKey(key);
                mixLineBar.setPos(q.getPos());

                mixLineBar.setData(null);

                list.add(mixLineBar);
            }
        }
        return list;
    }


    /**
     * 此方法用于组装ListChartOther
     * @param search 上送的search对象
     * @param cdChartOthers 调用getLayout接口得到的ListChartOthers集合
     * @return 返回一个组装好的ListChartOther列表
     */
    private List createListChartOther(Search search, List<QualityViewLayoutCDChartOther> cdChartOthers){
        List list = new LinkedList();
        for (QualityViewLayoutCDChartOther q : cdChartOthers){
            String key = q.getKey();
            OtherReport otherReport = new OtherReport();
            otherReport.setKey(key);

            otherReport.setData(null);

            list.add(otherReport);

        }
        return list;
    }





}
