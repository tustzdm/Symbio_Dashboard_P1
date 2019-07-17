package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.repository.DictionaryRep;
import com.symbio.dashboard.dictionary.dto.message.UiInfoPageNames;
import com.symbio.dashboard.dictionary.dto.upload.DictionaryUpload;
import com.symbio.dashboard.model.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName - CommonServiceImpl
 * @Author - Admin
 * @Description -
 * @Date - 2019/7/16
 * @Version 1.0
 */

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private DictionaryRep dictionaryRep;

    @Override
    public Result getDictionaryInfo(DictionaryUpload dictionaryUpload) {
        return getDictionaryInfoResult(dictionaryUpload);
    }

    private Result getDictionaryInfoResult(DictionaryUpload dictionaryUpload) {
        List<Dictionary> dictionaryList;
        List<UiInfoPageNames> pageNamesList = new ArrayList<>();

        try {
            String type = dictionaryUpload.getType();

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
}
