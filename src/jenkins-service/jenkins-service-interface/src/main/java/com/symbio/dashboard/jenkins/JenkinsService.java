package com.symbio.dashboard.jenkins;

import com.offbytwo.jenkins.model.Job;
import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.TEPInfoDTO;
import com.symbio.dashboard.dto.TestRunDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public interface JenkinsService {

    Result<Map<String, Object>> getParams(String url, String userName, String password, String jobName);

    Result<String> getJobLastStatus(String url, String userName, String password, String jobName, Integer buildId);

    void run(Job job, Map<String, String> params, Map<String, File> files);

    Job getJob(String jenkinsUrl, String userName, String password, String jobName) throws IOException;


    Result<TEPInfoDTO> getTEPInfo(Integer userId, String locale, Integer testSetId, Integer tepId);

    Result<String> runJob(Integer userId, String locale, Integer testSetId, Integer testRunId, Integer tepId, Map<String, Object> params);

    Result<String> runJob(Integer runCaseType, Map<String, Object> params);

    //Result<String> runJob(Integer userId, String locale, Integer tepId, Map<String, Object> params);

    Result<String> runJob(Integer userId, String locale, TestRunDTO testRun);

}
