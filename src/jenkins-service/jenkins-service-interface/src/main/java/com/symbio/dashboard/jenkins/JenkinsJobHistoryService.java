package com.symbio.dashboard.jenkins;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.model.JenkinsJobHistoryMain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JenkinsJobHistoryService {


    Result<List<JenkinsJobHistoryMain>> getJenkinsJobHistoryCronList();

    Result<String> updateJobStatus(JenkinsJobHistoryMain data, EnumDef.JENKINS_JOB_STATUS jobStatus);

    Result<EnumDef.JENKINS_JOB_STATUS> getJobLastStatus(JenkinsJobHistoryMain data);
}
