package com.symbio.dashboard.auth.service;

/**
 * @version: 1.0
 * @Date: 2019
 */

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.repository.UserRep;
import com.symbio.dashboard.data.service.RedisService;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AuthUserServiceImpl implements AuthUserService {

  @Autowired
  private RedisService redisService;
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

    String userId = redisService.getUserIdByToken(token);
    if (CommonUtil.isEmpty(userId)) {
      log.info("Cound not find userId by Token: " + token);
      return commonDao.getResult(ErrorConst.TOKEN_MISSING_INVALID);
    }

    String userName = token;
    User userInfo = userRep.getById(Integer.parseInt(userId)); // userRep.getLoginUserInfo(userName);
    if (userInfo == null) {
      retResult = commonDao.getResult(ErrorConst.TOKEN_MISSING_INVALID);
    } else {
      retResult = new Result(userInfo);
    }

    log.trace(funcName + " Exit");
    return retResult;
  }

  @Override
  public Result<Integer> getUserRole(String page, Integer userId) {
    Result<Integer> retRole = new Result<>();

    Integer role = 7;

    if (userId == 13 || userId == 14) role = 1;
    retRole.setCd(role);

    return retRole;

  }

  private List<Map<String, Object>> getMenuRoleList(Integer userId) {
    return null;
  }
}
