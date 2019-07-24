package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.repository.ResultMessageRep;
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
 * @Date - 2019/7/24
 * @Version 1.0
 */

@Service
@SuppressWarnings("unchecked")
public class ResultMessageServiceImpl implements MessageService {

    private static Logger logger = LoggerFactory.getLogger(ResultMessageServiceImpl.class);

    @Data
    private class Message {
        public String code;
        public String enUs;
        public String zhCn;
        public String formmater;

        public Message(String ec, String en, String zh, String formmater){
            this.code = ec;
            this.enUs = en;
            this.zhCn = zh;
            this.formmater = formmater;
        }
    }

    @Autowired
    private ResultMessageRep messageRep;

    private static Map<String, Message> mapMessage = null;

    private Message getMessage(ResultMessage data) {
        return new Message(data.getCode(), data.getEnUs(), data.getZhCn(), data.getFormatter());
    }

    private Map<String, Message> getMessageMap() {
        List<ResultMessage> listData = messageRep.getAll();
        if(listData.isEmpty()) {
            logger.warn("!!!WARNING!!! Result Message is empty. Please contact administrator for initialization.");
            return null;
        }

        mapMessage = new HashMap<String, Message>();
        for(ResultMessage item: listData) {
            mapMessage.put(item.getCode(), getMessage(item));
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
            logger.error("ResultMessageServiceImpl.getMessage() ERROR!!!", e);
            e.printStackTrace();
        }

        return retMsg;
    }

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
        String retMsg = null;
        if (mapMessage == null) {
            mapMessage = getMessageMap();
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
            return null;
        } else {
            return new Result(code, strMsg);
        }
    }

}
