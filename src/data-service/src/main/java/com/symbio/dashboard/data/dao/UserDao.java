package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.repository.UserRep;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Repository
@Slf4j
public class UserDao {

  private static Logger logger = LoggerFactory.getLogger(UserDao.class);

  @Autowired
  private EntityManager entityManager;
  @Autowired
  private UserRep userRep;

  /**
   * Get Mysql Table's field info except non user defined
   * @param ids
   * @return
   */
  public List<User> getUserByIds(String ids) {
    logger.trace("UserDao - getUserByIds() Enter. ids = " + ids);

    List<User> retUserData = new ArrayList<User>();
    try {
      String strSql = String.format("SELECT * FROM user WHERE id in (%s)", ids);
      retUserData = this.entityManager.createNativeQuery(strSql, User.class).getResultList();

    } catch (Exception e) {
      e.printStackTrace();
      retUserData = new ArrayList<User>();
    }

    logger.trace("UserDao - getUserByIds() Exit");
    return retUserData;
  }

  public List<User> getAllUser() {
    return userRep.getAllUser();
  }

  /**
   * 得到用于List显示用的SelectList信息
   * @return
   */
  public List<Map<String, Object>> getAllUserUiListInfo() {
    List<Map<String, Object>> retUsers = new ArrayList<Map<String, Object>>();

    List<User> listUser = userRep.getAllUser();
    Map<String, Object> mapUser ;

    for(User user: listUser) {
      retUsers.add(BusinessUtil.getUserUIListInfo(user));
    }

    return retUsers;
  }

  /**
   *
   * @param id
   * @return
   */
  public User getUserById(Integer id){
    return userRep.getOne(id);
  }

  /**
   * Get User by Id
   *
   * @param id
   * @return
   */

  public Result<User> getById(Integer id) {
    Result<User> retUserInfo = new Result<User>();
    String funcName = "UserDao.getUserById()";

    User tr = getUserById(id);
    if (CommonUtil.isEmpty(tr)) {
      log.error(ErrorConst.getWarningLogMsg(funcName, "Could not find User record by Id: " + id));
      return new Result<>("000016", String.format("Could not find relative data in table [%s]. id = [%d]", "User", id));
    } else {
      retUserInfo.setCd(tr);
    }

    return retUserInfo;
  }

}
