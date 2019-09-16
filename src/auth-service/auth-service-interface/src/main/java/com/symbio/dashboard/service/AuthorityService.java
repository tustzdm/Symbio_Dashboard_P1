package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.enums.FunctionalityType;
import com.symbio.dashboard.enums.MenuType;
import org.springframework.stereotype.Service;

@Service
public interface AuthorityService {

    Result<Boolean> hasPermission(Integer userId, String menuName, String funcName);

    Result<Boolean> hasPermission(Integer userId, Integer menuCode, Integer funcCode);

    Result<Boolean> hasPermission(Integer userId, MenuType menuType, FunctionalityType funcType);

}
