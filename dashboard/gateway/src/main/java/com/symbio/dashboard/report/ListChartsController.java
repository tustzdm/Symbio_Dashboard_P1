package com.symbio.dashboard.report;


import com.symbio.dashboard.report.dto.stackedLineChart.StackedLineChart;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 本类用于对ListCharts接口的一个控制
 *
 * @author daizongheng
 */


@Slf4j //用于添加日志信息
@Data
@RestController //相当于@ResponseBody+@Controller
@RequestMapping("/api/listCharts")
public class ListChartsController {
    /**
     * 成员对象用于封装返回到前端的具体信息
     */
    private StackedLineChart stackedLineChart;

    /**
     * 为了测试接口，此构造函数用于初始化需要返回的对象
     */
    public ListChartsController(){
        super();

        stackedLineChart = new StackedLineChart();
    }

    /**
     * 此方法用于调用返回StackedLineChart类型的数据
     *
     * 测试接口：
     *  localhost:8080/api/listCharts/getStackedLineChart
     *
     * @return 返回前端StackedLineChart类型的对象
     */
    @RequestMapping("/getStackedLineChart")
    public StackedLineChart getStackedLineChart(){
        return stackedLineChart;
    }

}
