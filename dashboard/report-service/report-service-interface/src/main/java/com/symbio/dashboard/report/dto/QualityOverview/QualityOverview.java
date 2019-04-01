package com.symbio.dashboard.report.dto.QualityOverview;

import lombok.Data;

/**
 * 本类用于对QualityOverview进行一个整合，将所有的QualityOverview内部的对象进行一个封装，返回一个总的json串
 *
 * @author daizongheng
 */
@Data
public class QualityOverview {

    private String ec;

    private String em;

    /**
     * 生成一个cd对象
     */
    private QualityOverviewCd cd;

    /**
     * 为了测试json串，定义构造方法写死成员变量，以后成员变量的具体内容从数据库或前台中获取
     */
    public QualityOverview(){
        ec = "0";
        em = "";
        cd = new QualityOverviewCd();
    }
}
