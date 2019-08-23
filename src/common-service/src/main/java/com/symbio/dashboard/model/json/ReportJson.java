package com.symbio.dashboard.model.json;

import java.util.List;

public class ReportJson {
    private ReportSummary reportSummary;

    private List<TestMethods> testMethods;

    private List<ConfigurationMethods> configurationMethods;

    private ConfigSummary configSummary;

    private List<LocalConfigSummary> localConfigSummary;

    private ReporterMetadata reporterMetadata;

    public ReportSummary getReportSummary() {
        return reportSummary;
    }

    public void setReportSummary(ReportSummary reportSummary) {
        this.reportSummary = reportSummary;
    }

    public List<TestMethods> getTestMethods() {
        return testMethods;
    }

    public void setTestMethods(List<TestMethods> testMethods) {
        this.testMethods = testMethods;
    }

    public List<ConfigurationMethods> getConfigurationMethods() {
        return configurationMethods;
    }

    public void setConfigurationMethods(List<ConfigurationMethods> configurationMethods) {
        this.configurationMethods = configurationMethods;
    }

    public ConfigSummary getConfigSummary() {
        return configSummary;
    }

    public void setConfigSummary(ConfigSummary configSummary) {
        this.configSummary = configSummary;
    }

    public List<LocalConfigSummary> getLocalConfigSummary() {
        return localConfigSummary;
    }

    public void setLocalConfigSummary(List<LocalConfigSummary> localConfigSummary) {
        this.localConfigSummary = localConfigSummary;
    }

    public ReporterMetadata getReporterMetadata() {
        return reporterMetadata;
    }

    public void setReporterMetadata(ReporterMetadata reporterMetadata) {
        this.reporterMetadata = reporterMetadata;
    }
}
