package com.symbio.dashboard.ui.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

/**
 * @ClassName - UpdateUiElementAuthServiceImpl
 * @Author - admin
 * @Description
 * @Date - 2019/7/5 15:59
 * @Version 1.0
 */

@Service
public class UpdateUiElementAuthServiceImpl implements UpdateUiElementAuthService {
    @Override
    public Result updateUiElementAuth(String token) {
        return updateUiElementAuthResult(token);
    }

    private Result updateUiElementAuthResult(String token) {

        return new Result(token);
    }
}