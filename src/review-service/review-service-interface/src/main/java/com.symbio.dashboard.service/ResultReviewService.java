package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.ResultReviewUiDTO;
import org.springframework.stereotype.Service;

@Service
public interface ResultReviewService {

    Result<ResultReviewUiDTO> getList(Integer userId, String locale, Integer testRunId, String trLocale, Integer pageIndex, Integer pageSize);

//    Result getTestRunList(String locale, TestRunVO testRun);
//
//    Result runTestRun(String locale, TestRunVO testRun);
//
//    Result getTestRunDemoList(String locale, TestRunVO testRun);
//
//    Result importExcel(String locale, Integer testSetId, String fileName);
//
//    Result updateTestRun(Integer userId, String locale, TestRun testRun);
//
//    TestRun updateTestRun(TestRun testRun);
//
//    TestRun getTestRunByReportFileInfo(Integer testSetId, String strTestCaseId, String locale);
//
//    TestRun getTestRunById(Integer id);
//
//    Result<TestResult> updateTestResultJobWeather(JenkinsJobHistoryMain jjhM, EnumDef.JENKINS_JOB_STATUS enumJobStatus);
}
