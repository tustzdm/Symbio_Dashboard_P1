package com.symbio.dashboard.enums;

public enum JenkinsParameter {
    BooleanType("0"),
    StringType("1"),
    NumberType("2"),
    ChoiceType("3"),
    FileType("4");

    private String type;

    private JenkinsParameter(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
