package com.symbio.dashboard.navigation.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unchecked")
public class GetTestSetAuthServiceImpl implements GetTestSetAuthService {
    @Override
    public Result<String> getTestSetAuth(String token) {
        return getTestSetAuthByToken(token);
    }

    private Result<String> getTestSetAuthByToken(String token) {
        return new Result("testSet token");
    }
}
