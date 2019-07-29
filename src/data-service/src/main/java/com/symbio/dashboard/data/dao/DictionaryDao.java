package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.repository.DictionaryRep;
import com.symbio.dashboard.data.repository.UiInfoRep;
import com.symbio.dashboard.enums.ColumnType;
import com.symbio.dashboard.enums.HtmlType;
import com.symbio.dashboard.model.Dictionary;
import com.symbio.dashboard.model.SysListSetting;
import com.symbio.dashboard.model.UiInfo;
import com.symbio.dashboard.model.User;
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
public class DictionaryDao {

  private static Logger logger = LoggerFactory.getLogger(DictionaryDao.class);

  @Autowired
  private EntityManager entityManager;


  @Autowired
  private DictionaryRep dictionaryRep;

  public List<Dictionary> getDicDataByType(String type){
    return dictionaryRep.getDictDataByType(type);
  }

  public List<Map<String, Object>> getDicMapDataByType(String type){
    List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();

    List<Dictionary> listDic = dictionaryRep.getDictDataByType(type);
    for(Dictionary dic : listDic) {
      retList.add(getDictionaryUIInfo(dic));
    }
    return retList;
  }

  /**
   * UI 使用的 Dictionary 信息
   * @param dic
   * @return
   */
  public static Map<String, Object> getDictionaryUIInfo(Dictionary dic) {
    Map<String, Object> dicInfo = new HashMap<String, Object>();

    dicInfo.put("id", dic.getId());
    dicInfo.put("code", dic.getCode());
    dicInfo.put("value", dic.getValue());

    return dicInfo;
  }

}
