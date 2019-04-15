package com.symbio.dashboard.report.dto.QualityOverview;

import lombok.Data;

/**
 * 本类用于对QualityOverview进行一个整合，将所有的QualityOverview内部的对象进行一个封装，返回一个总的json串
 *
 * @author daizongheng
 */
@Data
public class QualityOverview {

    /**
     * 错误码，返回"0"表示正常
     */
    private String ec;

    /**
     * 错误信息
     */
    private String em;

    /**
     * 生成一个cd对象
     */
    private QualityOverviewCd cd;


}
