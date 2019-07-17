package com.symbio.dashboard.report.dto.qualityoverview;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.symbio.dashboard.report.dro.getQualityOverview.Search;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 本类用于一个QualityOverview对象中的cd变量的一个封装
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QualityOverviewCd {


    /**
     *语言
     */
    private String locale;

    /**
     * 权限
     */
    private Map<String,Integer> role;

    /**
     * 上送的search对象
     */
    private Search search;

    /**
     * 下拉列表框
     */
    private List selectedItem = new LinkedList();

    /**
     * listCombox集合
     */
    private List listCombox = new LinkedList();

    /**
     * listCharts集合
     */
    private List listCharts = new LinkedList();

    /**
     * listList集合
     */
    private List listList = new LinkedList();

    /**
     * listRowCharts集合
     */
    private List listRowCharts = new LinkedList();

    /**
     * listChartOther集合
     */
    private List otherReport = null;


}
