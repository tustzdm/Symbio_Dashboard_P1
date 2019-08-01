package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.UiInfo;
import org.springframework.stereotype.Service;

@Service
public interface PageElementService {
    Result getUiInfoList(String locale, String page);

    Result removeUiElement(String locale, Integer id);

    Result updateUiElement(Integer userId, String locale, UiInfo data);
}
