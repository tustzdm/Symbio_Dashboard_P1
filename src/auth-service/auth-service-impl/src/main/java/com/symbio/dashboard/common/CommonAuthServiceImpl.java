package com.symbio.dashboard.common;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

/**
 * @ClassName - CommonAuthServiceImpl
 * @Author - admin
 * @Description - TODO
 * @Date - 2019/7/17 10:29
 * @Version 1.0
 */

@Service
public class CommonAuthServiceImpl implements CommonAuthService {

    @Override
    public Result getPageNamesDictionary(String token) {
        return getPageNames(token);
    }

    private Result getPageNames(String token) {
        return new Result(token);
    }
}
