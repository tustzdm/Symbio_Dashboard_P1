package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.business.UserFactory;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.repository.UserRep;
import com.symbio.dashboard.dto.UserLoginDTO;
import com.symbio.dashboard.encrypt.MD5Util;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName - CommonServiceImpl
 * @Author - Admin
 * @Description -
 * @Date - 2019/7/19
 * @Version 1.0
 */

@Service
@SuppressWarnings("unchecked")
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRep userRep;

    @Autowired
    private CommonDao commonDao;

    @Override
    public Result<User> getUserInfo(String locale, Integer id) {
        logger.trace("UserServiceImpl.getUserInfo() Enter");
        logger.trace(String.format("locale = %s, id = %d", locale, id));

        Result<User> retResult = new Result();
        User retUser = userRep.getById(id);
        if(retUser == null) {
            retResult = new Result("10001", "Could not get user info");
        } else {
            retResult = new Result(retUser);
        }

        logger.trace("UserServiceImpl.getUserInfo() Exit");
        return retResult ;
    }

    @Override
    public Result getUserList(String locale, Integer productId) {
        logger.trace("UserServiceImpl.getUserList() Enter");
        logger.trace(String.format("locale = %s, productId = %d", locale, productId));

        logger.trace("UserServiceImpl.getUserList() Exit");
        return new Result(new ArrayList<User>());
    }

    @Override
    public Result getUserListByStatus(String locale, Integer status) {
        logger.trace("UserServiceImpl.getUserListByStatus() Enter");
        logger.trace(String.format("locale = %s, status = %d", locale, status));

        Result retResult = null;
        List<User> list = userRep.getUserListByStatus(status);
        if (list == null || list.size() == 0) {
            retResult = new Result("10001", "Could not get user data");
        } else {
            retResult = new Result(list);
        }

        logger.trace("UserServiceImpl.getUserListByStatus() Exit");
        return retResult;
    }

    @Override
    public Result<User> getUserInfo(Integer id) {
        return getUserInfo(Locales.EN_US.toString(), id);
    }
    @Override
    public Result getUserList(Integer productId){
        return getUserList(Locales.EN_US.toString(), productId);
    }
    @Override
    public Result getUserListByStatus(Integer status){
        return getUserListByStatus(Locales.EN_US.toString(), status);
    }

    private Result<User> validationUser(String locale, User userData) {
        Result retResult = new Result();
        User user = userData;

        if (CommonUtil.isEmpty(user.getName())) {
            return commonDao.getResult("100201", "Name");
        }

        if (CommonUtil.isEmpty(user.getId())) {
            user = UserFactory.createNewUser(locale, user);

            if (CommonUtil.isEmpty(user.getPasswd())) {
                return commonDao.getResult("100201", "Password");
            }

            user.setPasswd(MD5Util.encrypt(user.getPasswd()));
        }

        retResult.setCd(user);
        return retResult;
    }

    @Override
    public Result saveUserInfo(String locale, User userInfo) {
        String funcName = "UserServiceImpl.saveUserInfo()";
        User newUserInfo = userInfo;

        if (!BusinessUtil.isIdEmpty(userInfo.getId())) {
            User userData = userRep.getById(userInfo.getId());
            if (CommonUtil.isEmpty(userData)) {
                return commonDao.getTableNoDataArgsResult("User", userInfo.getId());
            }

            newUserInfo.setCreateTime(userData.getCreateTime());
        } else {
            Result<User> retChkUser = validationUser(locale, userInfo);
            if (retChkUser.hasError()) {
                return new Result(retChkUser);
            }
            newUserInfo = retChkUser.getCd();
        }

        try {
            userRep.saveAndFlush(newUserInfo);
        } catch (DataIntegrityViolationException ec) {
            if (ec.getMessage().contains("unique_user_nickName")) {
                logger.error(commonDao.getMessage(locale, ErrorConst.EXCEPTION_DUPLICATE_KEY, "Name", newUserInfo.getName(), "User"), ec);
                return commonDao.getDuplicateMessage("Name", newUserInfo.getName());
            } else {
                ec.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return ErrorConst.getExceptionResult(funcName, e);
        }

        return new Result();
    }

    private Result<User> checkLoginUserInfo(String locale, String name, String pass) {

        if (CommonUtil.isEmpty(name)) {
            return commonDao.getResultArgs(locale, "100201", "Name");
        }

        if (CommonUtil.isEmpty(pass)) {
            return commonDao.getResultArgs(locale, "100201", "Password");
        }

        return new Result();
    }

    @Override
    public Result login(String locale, String name, String passWd) {

        // Step1 - validation
        Result retCheck = checkLoginUserInfo(locale, name, passWd);
        if (retCheck.hasError()) {
            return new Result(retCheck);
        }

        // Step2 - Check user
        User userData = userRep.getLoginUserInfo(name);
        if (userData == null) {
            return commonDao.getResult("100204");
        } else {
            String strPass = MD5Util.encrypt(passWd);
            if (!strPass.equals(userData.getPasswd())) {
                return commonDao.getResult("100204");
            }
        }

        // Step3 - get Role & Menu
        Integer userId = userData.getId();
        String token = "testToken";
        UserLoginDTO dtoUserLogin = new UserLoginDTO(locale, token);

        return new Result(dtoUserLogin);
    }

    public Result loginLDAP(String locale, String name, String passWd) {
        // Step1 - validation
        Result retCheck = checkLoginUserInfo(locale, name, passWd);
        if (retCheck.hasError()) {
            return new Result(retCheck);
        }

        // Step2 - Check LDAP user
        User userData = null;

        // Step3 - get Role & Menu
        Integer userId = userData.getId();

        return new Result();
    }
}
