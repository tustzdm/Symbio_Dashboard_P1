package com.symbio.dashboard.report.dto.qualityViewLeyout;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 本类用于返回qv布局信息中的 listList 集合中的对象
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QualityViewLayoutCDList {

    /**
     * 坐标
     */
    private Integer pos;

    /**
     * 顺序
     */
    private Integer idx;

    /**
     * 键值
     */
    private String key;

    /**
     * key对应的名字
     */
    private String name;

    /**
     * 控件类型
     *  1.通用控件（默认）
     *  2.other控件
     *  3.list
     *  4.行控件
     */
    private Integer type;

}
