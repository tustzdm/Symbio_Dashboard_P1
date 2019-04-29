package com.symbio.dashboard.report.dto.qualityViewLeyout;

import lombok.Data;

/**
 * 本类用于返回qv布局信息中的 listLabel 集合中的对象
 */

@Data
public class QualityViewLayoutCDLabel {

    /**
     * key
     */
    private String key;

    /**
     * 名称
     */
    private String label;
}
