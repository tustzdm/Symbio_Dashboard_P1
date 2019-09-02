package com.symbio.dashboard.ui.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

/**
 * @ClassName - RemoveUiElementAuthServiceImpl
 * @Author - admin
 * @Description
 * @Date - 2019/7/9 16:41
 * @Version 1.0
 */

@Service
public class RemoveUiElementAuthServiceImpl implements RemoveUiElementAuthService {
    @Override
    public Result removeUiElementAuth(String token) {
        return removeUiElementResult(token);
    }

    private Result removeUiElementResult(String token) {

        return new Result(token);
    }
}
