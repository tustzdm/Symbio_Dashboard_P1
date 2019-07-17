package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.repository.DictionaryRep;
import com.symbio.dashboard.dictionary.dto.message.UiInfoPageNames;
import com.symbio.dashboard.model.Dictionary;
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

    @Override
    public Result getDictionaryInfo(String type) {
        return getDictionaryInfoResult(type);
    }

    private Result getDictionaryInfoResult(String type) {
        List<Dictionary> dictionaryList;
        List<UiInfoPageNames> pageNamesList = new ArrayList<>();

        try {

            dictionaryList = dictionaryRep.getPageNameList(type);

            if (dictionaryList == null || dictionaryList.isEmpty()) {
                return new Result("100011", "查询失败");
            }

            for (Dictionary d : dictionaryList) {
                UiInfoPageNames uiInfoPageNames = new UiInfoPageNames();

                uiInfoPageNames.setId(d.getId());
                uiInfoPageNames.setType(d.getType());
                uiInfoPageNames.setCode(d.getCode());
                uiInfoPageNames.setValue(d.getValue());

                pageNamesList.add(uiInfoPageNames);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result("100012", "SQL Error");
        }
        return new Result(pageNamesList);
    }

    public List<Map<String, String>> getUserDefinedFields(String table) {
        List<Map<String, String>> retList = new ArrayList<>();
        try {
            Result retResult = commonDao.getDescField(table);
            Map<String, String> map = new HashMap<String, String>();
            map.put("code", "");
            map.put("value", "(Empty)");
            retList.add(map);

            if (retResult.isSuccess()) {
                List<String> listFields = (List<String>) retResult.getCd();
                String strField = "";
                for (int i = 0; i < listFields.size(); i++) {
                    strField = listFields.get(i);
                    map = new HashMap<String, String>();
                    map.put("code", strField);
                    map.put("value", strField);
                    retList.add(map);
                }
            }
        } catch (Exception e) {
            logger.error("CommonService - getUserDefinedFields() Exception!!!", e);
            retList = new ArrayList<>();
        }

        return retList;
    }
}
