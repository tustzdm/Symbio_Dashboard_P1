package com.symbio.dashboard.dto;

import com.symbio.dashboard.model.ScreenShot;
import com.symbio.dashboard.model.TestResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 本类作为 TestResult Info 的接口数据类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestResultUiDTO implements Serializable {

    private String locale;
    private Integer role;
    private Integer testRunId;

    private TestResult testResult;
    private List<ScreenShot> listScreenShots;

    //private List<Map<String, Object>> productList;

    public TestResultUiDTO(String locale, Integer testRunId) {
        this.locale = locale;
        this.testRunId = testRunId;
    }
}
