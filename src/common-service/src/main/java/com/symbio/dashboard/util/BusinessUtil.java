package com.symbio.dashboard.util;

import com.symbio.dashboard.model.User;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BusinessUtil {

  /**
   * 替换整个User信息
   * @param data
   * @param userFields
   * @param mapUsers
   * @return
   */
  public static List<Map<String, Object>> ReplaceUserInfo(List<Map<String, Object>> data, List<String> userFields, Map<Integer, User> mapUsers) {
    if(CommonUtil.isEmpty(userFields) || CommonUtil.isEmpty(mapUsers) || CommonUtil.isEmpty(data)) {
      return data;
    }

    List<Map<String, Object>> retData = data;

    try {
      Object obj = null;
      Integer userId = null;
      User usrData = null;
      for(Map<String, Object> mapDataItem : retData) {
        for(String field: userFields) {
          userId = (Integer)mapDataItem.get(field);
          if (userId != null) {
            usrData = mapUsers.get(userId);
            if (usrData != null) {
              mapDataItem.put(field, usrData);
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("BusinessUtil.ReplaceUserInfo() ERROR!!!" + e.getMessage());
    }

    return retData;
  }

  /**
   * UI 使用的User 信息
   * @param user
   * @return
   */
  private static Map<String, Object> getUserUIInfo(User user) {
    Map<String, Object> userInfo = new HashMap<String, Object>();

    userInfo.put("id", user.getId());
    userInfo.put("name", user.getName());
    userInfo.put("email", user.getEmail());
    userInfo.put("fullName", user.getFullName());

    return userInfo;
  }

  /**
   * 得到 UI 使用的User Map数据
   * @param data
   * @return
   */
  private static Map<Integer, Object> getShortUserInfo(Map<Integer, User> data) {
    Map<Integer, Object> retMap = new HashMap<Integer, Object>();

    Iterator iterUser = data.keySet().iterator();

    Integer userId = null;
    while (iterUser.hasNext()) {
      userId = (Integer) iterUser.next();
      User user = data.get(userId);
      retMap.put(userId, getUserUIInfo(user));
    }

    return retMap;
  }

  /**
   * 替换UI使用的User信息
   * @param data
   * @param userFields
   * @param mapUsers
   * @return
   */
  public static List<Map<String, Object>> ReplaceUserShortInfo(List<Map<String, Object>> data, List<String> userFields, Map<Integer, User> mapUsers) {
    if(CommonUtil.isEmpty(userFields) || CommonUtil.isEmpty(mapUsers) || CommonUtil.isEmpty(data)) {
      return data;
    }

    List<Map<String, Object>> retData = data;

    try {
      Object obj = null;
      Integer userId = null;
      Object usrData = null;

      Map<Integer, Object> newMapUsers = getShortUserInfo(mapUsers);

      for(Map<String, Object> mapDataItem : retData) {
        for(String field: userFields) {
          userId = (Integer)mapDataItem.get(field);
          if (userId != null) {
            usrData = newMapUsers.get(userId);
            if (usrData != null) {
              mapDataItem.put(field, usrData);
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("BusinessUtil.ReplaceUserInfo() ERROR!!! " + e.getMessage());
    }

    return retData;
  }
}
