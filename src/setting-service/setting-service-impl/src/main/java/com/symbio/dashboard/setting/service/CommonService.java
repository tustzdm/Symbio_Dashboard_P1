package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.Dictionay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.symbio.dashboard.data.repository.DictionayRep;

import java.util.List;

/**
 * @ClassName - CommonService
 * @Author - Admin
 * @Description -
 * @Date - 2019/7/16
 * @Version 1.0
 */

@Service
public class CommonService implements CommonServiceInterface {

  @Autowired
  private DictionayRep dictionayRep;

  @Override
  public Result getDictionaryByType(String type){

    List<Dictionay> list = dictionayRep.getDictDataByType(type);
    if(list == null || list.size() == 0) {
      return new Result("10001", "Could not get data");
    } else {
      return new Result(list);
    }
  }
}
