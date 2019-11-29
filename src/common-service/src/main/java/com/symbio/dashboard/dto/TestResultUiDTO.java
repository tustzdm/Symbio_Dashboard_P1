package com.symbio.dashboard.dto;

import com.symbio.dashboard.model.TestCase;
import com.symbio.dashboard.model.TestResult;
import com.symbio.dashboard.model.TestRun;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

    private TestCase testCase;
    private TestRun testRun;
    private TestResult testResult;

    private Map<String, Object> mapTestResult;
    private List<Map<String, Object>> listScreenShots;

    private List<Map<String, Object>> listAutoStatus;
    private List<Map<String, Object>> listQAStatus;

    public TestResultUiDTO(String locale, Integer testRunId) {
        this.locale = locale;
        this.testRunId = testRunId;
    }
}
