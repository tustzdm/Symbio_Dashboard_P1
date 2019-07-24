package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

    String getMessageEx(String code);

    String getMessageEx(String code, Object... args);

    String getMessage(String locale, String code, Object... args);

    Result getResultEx(String code);

    Result getResultEx(String code, Object... args);

    Result getResult(String locale, String code, Object... args);

}
