package com.symbio.dashboard.test.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public class GetProductInfoAuthServiceImpl implements GetProductInfoAuthService {
    @Override
    public Result getProductInfo(String token) {
        return getProductInfoResult(token);
    }

    private Result getProductInfoResult(String token) {

        return new Result(token);
    }
}
