package com.symbio.dashboard.model.json;

import com.symbio.dashboard.util.JSONUtil;

import java.util.HashMap;
import java.util.Map;

public class ReportSummary {
    private MethodsSummary testMethodsSummary;

    private MethodsSummary configurationMethodsSummary;

    public MethodsSummary getTestMethodsSummary() {
        return testMethodsSummary;
    }

    public void setTestMethodsSummary(MethodsSummary testMethodsSummary) {
        this.testMethodsSummary = testMethodsSummary;
    }

    public MethodsSummary getConfigurationMethodsSummary() {
        return configurationMethodsSummary;
    }

    public void setConfigurationMethodsSummary(MethodsSummary configurationMethodsSummary) {
        this.configurationMethodsSummary = configurationMethodsSummary;
    }

    @Override
    public String toString() {
        return "ReportSummary {testMethodsSummary="
                + testMethodsSummary
                + ", configurationMethodsSummary="
                + configurationMethodsSummary
                + "}";
    }

    public String toJsonString() {
        Map<String, Object> map = new HashMap<>();
        map.put("testMethodsSummary", testMethodsSummary);
        map.put("configurationMethodsSummary", configurationMethodsSummary);
        return JSONUtil.mapToString(map);
    }
}
