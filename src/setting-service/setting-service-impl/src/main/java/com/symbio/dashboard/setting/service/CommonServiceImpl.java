package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.repository.DictionaryRep;
import com.symbio.dashboard.enums.UIInfoPage;
import com.symbio.dashboard.model.Dictionary;
import com.symbio.dashboard.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName - CommonServiceImpl
 * @Author - Admin
 * @Description -
 * @Date - 2019/7/16
 * @Version 1.0
 */

@Service
@SuppressWarnings("unchecked")
public class CommonServiceImpl implements CommonService {

    private static Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Autowired
    private DictionaryRep dictionaryRep;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private ResultMessageServiceImpl messageService;

    @Override
    public Result getDictionaryByType(String type){

        List<Dictionary> list = dictionaryRep.getDictDataByType(type);
        if(list == null || list.size() == 0) {
            return messageService.getResultEx("000124", type);
        } else {
            return new Result(list);
        }
    }

    @Override
    public Result getDictionaryInfo(String type) {
        return getDictionaryInfoResult(type);
    }

    private Result getDictionaryInfoResult(String type) {
        List<Dictionary> dictionaryList;
        List<Map<String, Object>> pageNamesList = new ArrayList<>();

        try {

            dictionaryList = dictionaryRep.getDictDataByType(type);

            if (dictionaryList == null || dictionaryList.isEmpty()) {
                return messageService.getResultEx("000124", type);
            }

            for (Dictionary d : dictionaryList) {
                Map<String, Object> mapDict = new HashMap<String, Object>();

                mapDict.put("id", d.getId());
                mapDict.put("type", d.getType());
                mapDict.put("code", d.getCode());
                mapDict.put("value", d.getValue());

                pageNamesList.add(mapDict);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return messageService.getResultEx("000010");
        }
        return new Result(pageNamesList);
    }

    /**
     * 得到 Table 下的字段列表
     * @param locale
     * @param table
     * @return
     */
    @Override
    public Result getUserDefinedFields(String locale, String table) {
        Result retResult = null;

        try {
            List<Map<String, String>> retList = new ArrayList<>();

            String tableName = UIInfoPage.getTableName(table);
            if(StringUtil.isEmpty(tableName)){
                retResult = messageService.getResult(locale,"001003", table);
                return retResult;
            }

            Result retDBFieldResult = commonDao.getDescField(tableName);
            if (retDBFieldResult.isSuccess()) {
                List<String> listFields = (List<String>) retDBFieldResult.getCd();
                String strField = "";
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < listFields.size(); i++) {
                    strField = listFields.get(i);
                    map = new HashMap<String, String>();
                    map.put("code", strField);
                    map.put("value", strField);
                    retList.add(map);
                }
                retResult = new Result(retList);
            } else {
                retResult = retDBFieldResult;
            }
        } catch (Exception e) {
            logger.error("CommonService - getUserDefinedFields() Exception!!!", e);
            retResult = messageService.getResult(locale,"001001", table);
        }

        return retResult;
    }
}
