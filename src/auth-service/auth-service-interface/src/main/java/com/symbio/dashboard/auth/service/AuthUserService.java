package com.symbio.dashboard.auth.service;

/**
 * @Author: Shawn
 * @version: 1.0
 * @Date: 2019/11/22
 */

import com.symbio.dashboard.Result;

public interface AuthUserService {
  Result getUserInfo(String token);

  Result<Integer> getUserRole(String page, Integer userId);
}
