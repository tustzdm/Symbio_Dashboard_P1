package com.symbio.dashboard.report;


import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartCommon;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartOther;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListList;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListRowChart;
import com.symbio.dashboard.report.dto.QualityOverview.QualityOverview;
import com.symbio.dashboard.report.dto.QualityOverview.QualityOverviewCd;
import com.symbio.dashboard.report.dto.qualityViewLeyout.QualityViewLayout;
import com.symbio.dashboard.report.dto.QualityOverview.listCharts.barLabelRotation.BarLabelRotation;
import com.symbio.dashboard.report.dto.QualityOverview.listCharts.barLabelRotation.BarSimple;
import com.symbio.dashboard.report.dto.QualityOverview.listCharts.stackedLineChart.StackedLineChart;
import com.symbio.dashboard.report.dto.QualityOverview.listCombox.PieScrollLegend;
import com.symbio.dashboard.report.dto.QualityOverview.listList.ListProductStatistics;
import com.symbio.dashboard.report.dto.QualityOverview.listList.ListProductStatisticsDataInData;
import com.symbio.dashboard.report.dto.QualityOverview.listRowCharts.MixLineBar;
import com.symbio.dashboard.report.dto.QualityOverview.pieWithScrollableLegend.PieWithScrollableLegend;
import com.symbio.dashboard.report.dto.saveQualityViewLeyout.SaveQualityViewLeyout;
import com.symbio.dashboard.report.service.QualityViewLayoutService;
import com.symbio.dashboard.report.service.SaveQualityViewLeyoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 本类用于对QualityOverview的一个控制，用于控制返回的内容信息
 *
 * @author daizongheng
 */


@Slf4j //用于添加日志信息
@RequiredArgsConstructor
@RestController //相当于@ResponseBody+@Controller
@RequestMapping("/menu")
public class QualityOverviewController {
    /**
     * 成员对象用于封装返回到前端的stackedLineChart具体信息
     */
    private StackedLineChart stackedLineChart;

    /**
     * 成员对象用于封装返回到前端的barLabelRotation具体信息
     */
    private BarLabelRotation barLabelRotation;

    /**
     * 成员对象用于封装返回到前端的mixLineBar具体信息
     */
    private MixLineBar mixLineBar;

    /**
     * 成员对象用于封装返回到前端的ListProductStatistics具体信息
     */
    private ListProductStatistics listProductStatistics;

    /**
     * 成员对象用于封装返回到前端的BarSimple具体信息
     */
    private BarSimple barSimple;

    /**
     * 成员对象用于封装返回到前端的PieScrollLegend具体信息
     */
    private PieScrollLegend pieScrollLegend;

    /**
     * 成员对象用于封装返回到前端的PieWithScrollableLegend具体信息
     */
    private PieWithScrollableLegend pieWithScrollableLegend;

    /**
     * 成员对象用于封装返回所有的json串内容，测试最终的getQualityOverview接口
     */
    private QualityOverview qualityOverview;


    /**
     * 成员对象用于封装返回给前端的最终页面布局json串，测试最终getQualityViewLayout接口
     */
    private final QualityViewLayoutService qualityViewLayout;

    /**
     * 成员对象用于在前端页面布局保存时，将布局信息保存到数据库中之后，返回给前端信息
     */
    private final SaveQualityViewLeyoutService saveQualityViewLeyout;


    /**
     * 此方法用于调用返回StackedLineChart类型的数据
     *
     * 测试接口：
     *  localhost:8080/menu/getQualityOverview/getStackedLineChart
     *
     * @return 返回给前端StackedLineChart类型的对象json串
     */
    @RequestMapping("/getQualityOverview/getStackedLineChart")
    public StackedLineChart getStackedLineChart(){
        return stackedLineChart;
    }

    /**
     * 此方法用于调用返回BarLabelRotation类型的数据
     *
     * 测试接口：
     *  localhost:8080/menu/getQualityOverview/getBarLabelRotation
     *
     * @return 返回给前端BarLabelRotation类型的对象json串
     */
    @RequestMapping("/getQualityOverview/getBarLabelRotation")
    public BarLabelRotation getBarLabelRotation(){
        return barLabelRotation;
    }

    /**
     * 此方法用于调用返回MixLineBar类型的数据
     *
     * 测试接口：
     *  localhost:8080/menu/getQualityOverview/getMixLineBar
     *
     * @return 返回给前端MixLineBar类型对象json串
     */
    @RequestMapping("/getQualityOverview/getMixLineBar")
    public MixLineBar getMixLineBar(){
        return mixLineBar;
    }


    /**
     * 此方法用于测试ListProductStatisticsData中的data按照field的json返回顺序
     *
     * 测试接口：
     *  localhost:8080/menu/getQualityOverview/getListProductStatisticsDataInData
     *
     * @return 返回json串给前台
     */
    @RequestMapping("/getQualityOverview/getListProductStatisticsDataInData")
    public ListProductStatisticsDataInData get(){
        return new ListProductStatisticsDataInData();
    }

    /**
     * 此方法用于调用返回ListProductStatistics类型的数据
     *
     * 测试接口：
     *  localhost:8080/menu/getQualityOverview/getListProductStatistics
     *
     * @return 返回给前端的ListProductStatistics类型对象的json串
     */
    @RequestMapping("/getQualityOverview/getListProductStatistics")
    public ListProductStatistics getListProductStatistics(){
        return listProductStatistics;
    }

