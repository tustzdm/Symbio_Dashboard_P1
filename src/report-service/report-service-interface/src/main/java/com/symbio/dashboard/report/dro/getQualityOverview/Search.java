package com.symbio.dashboard.report.dro.getQualityOverview;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 本类是getOualityOverview的上送信息对象
 */

@Data
@AllArgsConstructor
public class Search {

    /**
     * 0.common
     * 1.other report
     */
    private Integer isCommon;

    /**
     * iscommon==1时为null
     */
    private SearchCommon searchCommon;

    /**
     * iscommon==0时为null
     */
    private SearchOther searchOther;
}
