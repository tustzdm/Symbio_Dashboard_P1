package com.symbio.dashboard.model.json;

public class ConfigurationMethods {
    private String type;
    private String suite;
    private String test;
    private String packageInfo;
    private String className;
    private String methodName;
    private String status;
    private String startTime;
    private String endTime;
    private String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    @Override
    public String toString() {
        return "ConfigurationMethods [type="
                + type
                + ", suite="
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
                + "]";
    }
}
