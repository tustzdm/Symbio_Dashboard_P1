package com.symbio.dashboard.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.JenkinsBean;
import com.symbio.dashboard.bean.JenkinsJobArgs;
import com.symbio.dashboard.constant.ProjectConst;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.dao.JenkinsDao;
import com.symbio.dashboard.dto.TEPInfoDTO;
import com.symbio.dashboard.model.JenkinsSvrInfo;
import com.symbio.dashboard.model.TestExecPlatform;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class JenkinsServiceImpl implements IJenkinsService {

    @Autowired
    JenkinsDao jenkinsDao;
    //    @Autowired
    //    IJenkinsJobHistoryService jenkinsJobHistoryService;

    private static Integer JENKINS_PORT = null;

    @Autowired
    private CommonDao commonDao;

    private Integer getJenkinsSvrPort() {
        if (JENKINS_PORT == null) {
            JENKINS_PORT = Integer.parseInt(commonDao.getConfigValueByKey(ProjectConst.JENKINS_PORT));
        }
        return JENKINS_PORT;
    }

    /**
     * Check arguments
     *
     * @param url
     * @param userName
     * @param password
     * @param jobName
     * @return
     */
    private Result validation(String url, String userName, String password, String jobName) {
        Result retResult = new Result();

        if (StringUtil.isEmpty(url)) {
            return commonDao.getResult("000101", "Jenkins.URL");
        }
        if (StringUtil.isEmpty(userName)) {
            return commonDao.getResult("000101", "Jenkins.UserName");
        }
        if (StringUtil.isEmpty(password)) {
            return commonDao.getResult("000101", "Jenkins.Password");
        }
        if (StringUtil.isEmpty(jobName)) {
            return commonDao.getResult("000101", "Jenkins.JobName");
        }

        return retResult;
    }

    private Result validation(
            String url, String userName, String password, String jobName, Integer buildId) {
        Result retResult = validation(url, userName, password, jobName);
        if (retResult.hasError()) {
            return retResult;
        }

        if (BusinessUtil.isIdEmpty(buildId)) {
            return commonDao.getResult("000101", "Jenkins.buildId");
        }
        return retResult;
    }

    @Override
    public Result<List<JenkinsBean>> getParams(
            String url, String userName, String password, String jobName) {
        JenkinsServer server;
        Result<List<JenkinsBean>> resultData = new Result<>(new ArrayList<>());

        Result retValidation = validation(url, userName, password, jobName);
        if (retValidation.hasError()) {
            return retValidation;
        }

        try {
            Result<JenkinsServer> retData = getJenkinsServer(url, userName, password);
            if (retData.isSuccess()) {
                server = retData.getCd();
            } else {
                resultData.setEc(retData.getEc());
                resultData.setEm(retData.getEm());
                return resultData;
            }

            String xml = server.getJobXml(jobName);
            JenkinsParseUtil parseUtil = new JenkinsParseUtil();
            resultData.setCd(parseUtil.parse(xml));
        } catch (IOException e) {
            resultData = commonDao.getResult("300101", jobName);
        }
        return resultData;
    }

    private Result<List<JenkinsBean>> getParams(JenkinsSvrInfo jsi) {
        return getParams(jsi.getUrl(), jsi.getUsername(), jsi.getPassword(), jsi.getJobname());
    }

    /**
     * To fix bug: build.url is fixed to http://ip:8080/
     *
     * @param build
     * @return
     */
    private Build getExactBuild(Build build) {
        Build newbuild = build;
        if (getJenkinsSvrPort() != 8080) {
            String newUrl = build.getUrl().replace("8080", getJenkinsSvrPort().toString());
            newbuild = new Build(build.getNumber(), newUrl);
            newbuild.setClient(build.getClient());
        }
        return newbuild;
    }

    @Override
    public Result<String> getJobLastStatus(
            String url, String userName, String password, String jobName, Integer buildId) {
        log.trace("logger - getJobLastStatus() Enter");
        log.trace("log - getJobLastStatus() Enter");

        JenkinsServer server;
        Result<String> retResult = new Result<>();

        Result retValidation = validation(url, userName, password, jobName, buildId);
        if (retValidation.hasError()) {
            return retValidation;
        }

        Result<JenkinsServer> retData = getJenkinsServer(url, userName, password);
        if (retData.isSuccess()) {
            server = retData.getCd();
        } else {
            retResult = new Result<String>(retData.getEc(), retData.getEm());
            return retResult;
        }

        try {
            String strResult = "";
            JobWithDetails job = server.getJob(jobName);
            Build build = job.getBuildByNumber(buildId);

            if (build != null) {
                Build newbuild = getExactBuild(build);
                boolean building = newbuild.details().isBuilding();
                if (building) {
                    strResult = "Running";
                } else {
                    BuildResult result = newbuild.details().getResult();
                    strResult = result.toString();
                }
            }
            retResult.setCd(strResult);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("JenkinsServiceImpl.getJobLastStatus() ERROR!!!" + e.getMessage());
            retResult = commonDao.getResult("300102", jobName);
        }
        return retResult;
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
    public Job getJob(String jenkinsUrl, String userName, String password, String jobName)
            throws IOException {
        Result<JenkinsServer> jenkinsServer = getJenkinsServer(jenkinsUrl, userName, password);
        if (jenkinsServer.hasError()) {
            log.error(String.format("Ec:%s, Em:%s", jenkinsServer.getEc(), jenkinsServer.getEm()));
            return null;
        }

        JenkinsServer retJServer = jenkinsServer.getCd();
        if (retJServer == null) {
            log.warn("JenkinsServer is NULL!!!");
            return null;
        }

        return retJServer.getJob(jobName);
    }

    /**
     * 得到Server Info
     *
     * @param url
     * @param userName
     * @param password
     * @return
     */
    private Result<JenkinsServer> getJenkinsServer(String url, String userName, String password) {
        log.info("JenkinsServiceImpl.getJenkinsServer Enter.");

        Result<JenkinsServer> retData = new Result<>();
        JenkinsServer server = null;
        if (StringUtil.isEmpty(url)) {
            retData = commonDao.getResult("000101", "jenkins url");
        }
        try {
            if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
                server = new JenkinsServer(new URI(url));
            } else {
                server = new JenkinsServer(new URI(url), userName, password);
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Implement of fetching TEP data for Run Job
     *
     * @param userId
     * @param locale
     * @param testSetId
     * @return
     */
    @Override
    public Result<TEPInfoDTO> getTEPInfo(Integer userId, String locale, Integer testSetId) {
        Result<TEPInfoDTO> retTEPInfo = new Result<>();

        TEPInfoDTO dtoTepInfo = new TEPInfoDTO();
        Integer tepId = 0;

        dtoTepInfo.setLocale(locale);
        dtoTepInfo.setRole(7);


        Result<List<TestExecPlatform>> resultListTEP = jenkinsDao.getTEPList(testSetId);
        if (resultListTEP.hasError()) {
            log.info(String.format("JenkinsServiceImpl.getTEPInfo() - invoke jenkinsDao.getTEPList() failure. ec = %s, em = %s",
                    resultListTEP.getEc(), resultListTEP.getEm()));
            return new Result(resultListTEP);
        }

        List<TestExecPlatform> listTEP = resultListTEP.getCd();
        if (CommonUtil.isEmpty(listTEP)) {
            return commonDao.getResult("300501");
        }

        tepId = listTEP.get(0).getId();
        dtoTepInfo.setTepId(tepId);
        dtoTepInfo.setNameList(getNameList(listTEP));

        Result<List<JenkinsJobArgs>> resultJJA = jenkinsDao.getJobArgsInfo(locale, tepId);
        if (resultJJA.hasError()) {
            if (!resultJJA.getEc().equals("300503")) {
                return new Result(resultJJA);
            }

            // Get Job args automatically
            log.info("Need to fetch Jenkins job args automatically...");

            try {
                JenkinsSvrInfo jsi = (JenkinsSvrInfo) resultJJA.getCd();
                saveJobArgsList(jsi);
            } catch (Exception e) {
                e.printStackTrace();
            }

            dtoTepInfo.setData(new ArrayList<>());
        } else {
            List<JenkinsJobArgs> listJJA = resultJJA.getCd();
            dtoTepInfo.setData(getJJAListMap(listJJA));
        }
        retTEPInfo.setCd(dtoTepInfo);

        return retTEPInfo;
    }

    private Result saveJobArgsList(JenkinsSvrInfo jsi) {
        Result<List<JenkinsBean>> resultListJB = getParams(jsi);
        if (resultListJB.hasError()) {
            log.error(String.format("saveJobArgsList() ERROR!!! ec=%s, em=%s", resultListJB.getEc(), resultListJB.getEm()));
            return new Result(resultListJB);
        }

        List<JenkinsBean> listData = resultListJB.getCd();
        if (CommonUtil.isEmpty(listData)) {
            log.warn(String.format("Job name[%s] has no run parameters.", jsi.getJobname()));
            return commonDao.getResult("300504", jsi.getJobname());
        }

        log.info("parameters count = " + listData.size());

        Result resultSaveJobParams = jenkinsDao.saveJobParameters(jsi.getId(), listData);
        if (resultSaveJobParams.hasError()) {
            return new Result(resultSaveJobParams);
        }
        return resultSaveJobParams;
    }

    /**
     * Convert List<TestExecPlatform> to List<Map>
     *
     * @param data
     * @return
     */
    private List<Map<String, Object>> getNameList(List<TestExecPlatform> data) {
        List<Map<String, Object>> listName = new ArrayList<>();

        Map<String, Object> mapItem = null;
        for (TestExecPlatform item : data) {
            mapItem = new HashMap<>();
            mapItem.put("id", item.getId());
            mapItem.put("name", item.getName());
            listName.add(mapItem);
        }
        return listName;
    }

    /**
     * Convert List<TestExecPlatform> to List<Map>
     *
     * @param data
     * @return
     */
    private List<Map<String, Object>> getJJAListMap(List<JenkinsJobArgs> data) {
        List<Map<String, Object>> listName = new ArrayList<>();

        Map<String, Object> mapItem = null;
        for (JenkinsJobArgs item : data) {
            mapItem = new HashMap<>();
            mapItem.put("name", item.getName());
            mapItem.put("type", item.getType());

            mapItem.put("defaultValue", item.getDefaultValue());
            mapItem.put("description", item.getDescription());

            if (item.getType().equals("3")) {
                mapItem.put("choiceList", item.getChoiceList());
            }
            listName.add(mapItem);
        }
        return listName;
    }
}
