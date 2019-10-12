package com.symbio.dashboard.dto;

import com.symbio.dashboard.enums.Locales;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 本类作为 Result Review UI info的接口数据类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultReviewUiDTO implements Serializable {

    private String locale;
    private Integer role;

    // page info
    private Integer pageIndex;
    private Integer pageSize;
    private Integer totalRecord;
    private Integer dataType;  // 1: Array[Array], 2: Array[Map]

    private String sourceLocale;
    private String targetLocale;
    private Integer testRunId;

    private List columns;
    private List<String> fields;
    private List data;

    // Current selected item
    // private Integer localeId;
    private List<Map<String, Object>> listLocales;
    private List<Map<String, Object>> listStatus;

    public ResultReviewUiDTO(String locale, Integer pageIndex, Integer pageSize) {
        this.locale = locale;
        this.pageIndex = pageIndex == null ? 0 : pageIndex;
        this.pageSize = pageSize == null ? 20 : pageSize;
        this.sourceLocale = Locales.EN_US.toString();
    }
}
