package com.symbio.dashboard.data.service.impl;

import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.data.service.RedisService;
import com.symbio.dashboard.encrypt.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@Slf4j
public class RedisServiceImpl implements RedisService {
    @Autowired
    StringRedisTemplate redisTemplate;

    private boolean isRedisValueEmpty(String value) {
        return value == null || "nil".equals(value);
    }

    @Override
    public void errorTimesClear(String nickName) {
        redisTemplate.delete(CommonDef.FAIL_LOGIN_USER_ID + nickName);
    }

    //    @Override
    public void tokenRemove(String token) {
        String tokenkey = CommonDef.USER_TOKEN + token;
        String userid = redisTemplate.opsForValue().get(tokenkey);
        if (userid == null || "nil".equals(userid)) {
            return;
        }
        redisTemplate.delete(tokenkey);
        String useridkey = CommonDef.USERID_PRE + userid;
        redisTemplate.delete(useridkey);
        String useridTokenkey = CommonDef.USER_ID_TOKEN_MAPPING + userid;
        redisTemplate.delete(useridTokenkey);
    }

    /*
     * TOKEN serid
     * @param userId ID
     * @param token
     *
     */
//    @Override
    public void tokenSave(String userId, String token) {
        String tokenkey = CommonDef.USER_TOKEN + token;
        redisTemplate.opsForValue().set(tokenkey, userId);
        log.info("tokenkey:" + tokenkey + "redis");

        redisTemplate.expire(tokenkey, CommonDef.USER_EXPIRE_TIME, TimeUnit.MINUTES);

        String userIdkey = CommonDef.USER_ID_TOKEN_MAPPING + userId;
        redisTemplate.opsForValue().set(userIdkey, token);
        log.info("userid:" + userIdkey + "tokenredis");

        redisTemplate.expire(userIdkey, CommonDef.USER_EXPIRE_TIME, TimeUnit.MINUTES);
    }

    private void clearUserToken(Integer userId) {
        String userIdKey = CommonDef.USER_ID_TOKEN_MAPPING + userId;
        String token = redisTemplate.opsForValue().get(userIdKey);

        if (!isRedisValueEmpty(token)) {
            String tokenKey = CommonDef.USER_TOKEN + token;
            clearRecord(tokenKey);
            clearRecord(userIdKey);
        }
    }

    @Override
    public String saveUserToken(Integer userId) {
        if (userId == null || userId < 0) return "";

        clearUserToken(userId);

        // Create new token
        Long encUserId = System.currentTimeMillis() + userId;
        String token = MD5Util.encrypt(String.valueOf(encUserId));

        String tokenKey = CommonDef.USER_TOKEN + token;
        redisTemplate.opsForValue().set(tokenKey, userId.toString());
        redisTemplate.expire(tokenKey, CommonDef.USER_EXPIRE_TIME, TimeUnit.MINUTES);
        redisTemplate.getExpire(tokenKey);

        log.info("tokenKey:" + tokenKey + ", userId:" + userId);

        String userIdKey = CommonDef.USER_ID_TOKEN_MAPPING + userId;
        redisTemplate.opsForValue().set(userIdKey, token);
        log.info("userId:" + userIdKey + ", token:" + token);
        redisTemplate.expire(userIdKey, CommonDef.USER_EXPIRE_TIME, TimeUnit.MINUTES);
        redisTemplate.getExpire(userIdKey);

        return token;
    }


    /*
     * TOKENID
     * @param token
     * @return ID
     */
    @Override
    public String getUserIdByToken(String token) {
        if (token == null || "".equals(token)) return "";
        String userId = "";

        if (CommonDef.isDEVEnvironment() && "symbio".equals(token)) {
            return "1"; // admin
        }

        try {
            String key = CommonDef.USER_TOKEN + token;
            userId = redisTemplate.opsForValue().get(key);
            if (!isRedisValueEmpty(userId)) {
                long timeout = redisTemplate.getExpire(key);
                if (timeout == -1L) {
                    return "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Invoking getUserIdByToken() ERROR!!! " + e.getMessage());
            userId = "";
        }

        return userId;
    }

    /*
     * IDTOKEN
     * @param token
     * @return ID
     */
//    @Override
    public String getTokenByUserId(String userId) {
        String key = CommonDef.USER_ID_TOKEN_MAPPING + userId;
        String token = redisTemplate.opsForValue().get(key);
        if (!isRedisValueEmpty(token)) {
            long timeout = redisTemplate.getExpire(key);
            if (timeout == -1L) {
                return "";
            }
        } else
            token = "";
        return token;
    }

    /*
     *
     * @param token
     * @return
     */
//    @Override
    public boolean expireUserTime(String token) {
        String tokenkey = CommonDef.USER_TOKEN + token;
        String userid = redisTemplate.opsForValue().get(tokenkey);
        if (userid != null && !"nil".equals(userid)) {
            redisTemplate.expire(tokenkey, CommonDef.USER_EXPIRE_TIME, TimeUnit.MINUTES);
            String useridkey = CommonDef.USERID_PRE + userid;
            redisTemplate.expire(useridkey, CommonDef.USER_EXPIRE_TIME, TimeUnit.MINUTES);
            String useridTokenkey = CommonDef.USER_ID_TOKEN_MAPPING + userid;
            redisTemplate.expire(useridTokenkey, CommonDef.USER_EXPIRE_TIME, TimeUnit.MINUTES);
            return true;
        }
        return false;
    }

    /**
     * redispars
     */
    @Override
    public void clearRecord(String pars) {
        redisTemplate.delete(pars);
    }
}
