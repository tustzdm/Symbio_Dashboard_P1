package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.business.SettingFactory;
import com.symbio.dashboard.data.dao.AuthorityDao;
import com.symbio.dashboard.dto.RoleSettingDTO;
import com.symbio.dashboard.model.FunctionInfo;
import com.symbio.dashboard.model.Menu;
import com.symbio.dashboard.model.RoleSetting;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//import com.symbio.dashboard.ui.service.SettingService;

/**
 * @ClassName - SettingServiceImpl
 * @Description -
 * @Date - 2019/7/16
 * @Version 1.0
 */

@Service
@Slf4j
public class SettingServiceImpl implements SettingService {

    @Autowired
    private AuthorityDao authDao;

    @Override
    public Result getRoleInfo(String locale, Integer userId) {
        Result<RoleSettingDTO> retResult = new Result();
        String funcName = "SettingServiceImpl.getRoleInfo";
        log.debug(funcName + " Enter");

        RoleSettingDTO roleSettingDTO = new RoleSettingDTO();
        List<RoleSetting> listRole = authDao.getRoleList();
        roleSettingDTO.setRoleList(SettingFactory.getRoleSettingListMap(listRole));

        List<Menu> listMenu = authDao.getMenuList();
        roleSettingDTO.setMenuList(SettingFactory.getMenuListMap(listMenu));

        List<FunctionInfo> listFunc = authDao.getFunctionList();
        roleSettingDTO.setFunctionList(SettingFactory.getFunctionListMap(listFunc));

        Integer currentFuncValue = 0;
        if (!CommonUtil.isEmpty(listRole) && !CommonUtil.isEmpty(listMenu)) {
            Integer roleId = listRole.get(0).getId();
            Integer menuId = listMenu.get(0).getId();
            currentFuncValue = authDao.getFunctionValueByRoleMenu(roleId, menuId);
        }
        roleSettingDTO.setTotalFunctionValue(currentFuncValue);

        retResult.setCd(roleSettingDTO);
        log.debug(funcName + " Exit");
        return retResult;
    }
}
