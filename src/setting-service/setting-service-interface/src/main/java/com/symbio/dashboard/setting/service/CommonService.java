package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public interface CommonService {
    Result getDictionaryInfo(String type);

    Result getDictionaryByType(String type);

    // 得到Table下用户可定义的字段列表
    Result getUserDefinedFields(String locale, String table);

    String getConfigValue(String key);
}
