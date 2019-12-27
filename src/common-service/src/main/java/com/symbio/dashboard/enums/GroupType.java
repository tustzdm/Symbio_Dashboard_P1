package com.symbio.dashboard.enums;

import lombok.Getter;

@Getter
public enum GroupType implements EnumDef.IDictEnum {
    SYSTEM(0, "System role group"),
    COMPANY(1, "Company group"),
    PRODUCT(2, "Product group");

    private Integer code;
    private String value;

    GroupType(int code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }

    // alias
    public String getGroupName() {
        return getValue();
    }
}
