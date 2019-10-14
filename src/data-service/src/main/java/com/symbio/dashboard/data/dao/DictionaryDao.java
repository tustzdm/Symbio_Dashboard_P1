package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.data.repository.DictionaryRep;
import com.symbio.dashboard.model.Dictionary;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Slf4j
@Repository
public class DictionaryDao {

  //private static Logger logger = LoggerFactory.getLogger(DictionaryDao.class);

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
    for (Dictionary dic : listDic) {
      retList.add(getDictionaryUIInfo(dic));
    }
    return retList;
  }

  public List<Map<String, Object>> getDicMapDataByTypeLocale(String type, String locale) {
    List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();

    List<Dictionary> listDic = dictionaryRep.getDictDataByType(type);
    for(Dictionary dic : listDic) {
      retList.add(getDictionaryUIInfoByLocale(dic, locale));
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

  /**
   * UI 使用的 Dictionary(code,value) 信息
   *
   * @param dic
   * @return
   */
  public static Map<String, Object> getDictionaryUIInfoByLocale(Dictionary dic, String locale) {
    Map<String, Object> dicInfo = new HashMap<String, Object>();

    dicInfo.put("code", dic.getCode());
    dicInfo.put("value", CommonUtil.getJSONLocaleValue(dic.getValue(), locale));

    return dicInfo;
  }

}
