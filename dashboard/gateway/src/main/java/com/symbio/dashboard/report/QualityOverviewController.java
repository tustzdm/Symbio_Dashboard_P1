package com.symbio.dashboard.report;


import com.symbio.dashboard.report.dto.listCharts.barLabelRotation.BarLabelRotation;
import com.symbio.dashboard.report.dto.listCharts.barLabelRotation.BarSimple;
import com.symbio.dashboard.report.dto.listCharts.stackedLineChart.StackedLineChart;
import com.symbio.dashboard.report.dto.listCombox.PieScrollLegend;
import com.symbio.dashboard.report.dto.listList.ListProductStatistics;
import com.symbio.dashboard.report.dto.listList.ListProductStatisticsDataInData;
import com.symbio.dashboard.report.dto.listRowCharts.MixLineBar;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 本类用于对QualityOverview的一个控制，用于控制返回的内容信息
 *
 * @author daizongheng
 */


@Slf4j //用于添加日志信息
@Data
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
     * 为了测试接口，此构造函数用于初始化需要返回的对象
     */
    public QualityOverviewController(){
        super();

        stackedLineChart = new StackedLineChart();
        barLabelRotation = new BarLabelRotation();
        mixLineBar = new MixLineBar();
        listProductStatistics = new ListProductStatistics();
        barSimple = new BarSimple();
        pieScrollLegend = new PieScrollLegend();
    }

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
}
