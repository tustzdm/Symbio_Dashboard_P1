package com.symbio.dashboard.test.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

/**
 * @ClassName - ProductAuthServiceImpl
 * @Author - admin
 * @Description
 * @Date - 2019/7/17 17:07
 * @Version 1.0
 */

@Service
@SuppressWarnings("unchecked")
public class ProductAuthServiceImpl implements ProductAuthService {
    @Override
    public Result getProductListAuth(String token) {
        return getProductListAuthResult(token);
    }

    private Result getProductListAuthResult(String token) {
        return new Result(token);
    }

    @Override
    public Result editProductAuth(String token) {
        return editProductAuthResult(token);
    }

    private Result editProductAuthResult(String token) {
        return new Result(token);
    }

    @Override
    public Result removeProductAuth(String token) {
        return removeProductAuthResult(token);
    }

    private Result removeProductAuthResult(String token) {
        return new Result(token);
    }
}
