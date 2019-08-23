package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.repository.ProjectConfigRep;
import com.symbio.dashboard.data.repository.ResultMessageRep;
import com.symbio.dashboard.data.repository.UiInfoRep;
import com.symbio.dashboard.entity.Message;
import com.symbio.dashboard.enums.*;
import com.symbio.dashboard.model.*;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;

@SuppressWarnings("unchecked")
@Repository
public class CommonDao {

  private static Logger logger = LoggerFactory.getLogger(CommonDao.class);

  // Message Map 对象
  private static Map<String, Message> mapMessage = null;
  // ProjectConfig 配置项
  private static Map<String, String> mapProjectConfig = null;

  @Autowired
  private EntityManager entityManager;
  @Autowired
  private UserDao userDao;
  @Autowired
  private UiInfoRep uiInfoRep;
  @Autowired
  private DictionaryDao dictionaryDao;
  @Autowired
  private ResultMessageRep messageRep;
  @Autowired
  private ProjectConfigRep projectConfigRep;

  public CommonDao() {
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

  /**
   * 得到Entity 相关的dbFiled信息
   *
   * @param listSetting
   * @return
   */
  public static List<String> getQueryFieldsEx(List<SysListSetting> listSetting) {
    List<String> dbFields = new ArrayList<>();

    boolean bFindId = false;

    for (SysListSetting item : listSetting) {
      if (!StringUtil.isEmpty(item.getField())) {
        String strFieldInfo = item.getField();
        if (strFieldInfo.contains(".")) {
          String[] arrField = strFieldInfo.split("\\.");
          if (arrField.length == 2) {
            strFieldInfo = "tc." + arrField[1];
          }
        } else {
          strFieldInfo = "tr." + strFieldInfo;
        }

        dbFields.add(strFieldInfo);
        if (!bFindId && item.getField().equals("id")) {
          bFindId = true;
        }
      }
    }

    if (!bFindId) dbFields.add(0, "id");

    return dbFields;
  }


  public static List<String> getQueryFieldsAppend(List<SysListSetting> listSetting, List<String> listAppendFields) {
    List<String> dbFields = getQueryFields(listSetting);
    dbFields.addAll(listAppendFields);
    return dbFields;
  }

    /**
     * Get Mysql Table's field info except non user defined
     * 得到Table下用户可定义的字段列表
     *
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
            retResult = new Result("40001",
                    String.format("Invoke getDescField() Exception!!! Table=[%s], Message = [%s]", tableName, e.getMessage()));
        }

        logger.trace("CommonDao - getDescField() Exit");
        return retResult;
    }

    /**
   * 将 User字段的 user信息转成UserMap，以便后面直接替换User字段为User Map信息
   * @param data
   * @param listUserFields
   * @return
   */
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

  /**
   * 得到该Page的所有UI Info信息
   * @param page
   * @return
   */
  public List<UiInfo> getUIInfoByPage(String page) {
    return uiInfoRep.getUiInfoListByPageName(page);
  }

  /**
   * 得到用于List显示用的SelectList信息
   * @param listUiInfo
   * @return
   */
  public List<Map<String, Object>> getUserUIList(List<UiInfo> listUiInfo) {
    List<Map<String, Object>> retListUser = new ArrayList<Map<String, Object>>();

    if(CommonUtil.isEmpty(listUiInfo)) {
      return retListUser;
    }

    boolean bUserRef = false;
    for(UiInfo uiInfo : listUiInfo) {
      if (HtmlType.User.getCode().equals(uiInfo.getType())) {
        bUserRef = true;
        break;
      }
    }

    if(bUserRef) {
      retListUser = userDao.getAllUserUiListInfo();
    }

    return retListUser;
  }

  /**
   * 得到字典的Map数据
   * @return
   */
  public List<Map<String, Object>> getDictDataByType(String type){
    return dictionaryDao.getDicMapDataByType(type);
  }

  /**
   * 得到 UiInfo 的Map数据
   * @return
   */
  public List<Map<String, Object>> getUiInfoList(String locale, String page, List<UiInfo> listUiInfo){
    List<Map<String, Object>> retMap = new ArrayList<>();

    Map<String, Object> mapData = new HashMap<>();
    for(UiInfo item : listUiInfo){
      mapData = new HashMap<>();

      // ToDo: [Enhancement] (Shawn) Use some function to set value by automatically
      mapData.put(UIInfoKey.Key.getKey(), item.getKey());
      mapData.put(UIInfoKey.DBField.getKey(), item.getDbField());
      mapData.put(UIInfoKey.Type.getKey(), item.getType());
      mapData.put(UIInfoKey.Data.getKey(), item.getData());
      mapData.put(UIInfoKey.DisplayStatus.getKey(), item.getDispStatus());
      mapData.put(UIInfoKey.Required.getKey(), item.getIsRequired());
      mapData.put(UIInfoKey.Disable.getKey(), item.getIsDisable());
      mapData.put(UIInfoKey.Label.getKey(), Locales.EN_US.toString().equals(locale) ? item.getEnUs() : item.getZhCn());
      mapData.put(UIInfoKey.PlaceHolder.getKey(), item.getPlaceHolder());
      mapData.put(UIInfoKey.DefaultValue.getKey(), item.getDefaultValue());
      mapData.put(UIInfoKey.ConstCondition.getKey(), item.getConstCondition());
      mapData.put(UIInfoKey.Order.getKey(), item.getIdx());

      retMap.add(mapData);
    }

    // return retMap;
    return buildUiInfoListData(locale, page, retMap);
  }

  /**
   * Put List data in data field
   * @param locale
   * @param listUiInfo
   * @return
   */
  public List<Map<String, Object>> buildUiInfoListData(String locale, String page, List<Map<String, Object>> listUiInfo) {
    List<Map<String, Object>> retList = listUiInfo;

    try {
      String strDBField, strType, strDictionayTpe, strData;
      for(Map<String, Object> mapItem : retList) {
        strDBField = (String)mapItem.get(UIInfoKey.DBField.getKey());
        strType = (String)mapItem.get(UIInfoKey.Type.getKey());
        strData = (String)mapItem.get(UIInfoKey.Data.getKey());

        // Fetch dictionay data for "list"
        if(HtmlType.SelectList.getCode().equals(strType)
                && (StringUtil.isEmpty(strData) || "status".equalsIgnoreCase(strDBField))) {
          strDictionayTpe = page + strDBField;
          try {
            // validation
            DictionaryType dictType = DictionaryType.getDicType(strDictionayTpe);
            List<Map<String, Object>> listDicMap = getDictDataByType(dictType.getType());
            mapItem.put(UIInfoKey.Data.getKey(), listDicMap);
          } catch (Exception e) {
            e.printStackTrace();
            logger.error("buildUiInfoListData() ERROR!!!  Message = " + e.getMessage());
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return retList;
  }

  public Result getResult(String code) {
    return getLocalResult(Locales.EN_US.toString(), code);
  }

  public Result getResult(String code, Object... args) {
    return getLocalResult(Locales.EN_US.toString(), code, args);
  }

  public Result getResultArgs(String locale, String code, Object... args) {
    return getLocalResult(locale, code, args);
  }

  public String getMessage(String locale, String code, Object... args) {
    String retMsg = null;
    if (mapMessage == null) {
      mapMessage = getLocalMessageMap();
    }

    if (mapMessage.isEmpty()) {
      return null;
    } else {
      Message msgInfo = mapMessage.get(code);
      if(msgInfo == null) {
        logger.warn("Could not find message info. code === " + code);
        System.out.println("Could not find message info. code === " + code);
        return null;
      }

      retMsg = getLocaleMessage(locale, msgInfo, args);
    }
    return retMsg;
  }

  private Result getLocalResult(String locale, String code, Object... args){
    String strMsg = getMessage(locale, code, args);
    if (strMsg == null || strMsg.isEmpty()) {
      return null;
    } else {
      return new Result(code, strMsg);
    }
  }

  private Map<String, Message> getLocalMessageMap() {
    List<ResultMessage> listData = messageRep.getAll();
    if(listData.isEmpty()) {
      logger.warn("!!!WARNING!!! Result Message is empty. Please contact administrator for initialization.");
      return null;
    }

    mapMessage = new HashMap<String, Message>();
    for(ResultMessage item: listData) {
      mapMessage.put(item.getCode(), getLocalMessage(item));
    }

    return mapMessage;
  }

  private String getLocaleMessage(String locale, Message msgInfo, Object... args) {
    String retMsg = null;

    try {
      if (locale == Locales.ZH_CN.toString()) {
        retMsg = msgInfo.getZhCn();
      } else {
        retMsg = msgInfo.getEnUs();
      }

      if (!StringUtil.isEmpty(retMsg) && (retMsg.contains("%s") || retMsg.contains("%d"))
              && args.length > 0) {
        retMsg = String.format(retMsg, args);
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("CommonDao.getMessage() ERROR!!!", e);
    }
    return retMsg;
  }

  private Message getLocalMessage(ResultMessage data) {
    return new Message(data.getCode(), data.getEnUs(), data.getZhCn(), data.getFormatter());
  }

  /**
   * Get config item info
   *
   * @param key
   * @return
   */
  public String getConfigValueByKey(String key) {
    String strValue = "";

    try {
      if (mapProjectConfig == null || mapProjectConfig.isEmpty()) {
        mapProjectConfig = new HashMap<>();

        List<ProjectConfig> listData = projectConfigRep.getAll();
        for (ProjectConfig item : listData) {
          mapProjectConfig.put(item.getConfigName(), item.getConfigValue());
        }
      }
      strValue = mapProjectConfig.get(key);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("CommonDao.getConfigValueByItem() ERROR!!! " + e.getMessage());
    }
    return strValue;
  }

  public boolean isProjectConfigSettingTrue(String key) {
    return CommonUtil.isTRUEStr(getConfigValueByKey(key));
  }

  // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // Fix Error code function Here!
  // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public Result getEmptyArgsLocale(String locale, Object... args) {
    return getResultArgs(locale, ErrorConst.ERROR_PARAMETER_EMPTY, args);
  }

  public Result getEmptyArgsResult(Object... args) {
    return getEmptyArgsLocale(Locales.EN_US.toString(), args);
  }

  // Without locale
  public Result getExceptionArgsResult(Object... args) {
    return getLocalResult(Locales.EN_US.toString(), ErrorConst.EXCEPTION, args);
  }

  /**
   * Args: [Table Name], [id]
   *
   * @param args
   * @return
   */
  public Result getTableNoDataArgsLocale(String locale, Object... args) {
    return getResultArgs(locale, ErrorConst.DB_NODATA_ARGS_TABLE_ID, args);
  }

  public Result getTableNoDataArgsResult(Object... args) {
    return getTableNoDataArgsLocale(Locales.EN_US.toString(), args);
  }


}
