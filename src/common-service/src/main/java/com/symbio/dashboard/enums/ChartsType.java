package com.symbio.dashboard.enums;

import lombok.Getter;

@Getter
public enum ChartsType implements EnumDef.IDictEnum {

    STACKED_LINE(1, 1, "StackedLine", ""),
    BAR_CATEGORY(2, 1, "BarCategoryStack", "/json/BarCategoryStack.json"),
    BAR_SIMPLE(3, 1, "BarSimple", ""),
    PIE_SCROLL_LEGEND(4, 2, "PieScrollLegend", ""),
    BAR_MIX_LINE(5, 4, "MixLineBar", ""),
    PIE_REFER(6, 2, "PieRefer", "/json/PieRefererChart.json");

    private Integer code;
    private Integer type;
    private String value;
    private String resourceFileName;

    ChartsType(int code, int type, String value, String fileName) {
        this.code = code;
        this.type = type;
        this.value = value;
        this.resourceFileName = fileName;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }
}
