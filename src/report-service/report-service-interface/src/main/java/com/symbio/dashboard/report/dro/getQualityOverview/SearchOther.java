package com.symbio.dashboard.report.dro.getQualityOverview;

import lombok.Data;

import java.util.Map;

/**
 * 本类作为search中的other对象
 */

@Data
public class SearchOther {

    private String key;

    private Map<String,String> data;
}
