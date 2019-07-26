package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@Repository
public class UserDao {

  private static Logger logger = LoggerFactory.getLogger(UserDao.class);

  @Autowired
  private EntityManager entityManager;

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

}
