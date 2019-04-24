package com.symbio.dashboard.auth;

import com.symbio.dashboard.auth.dto.UserDto;
import com.symbio.dashboard.auth.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Data
@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  @GetMapping("/get/{id}")
  public UserDto get(@PathVariable Integer id){
    return userService.selectUserById(id);
  }
}
