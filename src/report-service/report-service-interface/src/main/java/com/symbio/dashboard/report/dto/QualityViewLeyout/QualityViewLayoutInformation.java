package com.symbio.dashboard.report.dto.qualityViewLeyout;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 返回的是qv布局信息集合中的详细信息,如果为null则不返回
 *
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QualityViewLayoutInformation {

    /**
     * 页面具体位置,从数据库中获取
     *  因为pos表示坐标，有可能为Array,有可能为Integer
     *
     *  有的需要，有的不需要，所以默认为null
     */
    private Object pos = null;

    /**
     * 显示顺序
     */
    private Integer idx;

    /**
     * 键值
     */
    private String key;

    /**
     * 名称
     */
    private String name;

    /**
     * 控件类型
     *  1.通用控件（默认）
     *  2.other控件
     *  3.list
     *  4.行控件
     */
    private Integer type = 1;
}
