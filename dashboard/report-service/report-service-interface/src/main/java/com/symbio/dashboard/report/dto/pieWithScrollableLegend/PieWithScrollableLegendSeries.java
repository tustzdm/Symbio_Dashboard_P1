package com.symbio.dashboard.report.dto.pieWithScrollableLegend;

import lombok.Data;

import java.util.List;

/**
 * 用于存放PieWithScrollableLegend中的数据对象
 */
@Data
public class PieWithScrollableLegendSeries {


    private String name;

    private String radius;

    private List center;

    private List data;


}
