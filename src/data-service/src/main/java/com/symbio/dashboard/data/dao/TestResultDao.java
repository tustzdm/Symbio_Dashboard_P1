package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.data.repository.ScreenShotRep;
import com.symbio.dashboard.data.repository.TestResultRep;
import com.symbio.dashboard.model.ScreenShot;
import com.symbio.dashboard.model.TestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @ClassName - TestResultDao
 * @Author - Shawn
 * @Description
 * @Date - 2019/8/23
 * @Version 1.0
 */

@Repository
@Slf4j
@SuppressWarnings("unchecked")
public class TestResultDao {

    @Autowired
    private TestResultRep testResultRep;

    @Autowired
    private ScreenShotRep screenShotRep;

    public TestResult updateTestResult(TestResult tr) {
        return testResultRep.saveAndFlush(tr);
    }

    public TestResult getTestResultById(Integer id) {
        return testResultRep.getOne(id);
    }

    public TestResult getTestResultByTestRunId(Integer testRunId) {
        return testResultRep.getByTestRunId(testRunId);
    }

    public ScreenShot updateScreenShot(ScreenShot ss) {
        return screenShotRep.saveAndFlush(ss);
    }
}


