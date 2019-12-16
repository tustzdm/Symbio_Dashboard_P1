package com.symbio.dashboard.data.charts;

import com.symbio.dashboard.enums.ChartsType;

public interface ChartProvider {

    ChartGenerate produce(ChartsType type);
}
