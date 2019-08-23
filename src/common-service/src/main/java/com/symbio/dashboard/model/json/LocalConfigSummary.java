package com.symbio.dashboard.model.json;

public class LocalConfigSummary {
    private String currentDate;
    private String suite;
    private String test;

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
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

    @Override
    public String toString() {
        return "LocalConfigSummary [currentDate="
                + currentDate
                + ", suite="
                + suite
                + ", test="
                + test
                + "]";
    }
}
