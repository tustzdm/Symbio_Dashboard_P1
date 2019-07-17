package com.symbio.dashboard.report.dto.qualityoverview.listother;

import lombok.Data;

/**
 * 本方法用于返回给前端的listChartsOther列表中的对象
 */

@Data
public class OtherReport {

    private String key;

    private OtherReportData data;
}
