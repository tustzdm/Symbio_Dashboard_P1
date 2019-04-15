package com.symbio.dashboard.report.dto.qualityViewLeyout;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * qv布局信息的集合
 *
 * @author daizongheng
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
    private List listSupport = new ArrayList();

    /**
     * common chart
     */
    private List listChartCommon = new ArrayList();

    /**
     * 非 common chart
     */
    private List listChartOther = new ArrayList();

    private List listRowChartUsed = new ArrayList();

    private List listListUsed = new ArrayList();




}
