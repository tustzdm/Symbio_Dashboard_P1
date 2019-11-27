package com.symbio.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * 本类作为 TestRun Run的接口数据类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRunDTO implements Serializable {

    private String token;
    private String locale;
    private Integer userId;

    private Integer testSetId;
    private Integer tepId;
    private String ids;
    private Map<String, Object> parameters;

    public TestRunDTO(String locale) {
        this.locale = locale;
    }
}
