package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.TestRunVO;
import com.symbio.dashboard.dto.TestRunDTO;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.model.JenkinsJobHistoryMain;
import com.symbio.dashboard.model.TestResult;
import com.symbio.dashboard.model.TestRun;
import org.springframework.stereotype.Service;

@Service
public interface TestRunService {

    Result getTestRunList(String locale, TestRunVO testRun);

    Result runTestRun(String locale, TestRunVO testRun);

    Result runTestRun(TestRunDTO testRun);

    Result getTestRunDemoList(String locale, TestRunVO testRun);

    Result importExcel(String locale, Integer testSetId, String fileName);

    Result updateTestRun(Integer userId, String locale, TestRun testRun);

    TestRun updateTestRun(TestRun testRun);

    TestRun getTestRunByReportFileInfo(Integer testSetId, String strTestCaseId, String locale);

    TestRun getTestRunById(Integer id);

    Result<TestResult> updateTestResultJobWeather(JenkinsJobHistoryMain jjhM, EnumDef.JENKINS_JOB_STATUS enumJobStatus);
}
