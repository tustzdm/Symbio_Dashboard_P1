package com.symbio.dashboard.jenkins;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.dao.JenkinsDao;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.model.JenkinsJobHistoryMain;
import com.symbio.dashboard.model.JenkinsSvrInfo;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class JenkinsJobHistoryServiceImpl implements JenkinsJobHistoryService {

    @Autowired
    JenkinsDao jenkinsDao;
    @Autowired
    private CommonDao commonDao;
    @Autowired
    JenkinsServiceImpl jenkinsService;

    @Override
    public Result<List<JenkinsJobHistoryMain>> getJenkinsJobHistoryCronList() {
        Result<List<JenkinsJobHistoryMain>> retResult = new Result<List<JenkinsJobHistoryMain>>();

        List<JenkinsJobHistoryMain> listjjHMain = jenkinsDao.getJenkinsJobHistoryCronList();
        if (CommonUtil.isEmpty(listjjHMain)) {
            listjjHMain = new ArrayList<>();
        } else {
            log.debug("Jenkins job count needed updating is ：" + listjjHMain.size());
        }
        retResult.setCd(listjjHMain);

        return retResult;
    }

    @Override
    public Result<EnumDef.JENKINS_JOB_STATUS> getJobLastStatus(JenkinsJobHistoryMain data) {
        Result<EnumDef.JENKINS_JOB_STATUS> retResult = new Result<>();

        JenkinsSvrInfo jsi = jenkinsDao.getJSIById(data.getJsiId());
        if (CommonUtil.isEmpty(jsi)) {
            log.warn(String.format("Invoke JenkinsJobHistoryServiceImpl.getJobLastStatus() WARNING!!!, " +
                    "Could not find db data. table = %s, id = %d", "jenkins_svr_info", data.getJsiId()));
            return commonDao.getTableNoDataArgsResult("jenkins_svr_info", data.getJsiId());
        }

        Result<String> retJobStatus = jenkinsService.getJobLastStatus(jsi.getUrl(), jsi.getUsername(), jsi.getPassword(),
                data.getJobname(), data.getBuildId());
        if (retJobStatus.hasError()) {
            return new Result(retJobStatus);
        }

        String newStatus = retJobStatus.getCd();
        log.info(String.format("JobName = %s, buildId = %d, Current status = %s",
                data.getJobname(), data.getBuildId(), newStatus));

        EnumDef.JENKINS_JOB_STATUS enumJobStatus = null;
        try {
            enumJobStatus = EnumDef.getEnumTypeByValue(EnumDef.JENKINS_JOB_STATUS.class, newStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (enumJobStatus == null) {
            log.error("Could not find exact EnumDef.JENKINS_JOB_STATUS item. Status = " + newStatus);
            return commonDao.getResult("300507", newStatus);
        }

        // Corresponds to JenkinsJobHistoryFactory.createNewHistoryMainInfo().setStatus()
        retResult.setCd(enumJobStatus);
        return retResult;
    }

    @Override
    public Result<String> updateJobStatus(JenkinsJobHistoryMain data, EnumDef.JENKINS_JOB_STATUS jobStatus) {
        Result<String> retResult = new Result<>();

        try {
            data.setStatus(jobStatus.toString());
            jenkinsDao.updateJenkinsJobHistoryMain(data);
            retResult.setCd(jobStatus.getValue());
        } catch (Exception e) {
            log.error("Updating JenkinsJobHistoryMain Exception. id = " + data.getId());
            return commonDao.getExceptionArgsResult("updating JenkinsJobHistoryMain data. id = " + data.getId());
        }

        return retResult;
    }
}
