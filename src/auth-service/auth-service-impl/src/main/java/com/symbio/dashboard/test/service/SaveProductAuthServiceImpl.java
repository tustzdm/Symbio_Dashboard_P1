package com.symbio.dashboard.test.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@SuppressWarnings("unchecked")
@Service
public class SaveProductAuthServiceImpl implements SaveProductAuthService {

    @Override
    public Result saveProduct(String token) {
        return getSaveProductResult(token);
    }

    private Result getSaveProductResult(String token) {

        return new Result(token);
    }
}
