package com.symbio.dashboard.navigation.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public class GetTestSetAuthImpl implements GetTestSetAuth {
    @Override
    public Result getTestSetAuth(String token) {
        return getTestSetAuthByToken(token);
    }

    private Result getTestSetAuthByToken(String token) {
        Result result = new Result();
        result.setCdAndRightEcAndEm("testSet token");
        return result;
    }
}
