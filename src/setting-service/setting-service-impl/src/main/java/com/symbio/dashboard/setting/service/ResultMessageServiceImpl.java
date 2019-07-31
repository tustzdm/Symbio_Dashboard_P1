package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.repository.ResultMessageRep;
import com.symbio.dashboard.entity.Message;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.ResultMessage;
import com.symbio.dashboard.util.StringUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName - ResultMessageServiceImpl
 * @Author - Admin
 * @Description -
 * @Date - 2019/7/31
 * @Version 1.1
 */

@Service
@SuppressWarnings("unchecked")
public class ResultMessageServiceImpl implements MessageService {

    private static Logger logger = LoggerFactory.getLogger(ResultMessageServiceImpl.class);

    @Autowired
    private CommonDao commonDao;

    @Override
    public String getMessageEx(String code) {
        return getMessage(Locales.EN_US.toString(), code);
    }

    @Override
    public String getMessageEx(String code, Object... args){
        return getMessage(Locales.EN_US.toString(), code, args);
    }

    @Override
    public String getMessage(String locale, String code, Object... args) {
        return commonDao.getMessage(locale, code, args );
    }

    @Override
    public Result getResultEx(String code) {
        return getResult(Locales.EN_US.toString(), code);
    }

    @Override
    public Result getResultEx(String code, Object... args) {
        return getResult(Locales.EN_US.toString(), code, args);
    }

    @Override
    public Result getResult(String locale, String code, Object... args){
        String strMsg = getMessage(locale, code, args);
        if (strMsg == null || strMsg.isEmpty()) {
            logger.error("ResultMessageServiceImpl.getResult() Get message failure! code = " + code);
            return null;
        } else {
            return new Result(code, strMsg);
        }
    }
}
