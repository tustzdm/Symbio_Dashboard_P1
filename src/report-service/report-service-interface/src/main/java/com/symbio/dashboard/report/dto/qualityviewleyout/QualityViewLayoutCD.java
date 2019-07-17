package com.symbio.dashboard.report.dto.qualityviewleyout;

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
     * listLabel
     */
    private List listLabel = new LinkedList();

    /**
     *未设定的界面元素
     */
    private List listSupport = new LinkedList();

    /**
     * common chart
     */
    private List listCharts = new LinkedList();

    /**
     * 非 common chart
     */
    private List OtherReport = new LinkedList();

    private List listRowChart = new LinkedList();

    private List listList = new LinkedList();




}
