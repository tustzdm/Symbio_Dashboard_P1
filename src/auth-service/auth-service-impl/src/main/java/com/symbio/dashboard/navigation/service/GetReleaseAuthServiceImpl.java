package com.symbio.dashboard.navigation.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public class GetReleaseAuthServiceImpl implements GetReleaseAuthService {

    @Override
    public Result getReleaseAuth(String token) {
        return getReleaseAuthByToken(token);
    }

    private Result getReleaseAuthByToken(String token) {
        return new Result("release token");
    }
}
