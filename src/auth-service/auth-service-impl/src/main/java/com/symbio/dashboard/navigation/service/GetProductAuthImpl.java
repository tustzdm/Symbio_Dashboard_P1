package com.symbio.dashboard.navigation.service;

import com.symbio.dashboard.Result;

public class GetProductAuthImpl implements GetProductAuth {
    @Override
    public Result getProductAuth(String token) {
        return getProductAuthByToken(token);
    }

    private Result getProductAuthByToken(String token) {
        Result result = new Result();
        result.setCdAndRightEcAndEm("aaaa");
        return result;
    }
}
