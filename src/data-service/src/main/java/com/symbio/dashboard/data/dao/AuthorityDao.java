package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.repository.*;
import com.symbio.dashboard.model.*;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@SuppressWarnings("unchecked")
@Repository
public class AuthorityDao {

    @Autowired
    private GroupInfoRep groupInfoRep;
    @Autowired
    private RoleSettingRep roleSettingRep;
    @Autowired
    private MenuRep menuRep;
    @Autowired
    private FunctionInfoRep funcRep;
    @Autowired
    private RoleMenuFunctionRep roleMenuFuncRep;

    public Group getProductGroupInfo(Integer productId) {
        return groupInfoRep.getOneByProductId(productId);
    }

    public Group saveGroupInfo(Group groupInfo) {
        return groupInfoRep.saveAndFlush(groupInfo);
    }


    public List<RoleSetting> getRoleList() {
        return roleSettingRep.getList();
    }

    public List<Menu> getMenuList() {
        return menuRep.getList();
    }

    public List<FunctionInfo> getFunctionList() {
        return funcRep.getFunctionList();
    }

    public List<FunctionInfo> getFunctionListByAccValue(Integer value) {
        return funcRep.getFunctionListByAccumulativeValue(value);
    }

    public Integer getFunctionValueByRoleMenu(Integer roleId, Integer menuId) {
        return funcRep.getFunctionTotalValue(roleId, menuId);
    }

    public List<FunctionInfo> getFunctionListByRoleMenu(Integer roleId, Integer menuId) {
        return funcRep.getFunctionListBySetting(roleId, menuId);
    }

    public Result checkMenuInfo(String locale, Integer menuId) {
        Result<Menu> retMenuInfo = new Result<>();
        String funcName = "AuthorityDao.checkMenuInfo()";

        Menu menu = menuRep.getById(menuId);
        if (CommonUtil.isEmpty(menu)) {
            log.error(ErrorConst.getWarningLogMsg(funcName, "Could not find Menu record by Id: " + menuId));
            return new Result("000016", String.format("Could not find relative data in table [%s]. id = [%d]", "Menu", menuId));
        } else {
            retMenuInfo.setCd(menu);
        }

        return retMenuInfo;
    }

    public Result<RoleSetting> checkRoleInfo(String locale, Integer roleId) {
        Result<RoleSetting> retRoleInfo = new Result<>();
        String funcName = "AuthorityDao.checkRoleInfo()";

        RoleSetting roleInfo = roleSettingRep.getById(roleId);
        if (CommonUtil.isEmpty(roleInfo)) {
            log.error(ErrorConst.getWarningLogMsg(funcName, "Could not find role_setting record by Id: " + roleId));
            return new Result("000016", String.format("Could not find relative data in table [%s]. id = [%d]", "role_setting", roleId));
        } else {
            retRoleInfo.setCd(roleInfo);
        }

        return retRoleInfo;
    }

    public RoleMenuFunction saveRoleMenuFunction(RoleMenuFunction data) {
        return roleMenuFuncRep.saveAndFlush(data);
    }

}
