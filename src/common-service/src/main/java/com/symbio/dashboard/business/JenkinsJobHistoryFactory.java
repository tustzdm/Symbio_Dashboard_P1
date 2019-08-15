package com.symbio.dashboard.business;

import com.symbio.dashboard.enums.EntityDisplay;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.model.JenkinsJobHistoryMain;
import com.symbio.dashboard.model.JenkinsSvrInfo;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.JSONUtil;

import java.util.Date;
import java.util.Map;

public class JenkinsJobHistoryFactory {

    public static JenkinsJobHistoryMain createNewHistoryMainInfo(User user, Integer testSetId, Integer tepId, JenkinsSvrInfo jsi, Map<String, String> map, Integer buildId) {
        JenkinsJobHistoryMain data = new JenkinsJobHistoryMain();

        data.setTestSetId(testSetId);
        data.setTepId(tepId);
        data.setJsiId(jsi.getId());
        data.setJobname(jsi.getJobname());
        data.setJenkinsJobParameter(JSONUtil.mapToString(map));
        data.setStatus(EnumDef.JENKINS_JOB_STATUS.WAITING.getValue());
        data.setBuildId(buildId);
        data.setParseCount(0);
        data.setDescription("");

        data.setDisplay(EntityDisplay.SHOW.getValue());
        data.setCreateTime(new Date());
        data.setCreateUserId(user.getId());
        data.setCreateUserName(user.getFullName());

        return data;
    }
}
