package com.symbio.dashboard.dto;

import com.symbio.dashboard.model.BugInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本类作为 BugReport UI info的接口数据类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugReportUiDTO implements Serializable {

    private String locale;
    private Integer role;

    private Integer dataType;  // 1: Array[Array], 2: Array[Map]

    private List columns;
    private List<String> fields;
    private BugInfo bugInfo;
    private List data;

    // Fix variable
    private Integer testRunId;
    private Integer testResultId;
    private Integer screenshotId;

    private Map<String, Object> mapSetting;

    public BugReportUiDTO(String locale) {
        this.locale = locale;
        this.mapSetting = new HashMap<>();
    }
}
