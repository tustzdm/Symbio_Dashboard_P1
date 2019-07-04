package com.symbio.dashboard.ui.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public interface RemoveUiElementAuthService {
    Result removeUiElementAuth(String token);
}
