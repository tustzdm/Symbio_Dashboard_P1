package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.data.repository.FunctionInfoRep;
import com.symbio.dashboard.data.repository.GroupInfoRep;
import com.symbio.dashboard.data.repository.MenuRep;
import com.symbio.dashboard.data.repository.RoleSettingRep;
import com.symbio.dashboard.model.FunctionInfo;
import com.symbio.dashboard.model.Group;
import com.symbio.dashboard.model.Menu;
import com.symbio.dashboard.model.RoleSetting;
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

    public Integer getFunctionValueByRoleMenu(Integer roleId, Integer menuId) {
        return funcRep.getFunctionTotalValue(roleId, menuId);
    }

}
