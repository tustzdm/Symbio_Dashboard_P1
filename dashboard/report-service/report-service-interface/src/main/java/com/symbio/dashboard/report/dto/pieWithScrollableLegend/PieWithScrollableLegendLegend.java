package com.symbio.dashboard.report.dto.pieWithScrollableLegend;

import lombok.Data;

import java.util.List;

/**
 * 用于封装PieWithScrollableLegend中的图例对象
 *
 * @author daizongheng
 */

@Data
public class PieWithScrollableLegendLegend {

    private List data;

    private String orient;

    private Integer right;

    private Integer top;

    private Integer bottom;

    private Integer selected;

}
