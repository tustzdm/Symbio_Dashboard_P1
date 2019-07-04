package com.symbio.dashboard.ui.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public interface GetUiInfoListAuthService {
    Result getUiInfoListAuth(String token);
}
