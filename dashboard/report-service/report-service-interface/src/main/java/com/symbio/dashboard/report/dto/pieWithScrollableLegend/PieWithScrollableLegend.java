package com.symbio.dashboard.report.dto.pieWithScrollableLegend;

import lombok.Data;

/**
 * 本类用于返回PieWithScrollableLegend的图信息给前台
 *
 * @author daizongheng
 */
@Data
public class PieWithScrollableLegend {

    /**
     * 用于表示标题
     */
    private String title;

    /**
     * 用于表示图例对象
     */
    private PieWithScrollableLegendLegend pieWithScrollableLegendLegend = new PieWithScrollableLegendLegend();

    /**
     * 用于表示数据对象
     */
    private PieWithScrollableLegendSeries pieWithScrollableLegendSeries = new PieWithScrollableLegendSeries();

}
