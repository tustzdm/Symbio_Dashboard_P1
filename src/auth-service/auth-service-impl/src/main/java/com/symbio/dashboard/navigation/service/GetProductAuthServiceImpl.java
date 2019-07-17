package com.symbio.dashboard.navigation.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public class GetProductAuthServiceImpl implements GetProductAuthService {
    @Override
    public Result getProductAuth(String token) {
        return getProductAuthByToken(token);
    }

    private Result getProductAuthByToken(String token) {
        return new Result("product token");
    }
}
