package com.symbio.dashboard.jenkins;

import com.offbytwo.jenkins.model.Job;
import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.JenkinsBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface IJenkinsService {

    Result<List<JenkinsBean>> getParams(String url, String userName, String password, String jobName);

    Result<String> getJobLastStatus(String url, String userName, String password, String jobName, Integer buildId);

    void run(Job job, Map<String, String> params, Map<String, File> files);

    Job getJob(String jenkinsUrl, String userName, String password, String jobName) throws IOException;
}
