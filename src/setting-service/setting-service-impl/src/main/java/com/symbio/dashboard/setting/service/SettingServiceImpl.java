package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.business.AuthorityFactory;
import com.symbio.dashboard.business.SettingFactory;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.dao.AuthorityDao;
import com.symbio.dashboard.data.dao.UserDao;
import com.symbio.dashboard.dto.RoleSettingDTO;
import com.symbio.dashboard.model.*;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.symbio.dashboard.business.SettingFactory.getFunctionListMap;

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
    @Autowired
    private UserDao userDao;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result getRoleInfo(String locale, Integer userId) {
        Result<RoleSettingDTO> retResult = new Result();
        String funcName = "SettingServiceImpl.getRoleInfo";
        log.debug(funcName + " Enter");

        RoleSettingDTO roleSettingDTO = new RoleSettingDTO();
        List<RoleSetting> listRole = authDao.getRoleList();
        List<Map<String, Object>> listRoleMap = SettingFactory.getRoleSettingListMap(listRole);
        roleSettingDTO.setRoleList(listRoleMap);

        List<Menu> listMenu = authDao.getMenuList();
        roleSettingDTO.setMenuList(SettingFactory.getMenuListMap(listMenu));

        List<FunctionInfo> listFunc = authDao.getFunctionList();
        roleSettingDTO.setFunctionList(getFunctionListMap(listFunc));

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

    @Override
    @Transactional
    public Result saveRoleInfo(String locale, Integer userId, Integer roleId, Integer menuId, Integer value) {
        String funcName = "SettingServiceImpl.getRoleInfo";
        log.debug(funcName + " Enter");

        Result retResult = new Result();

        Result checkUserInfo = checkUserId(locale, userId);
        if (checkUserInfo.hasError()) {
            return checkUserInfo;
        }

        Result resultMenu = authDao.checkMenuInfo(locale, menuId);
        if (resultMenu.hasError()) {
            return resultMenu;
        }

        try {
            if (value <= 0) {
                return new Result(0);
            }

            List<FunctionInfo> listUserFunc = authDao.getFunctionListByRoleMenu(roleId, menuId);
            List<FunctionInfo> listSettingFunc = authDao.getFunctionListByAccValue(value);

            // Add role menu function setting
            List<Integer> listAddIds = getNewFunctionIds(listUserFunc, listSettingFunc);
            for (Integer funcId : listAddIds) {
                RoleMenuFunction roleMenuFunc = AuthorityFactory.createUserMenuFunction(roleId, menuId, funcId);
                authDao.saveRoleMenuFunction(roleMenuFunc);
            }

            // Remove user's funcSetting
            List<Integer> listDeleteIds = getDeleteFunctionIds(listUserFunc, listSettingFunc);
            if (!CommonUtil.isEmpty(listDeleteIds)) {
                StringBuffer sb = new StringBuffer();
                sb.append("DELETE FROM RoleMenuFunction rmf")
                        .append(" WHERE rmf.roleId = ").append(roleId)
                        .append(" AND rmf.menuId = ").append(menuId)
                        .append(" AND rmf.functionId in (select fi.id from FunctionInfo fi where fi.id in (:ids))");

                String sql = sb.toString(); //"DELETE FROM role_menu_function rmf where rmf.id in (:ids)";

                Integer nUpdateCount = entityManager.createQuery(sql).setParameter("ids", listDeleteIds).executeUpdate();
                System.out.println("delete count === " + nUpdateCount);
            }
            retResult = new Result(SettingFactory.getFunctionListMap(authDao.getFunctionListByRoleMenu(roleId, menuId)));

        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return ErrorConst.getExceptionResult(funcName, e);
        }
        log.debug(funcName + " Exit");
        return retResult;
    }

    @Override
    public Result<Map<String, Object>> getRoleDetailInfo(String locale, Integer userId, Integer roleId, Integer menuId) {
        Result<Map<String, Object>> retResult = new Result<>();

        Result resultMenu = authDao.checkMenuInfo(locale, menuId);
        if (resultMenu.hasError()) {
            return resultMenu;
        }

        Result<RoleSetting> resultRole = authDao.checkRoleInfo(locale, roleId);
        if (resultRole.hasError()) {
            return new Result(resultRole);
        }

        Integer currentFuncValue = authDao.getFunctionValueByRoleMenu(roleId, menuId);
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("roleId", roleId);
        mapData.put("menuId", menuId);
        mapData.put("value", currentFuncValue);
        retResult.setCd(mapData);

        return retResult;
    }

    private Result checkUserId(String locale, Integer userId) {
        Result<User> resultUser = userDao.getById(userId);
        return resultUser;
    }

    private List<Integer> getNewFunctionIds(List<FunctionInfo> userData, List<FunctionInfo> settingData) {
        List<Integer> listAddIds = new ArrayList<>();

        for (FunctionInfo item : settingData) {
            Integer id = item.getId();
            if (!isIdInList(id, userData)) {
                listAddIds.add(id);
            }
        }

        return listAddIds;
    }

    private List<Integer> getDeleteFunctionIds(List<FunctionInfo> userData, List<FunctionInfo> settingData) {
        List<Integer> listIds = new ArrayList<>();

        for (FunctionInfo item : userData) {
            Integer id = item.getId();
            if (!isIdInList(id, settingData)) {
                listIds.add(id);
            }
        }

        return listIds;
    }

    private boolean isIdInList(Integer id, List<FunctionInfo> data) {
        boolean bRet = false;

        for (FunctionInfo item : data) {
            if (id == item.getId()) {
                bRet = true;
                break;
            }
        }
        return bRet;
    }

}
