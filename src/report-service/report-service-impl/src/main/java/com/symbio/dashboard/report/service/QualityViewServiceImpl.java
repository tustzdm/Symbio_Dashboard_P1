package com.symbio.dashboard.report.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.report.dro.getQualityOverview.Search;
import com.symbio.dashboard.report.dto.QualityOverview.QualityOverviewCd;
import com.symbio.dashboard.report.dto.QualityOverview.listCharts.barLabelRotation.BarLabelRotation;
import com.symbio.dashboard.report.dto.QualityOverview.listCharts.stackedLineChart.StackedLineChart;
import com.symbio.dashboard.report.dto.QualityOverview.listCombox.ListCombox;
import com.symbio.dashboard.report.dto.QualityOverview.listList.ListProductStatistics;
import com.symbio.dashboard.report.dto.QualityOverview.listOther.OtherReport;
import com.symbio.dashboard.report.dto.QualityOverview.listRowCharts.MixLineBar;
import com.symbio.dashboard.report.dto.qualityViewLeyout.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Result getQualityOverview(Map<String,Integer> role, String locale, Search search) {

        //1 auth
        //2 role

        //
        Result result = setQualityOverview(role,locale,search);

        return result;

    }

    /**
     * 此方法用于生成一个QualityOverview对象
     *
     * @param role 权限
     * @param locale 语种
     * @param search search对象
     *
     * @return cd对象中存放的是QualityOverviewCD 返回一个QualityOverviewCD对象
     */
    private Result setQualityOverview(Map<String,Integer> role, String locale, Search search){
        return setQualityOverviewCd(role,locale,search);
    }

    /**
     * 本方法用于生成QualityOverview中的QualityOverviewCd对象
     * @param role 权限
     * @param locale 语种
     * @param search search对象
     * @return cd内的对象是一个拼装好的QualityOverviewCd对象
     */
    private Result setQualityOverviewCd(Map<String,Integer> role, String locale, Search search){
        Result result = new Result();

        QualityOverviewCd qualityOverviewCd = new QualityOverviewCd();

        qualityOverviewCd.setLocale(locale);
        qualityOverviewCd.setRole(role);
        qualityOverviewCd.setSearch(search);

        result = createSeletedItem(search);
        if (result.hasError()){
            return result;
        }else {
            qualityOverviewCd.setSelectedItem((List) result.getCd());
        }

        // 调用getLayout接口
        result = this.qualityViewLayout.getQualityViewLayout(locale);
        Result result1 = result;


        if (result.hasError()){
            return result;
        }

        // 4个list
        QualityViewLayoutCD cd = (QualityViewLayoutCD) result.getCd();
        if (cd == null){
            result.setEc("N20000");
            result.setEm("对象不能为空");
            result.setCd(null);
            return result;
        }else {
            try {
                result = createListCharts(search, cd.getListCharts());
                if (result.hasError()) {
                    return result;
                } else {
                    qualityOverviewCd.setListCharts((List) result.getCd());
                }

                result = createListList(search, cd.getListList());
                if (result.hasError()) {
                    return result;
                } else {
                    qualityOverviewCd.setListList((List) result.getCd());
                }

                result = createListRowCharts(search, cd.getListRowChart());
                if (result.hasError()) {
                    return result;
                } else {
                    qualityOverviewCd.setListRowCharts((List) result.getCd());
                }

                result = createListChartOther(search, cd.getOtherReport());
                if (result.hasError()) {
                    return result;
                } else {
                    qualityOverviewCd.setOtherReport((List) result.getCd());
                }

                //最后做这个
                result = createListCombox(search,cd.getOtherReport());
                if (result.hasError()){
                    return result;
                }else {
                    qualityOverviewCd.setListCombox((List) result.getCd());
                }
            }catch (ClassCastException ex){
                ex.printStackTrace();
            }

        }

        result.setCdAndRightEcAndEm(qualityOverviewCd);
        return result;
    }


    /**
     * 此方法用于组装selectedItem
     * @param search 上送的search对象
     * @return cd中的对象是List 返回组装好的selectedItem列表
     */
    private Result createSeletedItem(Search search){
        Result result = new Result();
        List list = new LinkedList();
        if (new Integer(0).equals(search.getIsCommon())){ // 0, "0", null
            list.add("common");
        }else if (new Integer(1).equals(search.getIsCommon())){
            list.add("common");
            //TODO 根据search中other里面的内容创建list
        }else {
            result.setEc("N10001");
            result.setEm("没有这个数字");
            return result;
        }

        result.setCdAndRightEcAndEm(list);
        return result;
    }


    /**
     * 此方法用于组装ListCombox
     * @param search 上送的search对象
     * @param cdChartOthers 调用getLayout接口得到的ListChartOther集合
     * @return cd中的对象是 组装好的ListCombox列表
     */
    private Result createListCombox(Search search,List<QualityViewLayoutCDChartOther> cdChartOthers){
        Result result = new Result();
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
            }else {
                result.setEc("N100001");
                result.setEm("iscommon只能为0或者1，不能为别的");
                return result;
            }

            list.add(listCombox);

        }
        result.setCdAndRightEcAndEm(list);
        return result;
    }

    /**
     * 本方法用于组装ListChart
     * @param search 上送的search对象
     * @param cdChartCommonshartCommonList 调用getLayout接口得到的ListChartCommon集合
     * @return cd中的对象是 组装好的listChart列表
     */
    private Result createListCharts(Search search, List<QualityViewLayoutCDChartCommon> cdChartCommonshartCommonList){
        Result result = new Result();

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
            }else {
                result.setEc("N1000001");
                result.setEm("listChart列表不能存这个key");
                result.setCd(null);
                return result;
            }
        }
        result.setCdAndRightEcAndEm(list);
        return result;
    }


    /**
     * 本方法用于组装ListList
     * @param search 上送的search对象
     * @param cdLists 调用getLayout接口得到的ListList集合
     * @return cd中的对象是 返回一个组装好的listlist列表
     */
    private Result createListList(Search search, List<QualityViewLayoutCDList> cdLists){
        Result result = new Result();
        List list = new LinkedList();

        for (QualityViewLayoutCDList q : cdLists){
            String key = q.getKey();
            if ("listProductStatistics".equals(key)){
                ListProductStatistics listProductStatistics = new ListProductStatistics();
                listProductStatistics.setPos(q.getPos());
                listProductStatistics.setKey(key);

                listProductStatistics.setData(null);

                list.add(listProductStatistics);
            }else {
                result.setEc("N1000005");
                result.setEm("listList列表不能存这个key");
                result.setCd(null);
                return result;
            }
        }
        result.setCdAndRightEcAndEm(list);
        return result;
    }


    /**
     * 本方法用于组装ListRowChart
     * @param search 上送的search对象
     * @param cdRowCharts 调用getLayout接口得到的ListRowCharts集合
     * @return cd中的对象是 返回一个组装好的ListRowChart列表
     */
    private Result createListRowCharts(Search search, List<QualityViewLayoutCDRowChart> cdRowCharts){
        Result result = new Result();

        List list = new LinkedList();
        for (QualityViewLayoutCDRowChart q : cdRowCharts){
            String key = q.getKey();
            if("MixLineBar".equals(key)){
                MixLineBar mixLineBar = new MixLineBar();
                mixLineBar.setKey(key);
                mixLineBar.setPos(q.getPos());

                mixLineBar.setData(null);

                list.add(mixLineBar);
            }else {
                result.setEc("N1000003");
                result.setEm("ListRowChart 列表不能存这个key");
                result.setCd(null);
                return result;
            }
        }
        result.setCdAndRightEcAndEm(list);
        return result;
    }


    /**
     * 此方法用于组装ListChartOther
     * @param search 上送的search对象
     * @param cdChartOthers 调用getLayout接口得到的ListChartOthers集合
     * @return cd中的对象是 返回一个组装好的ListChartOther列表
     */
    private Result createListChartOther(Search search, List<QualityViewLayoutCDChartOther> cdChartOthers){
        Result result = new Result();
        List list = new LinkedList();
        for (QualityViewLayoutCDChartOther q : cdChartOthers){
            String key = q.getKey();
            OtherReport otherReport = new OtherReport();
            otherReport.setKey(key);
            otherReport.setData(null);
            //TODO 错误的提示

            list.add(otherReport);

        }
        result.setCdAndRightEcAndEm(list);
        return result;
    }

}
