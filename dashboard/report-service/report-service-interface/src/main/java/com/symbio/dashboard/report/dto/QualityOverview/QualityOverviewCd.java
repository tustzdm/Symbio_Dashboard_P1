package com.symbio.dashboard.report.dto.QualityOverview;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 本类用于一个QualityOverview对象中的cd变量的一个封装
 *
 */
@Data
public class QualityOverviewCd {

    /**
     * token值
     */
    private String token;

    /**
     *语言
     */
    private String locale;



    /**
     * listCombox集合
     */
    private List listCombox = new ArrayList();

    /**
     * listCharts集合
     */
    private List listCharts = new ArrayList();

    /**
     * listList集合
     */
    private List listList = new ArrayList();

    /**
     * listRowCharts集合
     */
    private List listRowCharts = new ArrayList();


}
