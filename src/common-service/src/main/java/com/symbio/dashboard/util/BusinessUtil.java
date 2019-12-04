package com.symbio.dashboard.util;

import com.symbio.dashboard.business.CommonListDTOFactory;
import com.symbio.dashboard.business.StatListFactory;
import com.symbio.dashboard.constant.ProjectConfigConst;
import com.symbio.dashboard.entity.Progress;
import com.symbio.dashboard.enums.*;
import com.symbio.dashboard.model.*;

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
    return (role != null && role > 1) ? true : false;
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
//        String strColLabel = item.getLabel();
//        try {
//          JSONObject jsonLabel = new JSONObject(strColLabel);
//          if (locale == Locales.ZH_CN.toString()) {
//            mapColInfo.put(ListColumns.LABEL.getKey(), jsonLabel.get(strKeyCN));
//          } else {
//            mapColInfo.put(ListColumns.LABEL.getKey(), jsonLabel.get(strKeyEN));
//          }
//        } catch (Exception jsonE) {
//          jsonE.printStackTrace();
//        }
        mapColInfo.put(ListColumns.LABEL.getKey(), CommonUtil.getJSONLocaleValue(item.getLabel(), locale));

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

  @Deprecated
  public static List<Map<String, Object>> randomProgress(List<Map<String, Object>> data) {
    List<Map<String, Object>> retList = data;

    Progress progress;
    Random random = new Random();
    boolean bProcessRandom = false;
    for (Map item : data) {
      bProcessRandom = true;
      if (item.containsKey("status")) {
        String strStatus = item.get("status").toString();
        if (Integer.parseInt(strStatus) == 0) {
          bProcessRandom = false;
        }
      }

      if (bProcessRandom) {
        int total = random.nextInt(500);
        int done = random.nextInt(500);
        if (done > total) done = total;
        progress = new Progress(done, total);

      } else {
        progress = new Progress(0, 0);
      }
      item.put("progress", progress);
    }
    return retList;

  }

  public static List<Map<String, Object>> mergeStatData(List<Map<String, Object>> data, SystemListSetting systemListSetting, List<String> listFields, List<StatList> listStatData) {
    List<Map<String, Object>> retList = data;

    Progress progress;
    boolean bProcessEmpty = false;
    StatList statData = null;


    for (Map item : data) {
      bProcessEmpty = false;
      if (item.containsKey("status")) {
        String strStatus = item.get("status").toString();
        if (Integer.parseInt(strStatus) == 0) {
          bProcessEmpty = true;
        }
      }

      for (String field : listFields) {
        if (!item.containsKey(field)) {
          item.put(field, "");
        }
      }

      // Stat field: progress
      if (item.containsKey("progress")) {
        if (bProcessEmpty || CommonUtil.isEmpty(listStatData)) {
          progress = new Progress(0, 0);
        } else {
          statData = findExactData(systemListSetting, "progress", Integer.parseInt(item.get("id").toString()), listStatData);
          progress = StatListFactory.getProgress(statData);
        }
        item.put("progress", progress);
      }
    }
    return retList;

  }

  private static StatList findExactData(SystemListSetting systemListSetting, String fieldName, Integer id, List<StatList> listStatData) {
    StatList retStatData = null;

    if (CommonUtil.isEmpty(id)) return null;

    for (StatList item : listStatData) {

      switch (systemListSetting) {
        default:
          break;
        case Product:
        case Release:
        case TestSet:
          if ((id.intValue() == item.getFkId().intValue())
                  && (item.getTypeCode() == systemListSetting.ordinal())
                  && item.getField().equalsIgnoreCase(fieldName.toLowerCase())) {
            return item;
          }
          break;
      }
    }

    return retStatData;
  }

  public static List<String> getFieldListInfo(List<SysListSetting> data) {
    List<String> retData = new ArrayList<>();

    for (SysListSetting item : data) {
      retData.add(item.getField());
    }

    return retData;
  }

  /**
   * To use reflect
   *
   * @param data
   * @return
   */
  public static List<String> getCamelFieldListByUIInfo(List<UiInfo> data) {
    List<String> listField = new ArrayList<>();
    for (UiInfo item : data) {
      if (!CommonUtil.isEmpty(item.getDbField())) {
        listField.add(CommonUtil.getCamelField(item.getDbField()));
      }
    }

    return listField;
  }

  public static Map<String, Object> filterURLMapData(Map<String, Object> data, String preDomain) {
    Map<String, Object> retMap = data;
    try {
      Object objValue = null;
      for (String key : data.keySet()) {
        if (key.toLowerCase().contains("url")) {
          objValue = data.get(key);
          if (objValue != null && objValue.toString().startsWith("mock/image/")) {
            data.put(key, preDomain + objValue.toString());
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return retMap;
  }

  public static List<Map<String, Object>> filterURLListData(List<Map<String, Object>> data, String preDomain) {
    List<Map<String, Object>> retData = data;

    for (Map<String, Object> item : data) {
      filterURLMapData(item, preDomain);
    }

    return retData;
  }

  /**
   * 得到User参照的field
   *
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
        }
        dbFields.add(CommonUtil.getCamelField(item.getField()));

      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return dbFields;
  }

}
