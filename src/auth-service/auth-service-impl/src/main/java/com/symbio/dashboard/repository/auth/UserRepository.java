package com.symbio.dashboard.repository.auth;

import com.symbio.dashboard.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

}
