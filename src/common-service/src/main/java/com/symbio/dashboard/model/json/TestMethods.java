package com.symbio.dashboard.model.json;

import java.util.List;
import java.util.Map;

public class TestMethods {
    private Map<String, String> configuration;
    private String suite;
    private String test;
    private String packageInfo;
    private String className;
    private String methodName;
    private String status;
    private String startTime;
    private String endTime;
    private String description;
    private String exception;
    private String stacktrace;
    private String testName;

    private List<Logs> logs;

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(String packageInfo) {
        this.packageInfo = packageInfo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public List<Logs> getLogs() {
        return logs;
    }

    public void setLogs(List<Logs> logs) {
        this.logs = logs;
    }

    @Override
    public String toString() {
        return "TestMethods [suite="
                + suite
                + ", test="
                + test
                + ", packageInfo="
                + packageInfo
                + ", className="
                + className
                + ", methodName="
                + methodName
                + ", status="
                + status
                + ", startTime="
                + startTime
                + ", endTime="
                + endTime
                + ", description="
                + description
                + ", exception="
                + exception
                + ", testName="
                + testName
                + ",  logs="
                + logs
                + "]";
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }
}
