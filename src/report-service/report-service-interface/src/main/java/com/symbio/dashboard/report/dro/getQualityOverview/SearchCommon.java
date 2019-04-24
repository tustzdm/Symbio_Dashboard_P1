package com.symbio.dashboard.report.dro.getQualityOverview;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 本类作为search的common对象
 */

@Data
@AllArgsConstructor
public class SearchCommon {

    private String product;

    private String release;

    private String testset;
}
