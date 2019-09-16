package com.symbio.dashboard.enums;

import lombok.Getter;

@Getter
public enum FunctionalityType implements EnumDef.IDictEnum {
    EDIT(0x01, "Edit"),
    CREATE(0x02, "Create"),
    DELETE(0x04, "Delete"),

    COPY(0x08, "Copy"),
    EXPORT(0x10, "Export"),
    IMPORT(0x20, "Import"),

    RUN(0x40, "RunScript"),
    Email(0x80, "Email");

    private Integer code;
    private String value;

    FunctionalityType(int code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }
}
