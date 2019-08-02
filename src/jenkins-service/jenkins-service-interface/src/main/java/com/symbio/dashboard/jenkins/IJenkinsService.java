package com.symbio.dashboard.jenkins;

import com.offbytwo.jenkins.model.Job;
import com.symbio.dashboard.bean.JenkinsBean;
import com.symbio.dashboard.bean.ResultData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface IJenkinsService {

    ResultData<List<JenkinsBean>> getParams(String url, String username, String password, String jobName);

    ResultData<String> getJobLastStatus(String url, String username, String password, String jobName, Integer buildId);

    void run(Job job, Map<String, String> params, Map<String, File> files);

    Job getJob(String jenkinsUrl, String username, String password, String jobName) throws IOException;
}
