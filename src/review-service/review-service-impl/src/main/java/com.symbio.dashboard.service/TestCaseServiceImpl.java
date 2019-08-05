package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

/**
 * @ClassName - TestCaseServiceImpl
 * @Author - admin
 * @Description - TODO
 * @Date - 2019/8/1 15:21
 * @Version 1.0
 */

@Service
@SuppressWarnings("unchecked")
public class TestCaseServiceImpl implements TestCaseService {
    @Override
    public Result getTestCaseList(Integer userId, String locale) {
        return null;
    }

    @Override
    public Result getTestCasePageList(Integer userId, String locale, Integer pageIndex, Integer pageSize) {
        return null;
    }
}
