package com.symbio.dashboard.navigation.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public interface GetTestSetAuthService {
    Result<String> getTestSetAuth(String token);
}
