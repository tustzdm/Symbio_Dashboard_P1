package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.dao.RoleDao;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.FunctionalityType;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.enums.MenuType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unchecked")
@Slf4j
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public Result<Boolean> hasPermission(Integer userId, String menuName, String functionName) {

        Result<Boolean> retResult = new Result<>();

        String funcName = "AuthorityServiceImpl.hasPermission(userId, menuName, functionName)";
        MenuType menuType = null;
        FunctionalityType funcType = null;

        try {
            menuType = EnumDef.getEnumTypeByValue(MenuType.class, menuName);
            if (menuType == null) {
                log.warn(commonDao.getMessage(Locales.EN_US.toString(), ErrorConst.EXCEPTION_ENUM_CONVERT_ERROR_NAME, "MenuType", menuName));
                return commonDao.getResult(ErrorConst.EXCEPTION_ENUM_CONVERT_ERROR_NAME, "MenuType", menuName);
            }

            funcType = EnumDef.getEnumTypeByValue(FunctionalityType.class, functionName);
            if (menuType == null) {
                log.warn(commonDao.getMessage(Locales.EN_US.toString(), ErrorConst.EXCEPTION_ENUM_CONVERT_ERROR_NAME, "FunctionalityType", functionName));
                return commonDao.getResult(ErrorConst.EXCEPTION_ENUM_CONVERT_ERROR_NAME, "FunctionalityType", functionName);
            }

            retResult = hasPermission(userId, menuType, funcType);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return ErrorConst.getExceptionResult(funcName, e);
        }

        return retResult;
    }

    @Override
    public Result<Boolean> hasPermission(Integer userId, Integer menuCode, Integer funcCode) {
        Result<Boolean> retResult = new Result<>();

        String funcName = "AuthorityServiceImpl.hasPermission(userId, menuCode, funcCode)";
        MenuType menuType = null;
        FunctionalityType funcType = null;

        try {
            menuType = EnumDef.getEnumTypeByCode(MenuType.class, menuCode);
            if (menuType == null) {
                log.warn(commonDao.getMessage(Locales.EN_US.toString(), ErrorConst.EXCEPTION_ENUM_CONVERT_ERROR_CODE, "MenuType", menuCode));
                return commonDao.getResult(ErrorConst.EXCEPTION_ENUM_CONVERT_ERROR_CODE, "MenuType", menuCode);
            }

            funcType = EnumDef.getEnumTypeByCode(FunctionalityType.class, funcCode);
            if (funcType == null) {
                log.warn(commonDao.getMessage(Locales.EN_US.toString(), ErrorConst.EXCEPTION_ENUM_CONVERT_ERROR_CODE, "FunctionalityType", funcCode));
                return commonDao.getResult(ErrorConst.EXCEPTION_ENUM_CONVERT_ERROR_CODE, "FunctionalityType", funcCode);
            }

            retResult = hasPermission(userId, menuType, funcType);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return ErrorConst.getExceptionResult(funcName, e);
        }
        return retResult;
    }

    @Override
    public Result<Boolean> hasPermission(Integer userId, MenuType menuType, FunctionalityType funcType) {
        String funcName = "AuthorityServiceImpl.hasPermission()";
        Result<Boolean> retResult = new Result<>();

        try {
            boolean bPermission = roleDao.hasFunctionality(userId, menuType, funcType);
            retResult.setCd(bPermission);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return ErrorConst.getExceptionResult(funcName, e);
        }

        return retResult;
    }
}
