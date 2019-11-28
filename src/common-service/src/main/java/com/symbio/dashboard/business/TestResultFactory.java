package com.symbio.dashboard.business;

import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.model.TestCase;
import com.symbio.dashboard.model.TestResult;
import com.symbio.dashboard.model.User;

import java.util.Date;

public class TestResultFactory {

    public static TestResult createNewTestResult(TestCase tc, Integer testRunId, String local) {
        TestResult testResult = new TestResult();
//        testResult.setTestsetId(tc.getId());
        testResult.setTestRunId(testRunId);
        testResult.setCreateTime(new Date());
        return testResult;
    }

    public static TestResult createImpUpdateTestResult(TestCase tc, TestResult testResult, User userinfo) {
        testResult.setCreateTime(new Date());
        testResult.setUpdateUser(userinfo.getId());
        testResult.setUpdateUserName(userinfo.getFullName());
        return testResult;
    }

    public static TestResult createNewTestResult(Integer testSetId, Integer testRunId) {

        TestResult testResult = new TestResult();

        testResult.setTestSetId(testSetId);
        testResult.setTestRunId(testRunId);
        testResult.setAutoRunStatus(EnumDef.TEST_RUN_STATUS.NOT_RUN.getCode());
        testResult.setValidation(EnumDef.ENTITY_VALIDATION.VALID.getCode());
        testResult.setCreateTime(new Date());

        return testResult;
    }
}
