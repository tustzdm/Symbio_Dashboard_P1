package com.symbio.dashboard.navigation.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

/**
 * 得到导航条中的product的权限认证接口
 */

@Service
public interface GetProductAuthService {
    Result getProductAuth(String token);
}