    /**
     * 此方法用于调用返回BarSimple类型的数据
     *
     * 测试接口：
     *  localhost:8080/menu/getQualityOverview/getBarSimple
     *
     * @return 返回给前端的BarSimple类型对象的json串
     */
    @RequestMapping("/getQualityOverview/getBarSimple")
    public BarSimple getBarSimple(){
        return barSimple;
    }

    /**
     * 此方法用于调用要返回的PieScrollLegend类型的数据
     *
     * 测试接口：
     *  localhost:8080/menu/getQualityOverview/getPieScrollLegend
     *
     * @return 返回给前端的PieScrollLegend类型对象的json串
     */
    @RequestMapping("/getQualityOverview/getPieScrollLegend")
    public PieScrollLegend getPieScrollLegend(){
        return pieScrollLegend;
    }


    /**
     * 此方法用于调用要返回的PieWithScrollableLegend类型的数据
     *
     * 测试接口：
     *  localhost:8080/menu/getQualityOverview/getPieWithScrollableLegend
     *
     * @return 返回给前端的PieWithScrollableLegend类型对象的json串
     */
    @RequestMapping("/getQualityOverview/getPieWithScrollableLegend")
    public PieWithScrollableLegend getPieWithScrollableLegend(){
        return pieWithScrollableLegend;
    }


    /**
     * 此方法用于调用返回总的QualityOverview类型数据，测试最终的json串
     *
     * 测试接口：
     *  localhost:8080/menu/getQualityOverview
     *
     * @return 返回给前端最终的QualityOverview类型对象的json串
     */

    @RequestMapping("/getQualityOverview/{key}")
    public QualityOverview getQualityOverview(@PathVariable String key){
//        String key = "StackedLine";

        QualityOverviewCd qc = new QualityOverviewCd();
        if ("StackedLine".equals(key)){
            List listCharts = new ArrayList();
            listCharts.add(new StackedLineChart());
            qc.setListCharts(listCharts);

            qualityOverview.setCd(qc);
        }


        if ("BarLabRotation".equals(key)){
            List listCharts = new ArrayList();
            listCharts.add(new BarLabelRotation());
            qc.setListCharts(listCharts);
            qualityOverview.setCd(qc);
        }

        if ("listProductStatistics".equals(key)){
            List listList = new ArrayList();
            listList.add(new ListProductStatistics());
            qc.setListCharts(listList);
            qualityOverview.setCd(qc);
        }

        if ("MixLineBar".equals(key)){
            List listRowCharts = new ArrayList();
            listRowCharts.add(new MixLineBar());
            qc.setListRowCharts(listRowCharts);
            qualityOverview.setCd(qc);
        }

        if ("PieWithScrollLegend".equals(key)) {
            List list = new ArrayList();
            list.add(new PieWithScrollableLegend());
            qc.setListCombox(list);
            qualityOverview.setCd(qc);
        }

    return qualityOverview;
    }


    /**
     * 此方法用于调用返回总的QualityViewLayout类型数据，测试最终的json串
     *
     * 测试接口：
     *  localhost:8080/menu/getQualityViewLayout/zh_cn
     *
     *  为了测试语种信息，url中暂时带一个String类型的local（语种信息）
     *
     *
     * @return 返回给前端最终的QualityViewLayout类型对象的json串
     */
    @RequestMapping("/getQualityViewLayout/{locale}")
    public QualityViewLayout getQualityViewLayout(@PathVariable String locale){
        return qualityViewLayout.getQualityViewLayout(locale);
    }


    /**
     * 本方法用于前端布局页面确定后调用接口，将页面布局信息存放到数据库中
     *
     * 测试接口：
     *  localhost:8080/menu/saveQualityViewLeyout
     *
     * @param token 用户token值 前台必须传
     * @param locale 语种 前台必须传
     * @param listChartCommons listChartCommon块布局
     * @param listChartOthers listChartOther块布局
     * @param listRowCharts listRowChart块布局
     * @param listLists listList块布局
     *
     * @return 返回给前端存储布局信息后的反馈信息
     */
    @RequestMapping("/saveQualityViewLeyout")
    public SaveQualityViewLeyout saveQualityViewLeyout(@RequestParam(value = "token") String token,
                                                       @RequestParam(value = "locale") String locale,
                                                       @RequestParam(value = "listChartCommon",required = false) List<ListChartCommon> listChartCommons,
                                                       @RequestParam(value = "listChartOther",required = false) List<ListChartOther> listChartOthers,
                                                       @RequestParam(value = "listRowChart",required = false) List<ListRowChart> listRowCharts,
                                                       @RequestParam(value = "listList",required = false) List<ListList> listLists){

        //test
        Integer a[] = {1,2};
        List list = new LinkedList();
        ListChartCommon listChartCommon = new ListChartCommon();
        listChartCommon.setKey("StackedLine");
        listChartCommon.setPos(a);
        ListChartCommon listChartCommon1 = new ListChartCommon();
        listChartCommon1.setKey("BarLabRotation");
        listChartCommon1.setPos(a);
        list.add(listChartCommon);
        list.add(listChartCommon1);

        List list2 = new LinkedList();
        List list3 = new LinkedList();
        List list4 = new LinkedList();




        return saveQualityViewLeyout.getSaveFeedback(locale,list,list2,list3,list4);
    }



}
