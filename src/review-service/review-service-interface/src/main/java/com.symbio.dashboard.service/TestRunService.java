package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.TestRunVO;
import org.springframework.stereotype.Service;

@Service
public interface TestRunService {

    Result getTestRunList(String locale, TestRunVO testRun);

}
