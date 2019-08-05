package com.symbio.dashboard.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.symbio.dashboard.bean.JenkinsBean;
import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Slf4j
@Service
@SuppressWarnings("unchecked")
public class JenkinsServiceImpl implements IJenkinsService {

//    @Autowired
//    ITestRunService testRunService;
//    @Autowired
//    IJenkinsJobHistoryService jenkinsJobHistoryService;

    private static Logger log = LoggerFactory.getLogger(JenkinsServiceImpl.class);

    @Autowired
    CommonDao commonDao;

    @Override
    public Result<List<JenkinsBean>> getParams(String url, String username, String password, String jobName) {
        JenkinsServer server;
        Result<List<JenkinsBean>> resultData = new Result<>();
//        if (!StringUtils.isEmpty(jobName)) {
//            ResultData<JenkinsServer> retData = getJenkinsServer(url, username, password);
//            if (retData.isExecuteSuccess()) {
//                server = retData.getData();
//            } else {
//                resultData.setEc(retData.getEc());
//                resultData.setMsg(retData.getMsg());
//                return resultData;
//            }
//            try {
//                String xml = server.getJobXml(jobName);
//                ParseUtil ul = new ParseUtil();
//                resultData.setData(ul.parse(xml));
//            } catch (IOException e) {
//                resultData.setEc("001");
//                resultData.setMsg(" can not get the job's config xml ,please check the jobName");
//            }
//        }
        return resultData;
    }

    @Override
    public Result<String> getJobLastStatus(String url, String username, String password, String jobname,
                                                 Integer buildId) {
        JenkinsServer server;
        Result<String> ret = new Result<>();
//        ResultData<JenkinsServer> retData = getJenkinsServer(url, username, password);
//        if (retData.isExecuteSuccess()) {
//            server = retData.getData();
//        } else {
//            ret.setEc(retData.getEc());
//            ret.setMsg(retData.getMsg());
//            return ret;
//        }
//        try {
//            String data = "";
//            Build build = server.getJob(jobname).getBuildByNumber(buildId);
//            if (build != null) {
//                boolean building = build.details().isBuilding();
//                if (building) {
//                    data = "Running";
//                } else {
//                    BuildResult result = server.getJob(jobname).getBuildByNumber(buildId).details().getResult();
//                    data = result.toString();
//                }
//            }
//            ret.setData(data);
//        } catch (IOException e) {
//            ret.setEc("001");
//            ret.setMsg(jobname + " job is not exist");
//        }
        return ret;
    }

    @Override
    public void run(Job job, Map<String, String> params, Map<String, File> files) {
        Map<String, String> headMap = new HashMap<>(params);

        Assert.notNull(job, "no Job exist to run the execution");
        log.info("executing job {} with params: {}", job.getName(), headMap);

        try {
            job.build(headMap, files);
        } catch (Exception e) {
            log.warn("failed to execute job on jenkins", e);
            throw new IllegalStateException(" can not get the job ");
        }

    }

    @Override
    public Job getJob(String jenkinsUrl, String username, String password, String jobName) throws IOException {
        Result<JenkinsServer> jenkinsServer = getJenkinsServer(jenkinsUrl, username, password);
        if(jenkinsServer.hasError()) {
            log.error(String.format("Ec:%s, Em:%s",jenkinsServer.getEc(),jenkinsServer.getEm()));
            return null;
        }

        JenkinsServer retJServer = jenkinsServer.getCd();
        if(retJServer == null) {
            log.warn("JenkinsServer is NULL!!!");
            return null;
        }

        return retJServer.getJob(jobName);
    }

    private Result<JenkinsServer> getJenkinsServer(String url, String username, String password) {
        log.info("JenkinsServiceImpl.getJenkinsServer Enter.");

        Result<JenkinsServer> retData = new Result<>();
        JenkinsServer server = null;
        if (StringUtil.isEmpty(url)) {
            retData = commonDao.getResult("000101", "jenkins url");
        }
        try {
            if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
                server = new JenkinsServer(new URI(url));
            } else {
                server = new JenkinsServer(new URI(url), username, password);
            }
            if (!server.isRunning()) {
                retData = commonDao.getResult("000201");
            } else {
                retData.setCd(server);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.error("Wrong jenkins url", e);
            retData = commonDao.getResult("000102", "accessing Jenkins service");
        }

        log.info("JenkinsServiceImpl.getJenkinsServer Exit");
        return retData;
    }


}

