package com.symbio.dashboard.business;

import com.symbio.dashboard.model.TestCase;
import com.symbio.dashboard.model.TestRun;
import com.symbio.dashboard.util.StringUtil;

import java.util.Date;

public class TestRunFactory {

    public static TestRun createNewTestRun(TestCase tc, String labelInfoIdStr, String local) {
        TestRun testRun = new TestRun();
        if (!StringUtil.isEmpty(labelInfoIdStr)) {
//            testRun.setReleaseInfoId(Integer.parseInt(labelInfoIdStr));
        }
        testRun.setTestcaseId(tc.getId());
        testRun.setDisplay(1);
        testRun.setValidation(1);
        testRun.setLocale(local);
        testRun.setCreateTime(new Date());
        return testRun;
    }
}
