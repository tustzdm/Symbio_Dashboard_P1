package com.symbio.dashboard.enums;

import lombok.Getter;

@Getter
public enum MenuType implements EnumDef.IDictEnum {
    QUALITY_OVERVIEW(1, "Quality Overview", "Menu"),
    TEST_MANAGEMENT(2, "Test Management", "Menu"),
    RESULT_REVIEW(3, "Result Review", "Menu"),
    BUGS_OVERVIEW(4, "Bugs Overview", "Menu"),

    LAYOUT(10, "LayOut", "Setting"),
    PAGE_ELEMENT(11, "Page Element", "Setting"),

    ISSUE_TYPE(30, "Issue type", "Admin"),
    FILE_INFORMATION(31, "File information", "Admin"),
    SETTING(32, "Setting", "Admin"),
    TEP_LIST(33, "TEP list", "Admin");

    private Integer code;
    private String value;
    private String group;

    MenuType(int code, String value, String group) {
        this.code = code;
        this.value = value;
        this.group = group;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }

    public String getGroup() {
        return this.group;
    }
}
