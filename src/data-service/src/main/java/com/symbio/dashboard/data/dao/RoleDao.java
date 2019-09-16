package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.data.repository.FunctionInfoRep;
import com.symbio.dashboard.enums.FunctionalityType;
import com.symbio.dashboard.enums.MenuType;
import com.symbio.dashboard.model.FunctionInfo;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unchecked")
@Repository
@Slf4j
public class RoleDao {

    @Autowired
    private FunctionInfoRep funcInfoRep;

    /**
     * User has the specified menu's functionality or not
     *
     * @param userId   user id
     * @param menuType Menu defined
     * @param funcType Functionality defined
     * @return true:  user has the auth, false: no auth
     */
    public boolean hasFunctionality(Integer userId, MenuType menuType, FunctionalityType funcType) {
        List<FunctionInfo> listFunctions = funcInfoRep.getFunctionByUserIdMenuFunctionCode(userId, menuType.getCode().toString(), funcType.getCode().toString());
        return !CommonUtil.isEmpty(listFunctions);
    }
}
