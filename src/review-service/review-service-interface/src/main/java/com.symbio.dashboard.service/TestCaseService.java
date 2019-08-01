package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public interface TestCaseService {

    Result getTestCaseList(Integer userId, String locale);

    Result getTestCasePageList(Integer userId, String locale, Integer pageIndex, Integer pageSize);
}
