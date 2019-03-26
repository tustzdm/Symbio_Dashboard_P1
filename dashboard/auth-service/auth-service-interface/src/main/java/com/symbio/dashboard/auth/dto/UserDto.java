package com.symbio.dashboard.auth.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDto {

  private Integer id;
  private String name;
  private String password;

  public UserDto(String name, String password) {
    this.name = name;
    this.password = password;
  }

}
