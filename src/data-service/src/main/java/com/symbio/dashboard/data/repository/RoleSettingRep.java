package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.RoleSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleSettingRep extends JpaRepository<RoleSetting, Integer> {

    RoleSetting getById(Integer id);

    @Query(value = "SELECT rs.* FROM user_group_role ugr" +
            " INNER JOIN `user` u ON u.id = ugr.userId AND u.status = 1" +
            " INNER JOIN group_info gi ON gi.id = ugr.groupId AND gi.validation = 1" +
            " INNER JOIN role_setting rs ON rs.id = ugr.roleId AND rs.validation = 1" +
            " WHERE u.id = ?1 " +
            " ORDER BY gi.isSysGroup DESC, priority" +
            " LIMIT 0,1", nativeQuery = true)
    RoleSetting getRoleSettingByUserId(Integer userId);

    @Query(value = "SELECT rs.id FROM user_group_role ugr" +
            " INNER JOIN `user` u ON u.id = ugr.userId AND u.status = 1" +
            " INNER JOIN group_info gi ON gi.id = ugr.groupId AND gi.validation = 1" +
            " INNER JOIN role_setting rs ON rs.id = ugr.roleId AND rs.validation = 1" +
            " WHERE u.id = ?1 " +
            " ORDER BY gi.isSysGroup DESC, priority" +
            " LIMIT 0,1", nativeQuery = true)
    Integer getRoleIdByUserId(Integer userId);


    @Query(value = "SELECT * FROM role_setting WHERE validation = 1 ORDER BY priority", nativeQuery = true)
    List<RoleSetting> getList();

}
