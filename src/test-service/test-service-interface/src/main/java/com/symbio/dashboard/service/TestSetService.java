package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.TestSet;
import org.springframework.stereotype.Service;

@Service
public interface TestSetService {

    Result getTestSetList(Integer userId, String locale);

    Result getTestSetList(Integer userId);

    Result getTestSetPageList(Integer userId, String locale, int pageIndex, int pageSize);

    Result getTestSetPageList(Integer userId, int pageIndex, int pageSize);

    Result getTestSetInfo(Integer userId, Integer id);

    Result updateTestSet(TestSet testSet);

    Result removeTestSet(Integer id);
}
