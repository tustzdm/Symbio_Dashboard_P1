package com.symbio.dashboard.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository
public class CommonDao {

  private static Logger logger = LoggerFactory.getLogger(CommonDao.class);

  @Autowired
  private EntityManager entityManager;

  /**
   * Get Mysql Table's field info except non user defined
   * @param tableName
   * @return
   */
  public Result getDescField(String tableName) {

    logger.trace("CommonDao - getDescField() Enter. tableName = " + tableName);
    Result retResult = null;
    try {
      String strSql = "desc " + tableName;
      List<Object[]> listData = this.entityManager.createNativeQuery(strSql).getResultList();

      String strField = null;
      StringBuffer sb = new StringBuffer();
      List<String> listFields = new ArrayList<String>();
      boolean bIgnore = false;
      for (Object[] item : listData) {
        strField = item[0].toString();
        bIgnore = false;
        if (strField.startsWith("create_") || strField.startsWith("update_")) {
          bIgnore = true;
        } else if (strField.equals("id") || strField.equals("description") || strField.equals("display") || strField.equals("validation")) {
          bIgnore = true;
        }

        if (!bIgnore) {
          listFields.add(strField);
        }
      }
      retResult = new Result(listFields);
    } catch (Exception e) {
      e.printStackTrace();
      retResult = new Result("40001", "Exception!!!" + e.getMessage());
    }

    logger.trace("CommonDao - getDescField() Exit");
    return retResult;
  }
}
