package com.symbio.dashboard.test.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public interface GetProductListAuthService {
    Result getProductList(String token);
}
