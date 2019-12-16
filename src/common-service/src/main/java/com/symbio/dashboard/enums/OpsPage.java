package com.symbio.dashboard.enums;

import lombok.Getter;

@Getter
public enum OpsPage implements EnumDef.IDictEnum {

    QUALITY_OVERVIEW(100, "Quality Overview", ""),

    TEST_MANAGEMENT(200, "Test Management", ""), // PRODUCT
    PRODUCT(210, "Product", "testmgmt.product"),
    RELEASE(220, "Release", "testmgmt.release"),
    TEST_SET(230, "Test Set", "testmgmt.testset"),
    TEST_CASE(240, "Test Case", "testmgmt.testcase"),

    EXECUTE_REVIEW(300, "Execute Review", ""),
    TEST_RUN(310, "Test Run", ""),
    TEST_RESULT(320, "Test Result", ""),
    IMAGE_COMPARE(330, "Image Compare", ""),

    BUGS_OVERVIEW(400, "Bugs Overview", "");

    private Integer code;
    private String value;
    private String chartPrefix;

    OpsPage(int code, String value, String chartPrefix) {
        this.code = code;
        this.value = value;
        this.chartPrefix = chartPrefix;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }
}
