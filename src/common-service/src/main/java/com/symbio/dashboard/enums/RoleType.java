package com.symbio.dashboard.enums;

import lombok.Getter;

@Getter
public enum RoleType implements EnumDef.IDictEnum {
    USER(0, "User"), // CompanyUser

    ADMIN(1, "Admin"),
    COMPANY_MANAGER(2, "CompanyManager"),
    COMPANY_ASSISTANT(3, "CompanyAssistant"),
    PRODUCT_MANAGER(5, "ProductManager"),
    PRODUCT_USER(6, "ProductUser"),

    CLIENT_MANAGER(7, "ClientManager"),
    CLIENT(8, "Client"),

    QA_LEADER(20, "QA Leader"),
    QA(21, "QA"),

    IQA_LEADER(22, "IQA Leader"),
    IQA(23, "IQA"),

    LQA_LEADER(24, "LQA Leader"),
    LQA(25, "LQA"),

    AUTOMATION_LEADER(30, "AutomationLeader"),
    AUTOMATION(31, "Automation"),

    ENGINEER_LEADER(35, "EngineerLeader"),
    ENGINEER(36, "Engineer"),

    DEVELOPER_LEADER(38, "DeveloperLeader"),
    DEVELOPER(39, "Developer"),

    ANONYMOUS_USER(1948, "AnonymousUser"),
    SYSTEM_ADMIN(912, "SystemAdmin");

    private Integer code;
    private String value;

    RoleType(int code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }

    // alias
    public String getRoleName() {
        return getValue();
    }
}
