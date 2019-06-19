package com.symbio.dashboard.test.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public class GetProductListAuthServiceImpl implements GetProductListAuthService {
    @Override
    public Result getProductList(String token) {
        return getProductListResult(token);
    }

    private Result getProductListResult(String token) {
        Result result = new Result();

        //token
        result.setCdAndRightEcAndEm(token);
        return result;
    }
}
