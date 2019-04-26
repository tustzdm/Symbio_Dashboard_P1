package com.symbio.dashboard.service.auth;

import com.symbio.dashboard.dto.auth.UserDto;

public interface UserService {
  UserDto selectUserById(Integer id);
}
