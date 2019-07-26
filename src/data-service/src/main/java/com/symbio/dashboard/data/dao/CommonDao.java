package com.symbio.dashboard.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.symbio.dashboard.enums.ColumnType;
import com.symbio.dashboard.model.SysListSetting;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.StringUtil;
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
      String strSql = String.format("DESC `%s`", tableName);
      List<Object[]> listData = this.entityManager.createNativeQuery(strSql).getResultList();

      String strField = null;
      StringBuffer sb = new StringBuffer();
      List<String> listFields = new ArrayList<String>();
      for (Object[] item : listData) {
        strField = item[0].toString();
        if (BusinessUtil.isUserDefinedField(strField)) {
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

  /**
   * 得到User参照的field
   * @param listSetting
   * @return
   */
  public static List<String> getQueryUserRefFields(List<SysListSetting> listSetting) {
    List<String> dbFields = new ArrayList<>();

    try {
      String strFieldType = null;
      for (SysListSetting item : listSetting) {
        strFieldType = item.getType().trim();
        if (ColumnType.User.getCode().equals(strFieldType)) {
          dbFields.add(CommonUtil.getCamelField(item.getField()));
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
    }

    return dbFields;
  }

  /**
   * 得到dbFiled信息
   * @param listSetting
   * @return
   */
  public static List<String> getQueryFields(List<SysListSetting> listSetting) {
    List<String> dbFields = new ArrayList<>();

    boolean bFindId = false;

    for(SysListSetting item : listSetting){
      if(!StringUtil.isEmpty(item.getField())) {
        dbFields.add(item.getField());
        if(!bFindId && item.getField().contains("id")) {
          bFindId = true;
        }
      }
    }

    if(!bFindId) dbFields.add(0, "id");

    return dbFields;
  }
}
