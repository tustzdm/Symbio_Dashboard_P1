package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.TestSet;
import org.springframework.stereotype.Service;

@Service
public interface TestSetService {

    Result getTestSetList(Integer userId, String locale, Integer releaseId, Integer pageIndex, Integer pageSize);

    Result getTestSetList(Integer releaseId, Integer pageIndex, Integer pageSize);


    Result getTestSetInfo(Integer userId, Integer id);

    Result updateTestSet(Integer userId, String locale, TestSet testSet);

    Result removeTestSet(Integer id);

    Result getTestSetUiInfo(Integer userId, String locale, Integer uiInfo, Integer id);
}
