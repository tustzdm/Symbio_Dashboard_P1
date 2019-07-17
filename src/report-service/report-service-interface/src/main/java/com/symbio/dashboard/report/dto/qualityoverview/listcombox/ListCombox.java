package com.symbio.dashboard.report.dto.qualityoverview.listcombox;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 本类用于返回给前端页面的PieScrollLegend具体信息
 *  暂时以json测试串为主
 *
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListCombox {

    /**
     * 关键字
     */
    private String key;

    /**
     * 类型的名字
     */
    private String name;

    /**
     * 内容为对象列表
     */
    private List<ListComboxCondition> condition = null;


}
