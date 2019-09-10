package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.repository.UserRep;
import com.symbio.dashboard.encrypt.MD5Util;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    private Result<User> validationUser(User user) {
        Result retResult = new Result();

        if (CommonUtil.isEmpty(user.getName())) {
            return commonDao.getResult("100201", "Name");
        }

        if (CommonUtil.isEmpty(user.getId())) {
            if (CommonUtil.isEmpty(user.getPasswd())) {
                return commonDao.getResult("100201", "Password");
            }

            user.setPasswd(MD5Util.encrypt(user.getPasswd()));
            if (!CommonUtil.isEmpty(user.getFirstName()) && !CommonUtil.isEmpty(user.getLastName())) {
                user.setFullName(String.format("%s %s", user.getFirstName(), user.getLastName()));
            }

            user.setCreateTime(new Date());
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
            Result<User> retChkUser = validationUser(userInfo);
            if (retChkUser.hasError()) {
                return new Result(retChkUser);
            }
            newUserInfo = retChkUser.getCd();
        }

        try {
            userRep.saveAndFlush(newUserInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorConst.getExceptionResult(funcName, e);
        }

        return null;
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
        User userData = userRep.getUserByName(name, 1);
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

        return new Result();
    }
}
