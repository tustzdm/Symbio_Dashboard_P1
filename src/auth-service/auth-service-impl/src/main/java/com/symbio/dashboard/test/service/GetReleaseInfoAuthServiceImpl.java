package com.symbio.dashboard.test.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public class GetReleaseInfoAuthServiceImpl implements GetReleaseInfoAuthService {
    @Override
    public Result getReleaseInfoAuth(String token) {
        return getReleaseInfoAuthResult(token);
    }

    private Result getReleaseInfoAuthResult(String token) {
        Result result = new Result();
        result.setCdAndRightEcAndEm(token);
        return result;
    }
}
