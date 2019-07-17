package com.symbio.dashboard.test.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public class SaveTestSetAuthServiceImpl implements SaveTestSetAuthService {
    @Override
    public Result saveTestSetAuth(String token) {
        return saveTestSetResult(token);
    }

    private Result saveTestSetResult(String token) {

        return new Result(token);
    }
}
