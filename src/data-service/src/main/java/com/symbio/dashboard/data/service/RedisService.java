package com.symbio.dashboard.data.service;

public interface RedisService {

    String saveUserToken(Integer userId);

    /***
     * Through token access to user's userid
     *
     * */
    String getUserIdByToken(String token);

    /**
     * clean error login times, each time after login successfully cleared
     */
    void errorTimesClear(String nickName);

//    /**
//     * Save the token, valid for 24 hour
//     */
//    void tokenSave(String userId, String token);
//
//    void tokenRemove(String token);
//
//    boolean expireUserTime(String token);
//

    /**
     * Remove parameters from the redis for records of pars
     */
    void clearRecord(String pars);
//
//    /**
//     * Through user ID access TOKEN, generally is used to verify whether the user login
//     *
//     * @param userId
//     * @return token
//     */
//    String getTokenByUserId(String userId);
}
