package com.symbio.dashboard.report.dro.saveUploadInformation;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 本类用于存放上送的listChartCommon信息对象
 */

@Data
public class ListChartCommon {

//    @JSONField(name = "pos")
    private Integer pos[];

//    @JSONField(name = "key")
    private String key;

}
