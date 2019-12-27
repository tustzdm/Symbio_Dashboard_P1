package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.RoleMenuFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuFunctionRep extends JpaRepository<RoleMenuFunction, Integer> {

    RoleMenuFunction getById(Integer id);

    @Query(value = "SELECT fi.* FROM role_menu_function rmf" +
            " INNER JOIN role_setting rs ON rs.id = rmf.roleId AND rs.validation = 1" +
            " INNER JOIN menu ON menu.id = rmf.menuId AND menu.validation = 1" +
            " INNER JOIN function_info fi ON fi.id = rmf.functionId and fi.validation = 1" +
            " WHERE rmf.roleId = ?1" +
            " AND menu.id = ?2", nativeQuery = true)
    List<RoleMenuFunction> getRoleMenuFunctionSetting(Integer roleId, Integer menuId);
}
