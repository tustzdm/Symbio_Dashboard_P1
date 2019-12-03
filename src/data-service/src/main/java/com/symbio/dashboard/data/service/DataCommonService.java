package com.symbio.dashboard.data.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.enums.EnumDef;

public interface DataCommonService {

    Result getUserPageRole(EnumDef.DASHBOARD_PAGE page, Integer userId);

    Result getUserPageRole(String funcName, EnumDef.DASHBOARD_PAGE page, Integer userId);
}
