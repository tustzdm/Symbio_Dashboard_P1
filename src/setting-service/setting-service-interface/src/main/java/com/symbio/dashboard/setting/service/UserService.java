package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Result getUserInfo(Integer id);

    Result getUserList(Integer productId);

    Result getUserListByStatus(Integer status);

    Result getUserInfo(String locale, Integer id);
    Result getUserList(String locale,Integer productId);
    Result getUserListByStatus(String locale, Integer status);
}
