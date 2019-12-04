package com.symbio.dashboard.data.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.SystemListSetting;
import com.symbio.dashboard.model.SysListSetting;

import java.util.List;

public interface DataCommonService {

    Result getUserPageRole(EnumDef.DASHBOARD_PAGE page, Integer userId);

    Result getUserPageRole(String funcName, EnumDef.DASHBOARD_PAGE page, Integer userId);

    List<String> getQueryFields(SystemListSetting listType, List<SysListSetting> listSetting);

    Integer getSqlCount(String sql);
}
