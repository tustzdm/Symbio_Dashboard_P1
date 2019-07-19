package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.repository.UserRep;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Result getUserInfo(String locale, Integer id){
        logger.trace("UserServiceImpl.getUserInfo() Enter");
        logger.trace(String.format("locale = %s, id = %d", locale, id));

        Result retResult = null;
        User retUser = userRep.getOne(id);
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
    public Result getUserInfo(Integer id){
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

}
