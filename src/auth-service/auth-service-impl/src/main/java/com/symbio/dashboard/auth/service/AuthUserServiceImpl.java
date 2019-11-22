package com.symbio.dashboard.auth.service;

/**
 * @Author: Shawn
 * @version: 1.0
 * @Date: 2019/11/22
 */

import com.symbio.dashboard.Result;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class AuthUserServiceImpl implements AuthUserService {

  @Override
  public Result getUserInfo(String token) {
    return null;
  }
}
