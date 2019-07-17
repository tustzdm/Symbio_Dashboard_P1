package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public interface CommonService {
    Result getDictionaryInfo(String type);
}
