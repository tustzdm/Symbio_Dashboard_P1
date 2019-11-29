package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.FilePathDTO;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.model.ParseResultSummary;
import com.symbio.dashboard.model.TestResult;
import com.symbio.dashboard.model.TestRun;
import com.symbio.dashboard.model.json.TestMethods;
import org.springframework.stereotype.Service;

@Service
public interface TestResultService {

    TestResult getTestResultByTestRunId(Integer testRunId);

    Result<TestResult> updateTestResultByReportFile(ParseResultSummary prs, TestRun tr, TestMethods testMethod, FilePathDTO dtoFilePathInfo);

    EnumDef.TEST_RESULT_STATUS getTestResultStatusEnum(Integer status);

    EnumDef.TEST_RESULT_QA_STATUS getQAStatusEnum(Integer qaStatus);

    Result<FilePathDTO> getFilePathDTOByInfo(Integer testSetId, String testCaseId, String locale);

    Result<FilePathDTO> getFilePathDTOByInfo(TestRun testRun);

    Result getTestResultInfoByTestRunId(Integer userId, String locale, Integer testRunId);

    Result updateTestResultInfo(Integer userId, String locale, Integer testRunId, Integer autoStatus, String qaStatus);
}
