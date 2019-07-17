package com.symbio.dashboard.test.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;


@Service
public class GetReleaseListAuthServiceImpl implements GetReleaseListAuthService {
    @Override
    public Result getReleaseList(String token) {
        return getReleaseListResult(token);
    }

    private Result getReleaseListResult(String token) {

        return new Result(token);
    }
}
