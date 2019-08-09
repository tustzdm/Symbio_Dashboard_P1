package com.symbio.dashboard.util;

import com.symbio.dashboard.business.CommonListDTOFactory;
import com.symbio.dashboard.constant.ProjectConfigConst;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.ListColumns;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.model.Release;
import com.symbio.dashboard.model.SysListSetting;
import com.symbio.dashboard.model.User;
import org.json.JSONObject;

import java.util.*;

@SuppressWarnings("unchecked")
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

  public static List AppendOperation(EnumDef.OPERATION_TYPE enumOperation, Integer role, List data) {
    if (CommonUtil.isEmpty(data)) return data;

    List<Map<String, Object>> listData = new ArrayList<>();
    if (ProjectConfigConst.CommonList_Data_Format == 2) {
      listData = data;
      for (Map<String, Object> mapData : listData) {
        mapData.put(ListColumns.OPERATION.getKey(), CommonListDTOFactory.createOperationData(role));
      }
    }

    return listData;
  }

  public static boolean hasOverReadRole(Integer role) {
    return (role > 1) ? true : false;
  }

  public static boolean hasWriteRole(Integer role, Integer bit) {
    return (role >> bit) > 0;
  }

  public static List<Map<String, Object>> getListColumnInfo(String locale, List<SysListSetting> listData) {
    return getListColumnInfo(0, locale, listData);
  }

  /**
   * List 的 Column Info 信息
   *
   * @param listData
   * @return
   */
  public static List<Map<String, Object>> getListColumnInfo(Integer role, String locale, List<SysListSetting> listData) {
    List<Map<String, Object>> listColInfo = new ArrayList<>();

    try {
      Map<String, Object> mapColInfo = new HashMap<String, Object>();
      String strKeyCN = Locales.ZH_CN.getKey();
      String strKeyEN = Locales.EN_US.getKey();

      for (SysListSetting item : listData) {
        mapColInfo = new HashMap<String, Object>();

        mapColInfo.put(ListColumns.KEY.getKey(), item.getKey());
        String strColLabel = item.getLabel();
        try {
          JSONObject jsonLabel = new JSONObject(strColLabel);
          if (locale == Locales.ZH_CN.toString()) {
            mapColInfo.put(ListColumns.LABEL.getKey(), jsonLabel.get(strKeyCN));
          } else {
            mapColInfo.put(ListColumns.LABEL.getKey(), jsonLabel.get(strKeyEN));
          }
        } catch (Exception jsonE) {
          jsonE.printStackTrace();
        }
        mapColInfo.put(ListColumns.TYPE.getKey(), item.getType());
        // Change field to CamelField for UI
        mapColInfo.put(ListColumns.FIELD.getKey(), EntityUtils.getMapFieldKey(WebUtil.getItemValue(item.getField())));

        mapColInfo.put(ListColumns.ALIGN.getKey(), item.getAlign());
        mapColInfo.put(ListColumns.FORMAT.getKey(), WebUtil.getItemValue(item.getFormatter()));
        listColInfo.add(mapColInfo);
      }

      if (hasOverReadRole(role)) {
        listColInfo.add(CommonListDTOFactory.createOperationColumnInfo(locale));
      }

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("BusinessUtil.getListColumnInfo() ERROR!!! " + e.getMessage());
    }

    return listColInfo;
  }

  /**
   * UI 使用的User 信息
   * @param user
   * @return
   */
  public static Map<String, Object> getUserUIListInfo(User user) {
    Map<String, Object> userInfo = new HashMap<String, Object>();

    userInfo.put("id", user.getId());
    userInfo.put("fullName", user.getFullName());

    return userInfo;
  }

  /**
   * UI 使用的User 信息
   * @param user
   * @return
   */
  public static Map<String, Object> getUserUIInfo(User user) {
    Map<String, Object> userInfo = new HashMap<String, Object>();

    userInfo.put("id", user.getId());
    userInfo.put("name", user.getName());
    userInfo.put("email", user.getEmail());
    userInfo.put("fullName", user.getFullName());

    return userInfo;
  }

  /**
   * UI 使用的 Product 信息
   * @param product
   * @return
   */
  public static Map<String, Object> getProductUIListInfo(Product product) {
    Map<String, Object> userInfo = new HashMap<String, Object>();

    userInfo.put("id", product.getId());
    userInfo.put("name", product.getName());

    return userInfo;
  }

  /**
   * UI 使用的 Release 信息
   * @param release
   * @return
   */
  public static Map<String, Object> getReleaseUIListInfo(Release release) {
    Map<String, Object> userInfo = new HashMap<String, Object>();

    userInfo.put("id", release.getId());
    userInfo.put("name", release.getName());

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

  /**
   * 数据库字段是否是用户可指定的字段
   * @param strFieldName
   * @return  true :  user could define it
   *          false:  PK or FK or system field used
   */
  public static boolean isUserDefinedField (String strFieldName) {
    boolean bRet = true;

    if (strFieldName.startsWith("create_") || strFieldName.startsWith("update_")) {
      bRet = false;
    } else if (strFieldName.equals("id") || strFieldName.equals("description") || strFieldName.equals("display") || strFieldName.equals("validation")) {
      bRet = false;
    } else if (strFieldName.endsWith("_id")) {
      bRet = false;
    }

    return bRet;
  }

  public static boolean isIdEmpty(Integer id) {
    if(id == null) {
      return true;
    } else {
      return id < 1;
    }
  }

}
