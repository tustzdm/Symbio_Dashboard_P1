package com.symbio.dashboard.test.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public class GetTestSetListAuthServiceImpl implements GetTestSetListAuthService {
    @Override
    public Result getTestSetList(String token) {
        return getTestSetListResult(token);
    }

    private Result getTestSetListResult(String token) {

        return new Result(token);
    }
}
