package com.symbio.dashboard.navigation.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public class GetReleaseAuthImpl implements GetReleaseAuth {

    @Override
    public Result getReleaseAuth(String token) {
        return getReleaseAuthByToken(token);
    }

    private Result getReleaseAuthByToken(String token) {
        Result result = new Result();
        result.setCdAndRightEcAndEm("release token");
        return result;
    }
}
