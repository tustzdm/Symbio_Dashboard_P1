package com.symbio.dashboard.test.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public interface ProductAuthService {

    Result getProductListAuth(String token);

    Result editProductAuth(String token);

    Result removeProductAuth(String token);
}
