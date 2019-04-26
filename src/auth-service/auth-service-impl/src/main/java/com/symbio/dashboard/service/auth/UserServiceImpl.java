package com.symbio.dashboard.service.auth;

import com.symbio.dashboard.dto.auth.UserDto;
import com.symbio.dashboard.model.auth.User;
import com.symbio.dashboard.repository.auth.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserDto selectUserById(Integer id) {
    User one = userRepository.getOne(id);
    return new UserDto(one.getName(),one.getPassword());
  }
}
