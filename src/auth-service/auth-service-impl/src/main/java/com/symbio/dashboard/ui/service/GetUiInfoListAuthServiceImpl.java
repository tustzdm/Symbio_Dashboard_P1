package com.symbio.dashboard.ui.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

/**
 * @ClassName - GetUiInfoListAuthServiceImpl
 * @Author - admin
 * @Description -
 * @Date - 2019/7/5 15:59
 * @Version 1.0
 */

@Service
@SuppressWarnings("unchecked")
public class GetUiInfoListAuthServiceImpl implements GetUiInfoListAuthService {
    @Override
    public Result getUiInfoListAuth(String token) {
        return getUiInfoListResult(token);
    }

    private Result<String> getUiInfoListResult(String token) {
        return new Result(token);
    }
}
