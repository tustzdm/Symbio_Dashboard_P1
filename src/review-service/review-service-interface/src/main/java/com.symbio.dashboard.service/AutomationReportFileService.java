package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.JenkinsJobHistoryMain;
import com.symbio.dashboard.model.ParseResultSummary;
import com.symbio.dashboard.model.RelationTestCaseMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AutomationReportFileService {

    ParseResultSummary getParseResultSummaryByFileName(String fileName);

    Result<Map<String, Object>> getMapByReportFileName(String fileName);

    Result<JenkinsJobHistoryMain> getJobInfoFromZipName(String fileName);

    RelationTestCaseMethod getRelationTestCaseMethodByInfo(String packageInfo, String className, String methodName);
}
