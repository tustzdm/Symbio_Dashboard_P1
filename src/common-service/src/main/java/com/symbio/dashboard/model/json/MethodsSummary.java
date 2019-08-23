package com.symbio.dashboard.model.json;

public class MethodsSummary {
    private Integer passed;

    private Integer failed;

    private Integer skipped;

    public Integer getPassed() {
        return passed;
    }

    public void setPassed(Integer passed) {
        this.passed = passed;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public Integer getSkipped() {
        return skipped;
    }

    public void setSkipped(Integer skipped) {
        this.skipped = skipped;
    }

    @Override
    public String toString() {
        return " {passed=" + passed + ", failed=" + failed + ", skipped=" + skipped + "}";
    }
}
