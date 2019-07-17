package com.symbio.dashboard.navigation.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public class GetTestSetAuthServiceImpl implements GetTestSetAuthService {
    @Override
    public Result getTestSetAuth(String token) {
        return getTestSetAuthByToken(token);
    }

    private Result getTestSetAuthByToken(String token) {
        return new Result("testSet token");
    }
}
