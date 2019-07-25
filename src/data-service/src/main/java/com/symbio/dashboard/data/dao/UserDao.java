package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.enums.ColumnType;
import com.symbio.dashboard.model.SysListSetting;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.EntityUtils;
import com.symbio.dashboard.util.StringUtil;
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
//      List<Object[]> listData = this.entityManager.createNativeQuery(strSql).getResultList();
      retUserData = this.entityManager.createNativeQuery(strSql, User.class).getResultList();
      System.out.println(">>>>>> retUserData.size == " + retUserData.size());

//      retUserData = EntityUtils.castEntity(listData, User.class, new User());
    } catch (Exception e) {
      e.printStackTrace();
//      retResult = new Result("40001", "Exception!!!" + e.getMessage());
      retUserData = new ArrayList<User>();
    }

    logger.trace("UserDao - getUserByIds() Exit");
    return retUserData;
  }

}
