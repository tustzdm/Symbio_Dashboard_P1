package com.symbio.dashboard.auth.service;

/**
 * @Author: Shawn
 * @version: 1.0
 * @Date: 2019/11/22
 */

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.repository.UserRep;
import com.symbio.dashboard.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthUserServiceImpl implements AuthUserService {

  @Autowired
  private UserRep userRep;

  @Autowired
  private CommonDao commonDao;

  public Result checkUserToken(String token) {
    return getUserInfo(token);
  }

  @Override
  public Result getUserInfo(String token) {
    String funcName = "AuthUserServiceImpl.getUserInfo()";

    Result<User> retResult = new Result<User>();

    if (token == null || token.trim().length() == 0) {
      return commonDao.getResult(ErrorConst.TOKEN_MISSING_INVALID);
    }

    String userName = token;
    User userInfo = userRep.getLoginUserInfo(userName);//userRep.getById(id);
    if (userInfo == null) {
      retResult = commonDao.getResult(ErrorConst.TOKEN_MISSING_INVALID);
    } else {
      retResult = new Result(userInfo);
    }

    log.trace(funcName + " Exit");
    return retResult;
  }
}
