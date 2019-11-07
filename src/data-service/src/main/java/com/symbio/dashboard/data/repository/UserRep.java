package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRep extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user ORDER BY id", nativeQuery = true)
    List<User> getUserListByProduct(Integer productId);

    @Query(value = "SELECT * FROM user WHERE `status` = ? ORDER BY id", nativeQuery = true)
    List<User> getUserListByStatus(Integer status);

    @Override
    User getOne(Integer id);

    @Query(value = "SELECT * FROM `user` WHERE id = ?", nativeQuery = true)
    User getById(Integer id);

    @Query(value = "SELECT * FROM `user` WHERE name = ?1 and status = ?2", nativeQuery = true)
    User getUserByName(String name, Integer status);

    @Query(value = "SELECT * FROM user ORDER BY id", nativeQuery = true)
    List<User> getAllUser();

    @Query(value = "SELECT * FROM `user` WHERE name = ?1 and status != 4 LIMIT 0,1", nativeQuery = true)
    User getLoginUserInfo(String name);

}
