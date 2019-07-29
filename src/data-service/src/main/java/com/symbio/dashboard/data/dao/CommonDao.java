package com.symbio.dashboard.data.dao;

import java.util.*;

import javax.persistence.EntityManager;

import com.symbio.dashboard.enums.ColumnType;
import com.symbio.dashboard.model.SysListSetting;
import com.symbio.dashboard.model.User;
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

  @Autowired
  private UserDao userDao;

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
   * 得到Entity 相关的dbFiled信息
   *
   * @param listSetting
   * @return
   */
  public static List<String> getQueryFields(List<SysListSetting> listSetting) {
    List<String> dbFields = new ArrayList<>();

    boolean bFindId = false;

    for(SysListSetting item : listSetting){
      if(!StringUtil.isEmpty(item.getField())) {
        dbFields.add(item.getField());
        if(!bFindId && item.getField().equals("id")) {
          bFindId = true;
        }
      }
    }

    if(!bFindId) dbFields.add(0, "id");

    return dbFields;
  }

  public static List<String> getQueryFieldsAppend(List<SysListSetting> listSetting, List<String> listAppendFields) {
    List<String> dbFields = getQueryFields(listSetting);
    dbFields.addAll(listAppendFields);
    return dbFields;
  }

  public List<Map<String, Object>> setUserMapInfo(List<Map<String, Object>> data, List<String> listUserFields) {
    List<Map<String, Object>> retList = data;

    try {
      if (CommonUtil.isEmpty(listUserFields) || CommonUtil.isEmpty(data)) {
        return data;
      }

      Set<Integer> userIds = new TreeSet<>();

      // Check map key is match
      Map<String, Object> mapTest = data.get(0);
      CommonUtil.validateMapKey(mapTest, listUserFields);

      for (Map map : data) {
        for (String field : listUserFields) {
          userIds.add((Integer) map.get(field));
        }
      }

      // Find User infos
      List<Integer> listUsers = new ArrayList<>(userIds);
      String inIds = CommonUtil.join(listUsers);
      List<User> listUserInfo = userDao.getUserByIds(inIds);
      System.out.println("ids == " + inIds);
      System.out.println("Find user length == " + listUserInfo.size());

      if (CommonUtil.isEmpty(listUserInfo)) {
        return data;
      }

      Map<Integer, User> mapUsers = new HashMap<>();
      for (User user : listUserInfo) {
        mapUsers.put(user.getId(), user);
      }

      // Get User Map
      retList = BusinessUtil.ReplaceUserShortInfo(data, listUserFields, mapUsers);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    }

    return retList;
  }
}
