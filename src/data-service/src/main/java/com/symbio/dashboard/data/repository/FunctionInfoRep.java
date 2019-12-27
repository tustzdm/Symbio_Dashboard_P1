package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.FunctionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionInfoRep extends JpaRepository<FunctionInfo, Integer> {

    FunctionInfo getById(Integer id);

    @Query(value = "SELECT IFNULL(sum(fi.fValue),0) as fValue FROM role_menu_function rmf" +
            " INNER JOIN role_setting rs ON rs.id = rmf.roleId AND rs.validation = 1" +
            " INNER JOIN menu ON menu.id = rmf.menuId AND menu.validation = 1" +
            " INNER JOIN function_info fi ON fi.id = rmf.functionId and fi.validation = 1" +
            " WHERE rmf.roleId = ?1" +
            " AND menu.id = ?2", nativeQuery = true)
    Integer getFunctionTotalValue(Integer roleId, Integer menuId);

    @Query(value = "SELECT fi.* FROM role_menu_function rmf" +
            " INNER JOIN role_setting rs ON rs.id = rmf.roleId AND rs.validation = 1" +
            " INNER JOIN menu ON menu.id = rmf.menuId AND menu.validation = 1" +
            " INNER JOIN function_info fi ON fi.id = rmf.functionId and fi.validation = 1" +
            " WHERE rmf.roleId = ?1" +
            " AND menu.id = ?2", nativeQuery = true)
    List<FunctionInfo> getFunctionListBySetting(Integer roleId, Integer menuId);

    @Query(value = "SELECT fi.* FROM role_menu_function rmf" +
            " INNER JOIN menu ON menu.id = rmf.menuId AND menu.validation = 1" +
            " INNER JOIN function_info fi ON fi.id = rmf.functionId and fi.validation = 1" +
            " WHERE rmf.roleId = ?1 " +
            " AND (menu.name = ?2 )" + // or menu.code = 1
            " AND (fi.name = ?3", nativeQuery = true)
        //  or fi.fvalue = 2)
    List<FunctionInfo> getFunctionByRoleMenuFunctionName(Integer roleId, String menuName, String funcName);

    @Query(value = "SELECT fi.* FROM role_menu_function rmf" +
            " INNER JOIN menu ON menu.id = rmf.menuId AND menu.validation = 1" +
            " INNER JOIN function_info fi ON fi.id = rmf.functionId and fi.validation = 1" +
            " WHERE rmf.roleId = ?1 " +
            " AND (menu.code = ?2)" +
            " AND (fi.fvalue = ?3)", nativeQuery = true)
    List<FunctionInfo> getFunctionByRoleMenuFunctionCode(Integer roleId, String menuCode, String funcCode);

    @Query(value = "SELECT fi.* FROM " +
            "(SELECT rs.id FROM user_group_role ugr" +
            " INNER JOIN `user` u ON u.id = ugr.userId AND u.status = 1" +
            " INNER JOIN group_info gi ON gi.id = ugr.groupId AND gi.validation = 1" +
            " INNER JOIN role_setting rs ON rs.id = ugr.roleId AND rs.validation = 1" +
            " WHERE u.id = ?1 " +
            " ORDER BY gi.isSysGroup DESC, priority" +
            " LIMIT 0,1) role" +
            " INNER JOIN role_menu_function rmf ON role.id = rmf.roleId" +
            " INNER JOIN menu ON menu.id = rmf.menuId AND menu.validation = 1" +
            " INNER JOIN function_info fi ON fi.id = rmf.functionId and fi.validation = 1" +
            " WHERE menu.code = ?2" +
            " AND fi.fvalue = ?3", nativeQuery = true)
    List<FunctionInfo> getFunctionByUserIdMenuFunctionCode(Integer userId, String menuCode, String funcCode);

    @Query(value = "SELECT fi.* FROM " +
            "(SELECT rs.id FROM user_group_role ugr" +
            " INNER JOIN `user` u ON u.id = ugr.userId AND u.status = 1" +
            " INNER JOIN group_info gi ON gi.id = ugr.groupId AND gi.validation = 1" +
            " INNER JOIN role_setting rs ON rs.id = ugr.roleId AND rs.validation = 1" +
            " WHERE u.id = ?1 " +
            " ORDER BY gi.isSysGroup DESC, priority" +
            " LIMIT 0,1) role" +
            " INNER JOIN role_menu_function rmf ON role.id = rmf.roleId" +
            " INNER JOIN menu ON menu.id = rmf.menuId AND menu.validation = 1" +
            " INNER JOIN function_info fi ON fi.id = rmf.functionId and fi.validation = 1" +
            " WHERE menu.name = ?2" +
            " AND fi.name = ?3", nativeQuery = true)
    List<FunctionInfo> getFunctionByUserIdMenuFunctionName(Integer userId, String menuName, String funcName);

    @Query(value = "SELECT * FROM function_info WHERE validation = 1 ORDER BY id", nativeQuery = true)
    List<FunctionInfo> getFunctionList();

    @Query(value = "SELECT * FROM function_info WHERE (fvalue & ?1 > 0) AND validation = 1 ORDER BY id", nativeQuery = true)
    List<FunctionInfo> getFunctionListByAccumulativeValue(Integer value);

}
