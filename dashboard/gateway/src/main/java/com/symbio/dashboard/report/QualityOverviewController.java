package com.symbio.dashboard.report;


import com.symbio.dashboard.report.dto.listCharts.barLabelRotation.BarLabelRotation;
import com.symbio.dashboard.report.dto.listCharts.stackedLineChart.StackedLineChart;
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
     * 为了测试接口，此构造函数用于初始化需要返回的对象
     */
    public QualityOverviewController(){
        super();

        stackedLineChart = new StackedLineChart();
        barLabelRotation = new BarLabelRotation();
        mixLineBar = new MixLineBar();
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


}
