package com.symbio.dashboard.test.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public class SaveReleaseAuthServiceImpl implements SaveReleaseAuthService {
    @Override
    public Result saveReleaseAuth(String token) {
        return saveReleaseResult(token);
    }

    private Result saveReleaseResult(String token) {

        return new Result(token);
    }
}
