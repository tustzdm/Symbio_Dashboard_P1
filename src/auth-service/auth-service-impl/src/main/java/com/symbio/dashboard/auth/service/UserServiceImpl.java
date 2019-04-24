package com.symbio.dashboard.auth.service;

import com.symbio.dashboard.auth.dto.UserDto;
import com.symbio.dashboard.auth.model.User;
import com.symbio.dashboard.auth.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;

  @Override
  public UserDto selectUserById(Integer id) {
    User one = userRepository.getOne(id);
    return new UserDto(one.getName(),one.getPassword());
  }
}
