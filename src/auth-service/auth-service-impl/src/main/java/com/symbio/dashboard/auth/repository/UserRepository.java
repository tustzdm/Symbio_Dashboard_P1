package com.symbio.dashboard.auth.repository;

import com.symbio.dashboard.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

}
