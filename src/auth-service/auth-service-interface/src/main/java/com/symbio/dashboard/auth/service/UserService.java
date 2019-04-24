package com.symbio.dashboard.auth.service;

import com.symbio.dashboard.auth.dto.UserDto;

public interface UserService {
  UserDto selectUserById(Integer id);
}
