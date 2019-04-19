package com.symbio.dashboard.report.dto.qualityViewLeyout;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * qv布局信息的集合
 *
 */
@Data
public class QualityViewLayoutCD {

    /**
     * 语种
     */
    private String locale;

    /**
     * 菜单权限
     */
    private Integer role;

    /**
     *未设定的界面元素
     */
    private List listSupport = new LinkedList();

    /**
     * common chart
     */
    private List listChartCommon = new LinkedList();

    /**
     * 非 common chart
     */
    private List listChartOther = new LinkedList();

    private List listRowChartUsed = new LinkedList();

    private List listListUsed = new LinkedList();




}
