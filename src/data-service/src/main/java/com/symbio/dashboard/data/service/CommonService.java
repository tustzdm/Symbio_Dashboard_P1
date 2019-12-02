package com.symbio.dashboard.data.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.enums.EnumDef;

public interface CommonService {

    Result getUserPageRole(EnumDef.DASHBOARD_PAGE page, Integer userId);

    Result getUserPageRole(String funcName, EnumDef.DASHBOARD_PAGE page, Integer userId);
}
