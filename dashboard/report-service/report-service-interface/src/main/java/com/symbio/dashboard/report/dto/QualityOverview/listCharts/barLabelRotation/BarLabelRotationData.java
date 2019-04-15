package com.symbio.dashboard.report.dto.QualityOverview.listCharts.barLabelRotation;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本类为返回给前端的BarLabelRotation对象的具体数据内容
 *
 * @author daizongheng
 */

@Data
public class BarLabelRotationData {
    /**
     * color用于表示颜色，暂定用list接收数据库color信息
     */
    private List color;

    /**
     * legend暂定使用list接收后台legend信息
     */
    private List legend;

    /**
     * xData用于表示年份，暂定用list接收后台数据库XData信息
     */
    private List xData;

    /**
     * groupby用于表示图表显示的方向
     *
     * 暂定为1
     */
    private Integer groupby = 1;

    /**
     * 用于表示图表内容信息
     */
    private Map seriesData;


}
