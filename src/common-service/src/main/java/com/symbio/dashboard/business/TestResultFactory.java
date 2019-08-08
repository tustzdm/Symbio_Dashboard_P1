package com.symbio.dashboard.business;

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
//        String environment = tc.getEnvironment();
//        if (!StringUtil.isEmpty(environment)) {
//            if ("QA".equalsIgnoreCase(environment)) {
//                environment = "0";
//            } else if ("E2E".equalsIgnoreCase(environment)) {
//                environment = "1";
//            } else {
//                environment = "-1";
//            }
//            testResult.setEnvironment(Integer.parseInt(environment));
//        }
//        testResult.setLocales(tc.getLocaleStr());
        testResult.setCreateTime(new Date());
        testResult.setUpdateUser(userinfo.getId());
        testResult.setUpdateUserName(userinfo.getFullName());
        return testResult;
    }

//    public static TestResult createUpdateTestResult(TestResultValidator cmd, User user) {
//        TestResult testResult = new TestResult();
//        testResult.setId(cmd.getTestResultId());
//        testResult.setQaStatus(cmd.getQaStatus() + "");
//        testResult.setIssueCategoryId(cmd.getIssueCategoryId());
//        testResult.setIssueReasonId(cmd.getIssueReasonId());
//        testResult.setClusterName(cmd.getClusterName());
//        testResult.setLocales(cmd.getLocale());
//        testResult.setSkus(cmd.getSkus());
//        testResult.setNotes(cmd.getNotes());
//        testResult.setRequestId(cmd.getRequestId());
//        testResult.setBuildInfo(cmd.getBuildInfo());
//        testResult.setQaStatus(cmd.getQaStatus() + "");
//        testResult.setBrowsers(cmd.getBrowsers());
//        testResult.setMethod(cmd.getMethod());
//        testResult.setRegion(cmd.getRegion());
//        testResult.setTestingNotes(cmd.getTestingNotes());
//        //testResult.setBugReport(cmd.getBugReport());
//        testResult.setUpdateUser(user.getId());
//        testResult.setUpdateUserName(user.getNickName());
//        return testResult;
//    }
}
