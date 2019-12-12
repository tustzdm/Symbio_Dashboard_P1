package com.symbio.dashboard.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.JenkinsBean;
import com.symbio.dashboard.bean.JenkinsJobArgs;
import com.symbio.dashboard.business.JenkinsJobArgsFactory;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.constant.ProjectConst;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.dao.JenkinsDao;
import com.symbio.dashboard.dto.TEPInfoDTO;
import com.symbio.dashboard.dto.TestRunDTO;
import com.symbio.dashboard.enums.EnumDef;
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
public class JenkinsServiceImpl implements JenkinsService {

    @Autowired
    JenkinsDao jenkinsDao;
    @Autowired
    private CommonDao commonDao;

    private static Integer JENKINS_PORT = null;
    private final Integer JENKINS_PORT_8080 = 8080;

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
            return commonDao.getEmptyArgsResult("Jenkins.URL");
        }
        if (StringUtil.isEmpty(userName)) {
            return commonDao.getEmptyArgsResult("Jenkins.UserName");
        }
        if (StringUtil.isEmpty(password)) {
            return commonDao.getEmptyArgsResult("Jenkins.Password");
        }
        if (StringUtil.isEmpty(jobName)) {
            return commonDao.getEmptyArgsResult("Jenkins.JobName");
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
    public Result<Map<String, Object>> getParams(
            String url, String userName, String password, String jobName) {
        JenkinsServer server;
        Result<Map<String, Object>> resultData = new Result();

        Result retValidation = validation(url, userName, password, jobName);
        if (retValidation.hasError()) {
            return retValidation;
        }

        try {
            Result<JenkinsServer> retData = getJenkinsServer(url, userName, password);
            if (retData.isSuccess()) {
                server = retData.getCd();
            } else {
//                resultData.setEc(retData.getEc());
//                resultData.setEm(retData.getEm());
                return new Result(resultData);
            }

            String xml = server.getJobXml(jobName);
            JenkinsParseUtil parseUtil = new JenkinsParseUtil();

            Map<String, Object> mapData = new HashMap<>();
            mapData.put("jobXML", xml);
            mapData.put("data", parseUtil.parse(xml));


            resultData.setCd(mapData);
        } catch (IOException e) {
            resultData = commonDao.getResult("300101", jobName);
        }
        return resultData;
    }

    private Result<Map<String, Object>> getParams(JenkinsSvrInfo jsi) {
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
        if (getJenkinsSvrPort() != JENKINS_PORT_8080) {
            String newUrl = build.getUrl().replace(String.valueOf(JENKINS_PORT_8080), getJenkinsSvrPort().toString());
            newbuild = new Build(build.getNumber(), newUrl);
            newbuild.setClient(build.getClient());
        }
        return newbuild;
    }

    private Job getExactJob(Job job) {
        Job newJob = job;
        if (getJenkinsSvrPort() != JENKINS_PORT_8080) {
            String newUrl = job.getUrl().replace(String.valueOf(JENKINS_PORT_8080), getJenkinsSvrPort().toString());
            newJob = new Job(job.getName(), newUrl);
            newJob.setClient(job.getClient());
        }
        return newJob;
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
            String strResult = EnumDef.JENKINS_JOB_STATUS.JOB_NOT_FOUND.getValue();
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
            throw new IllegalStateException(e);
        }
    }

    private Job getJob(JenkinsSvrInfo jsi) throws IOException {
        return getJob(jsi.getUrl(), jsi.getUsername(), jsi.getPassword(), jsi.getJobname());
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
    @Override
    public Result<String> runJob(Integer runCaseType, Map<String, Object> params) {

        if (runCaseType != EnumDef.CASE_TYPE.AUTOMATION.getCode()) {
            log.warn("Do not support this runCaseType: " + runCaseType);
            return commonDao.getResult("300600", runCaseType);
        }

        Result<String> retResult = new Result<>();

        Map<String, Object> mapData = params;
        Integer userId = Integer.parseInt(mapData.get("userId").toString());
        String locale = mapData.get("locale").toString();
        Integer testRunId = Integer.parseInt(mapData.get("testRunId").toString());
        Integer tepId = Integer.parseInt(mapData.get("tepId").toString());

        return this.runJob(userId, locale, null, testRunId, tepId, mapData);
    }

    @Override
    public Result<String> runJob(Integer userId, String locale, Integer testSetId, Integer testRunId, Integer tepId, Map<String, Object> params) {
        return this.runJob(userId, locale, testSetId, testRunId, tepId, params, 0);
    }

    private Result<String> runJob(Integer userId, String locale, Integer testSetId, Integer testRunId, Integer tepId, Map<String, Object> params, Integer delta) {
        Result<String> retResult = new Result<>();

        // Step1 - Get JSI info
        Result<JenkinsSvrInfo> retJSI = jenkinsDao.getJSIByTepId(locale, tepId);
        if (retJSI.hasError()) {
            return new Result<>(retJSI);
        }
        JenkinsSvrInfo jsi = retJSI.getCd();
        Job job = null;
        Map<String, String> mapRunArgs = null;
        Integer nextBuildNumber = 0;

        try {
            job = this.getJob(jsi);
            nextBuildNumber = ((JobWithDetails) job).getNextBuildNumber();
            if (delta > 0) {
                nextBuildNumber += delta;
            }

            job = getExactJob(job);

            // Step2 - Get job parameters
            Result<List<JenkinsJobArgs>> retJJA = jenkinsDao.getJobArgsInfo(locale, tepId);
            if (retJJA.hasError()) {
                return new Result<>(retJJA);
            }
            List<JenkinsJobArgs> listJJA = retJJA.getCd();

            String jobRunToken = commonDao.getConfigValueByKey(ProjectConst.JENKINS_JOB_TOKEN);
            mapRunArgs = JenkinsJobArgsFactory.buildRunMapWithMap(listJJA, jobRunToken, params);

            // Step3 - run job
            try {
                this.run(job, mapRunArgs, new HashMap<>());
            } catch (Exception e) {
                e.printStackTrace();
                log.error("runJob.run() ERROR!!! " + e.getMessage());
                return commonDao.getResult("300506", jsi.getJobname(), e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("runJob.getJob() ERROR!!! " + e.getMessage());
            return commonDao.getExceptionArgsResult("running jenkins job. Message is : " + e.getMessage());
        }

        // Step4 - Get buildId
        Result retSaveJobInfo = jenkinsDao.saveNewJobHistory(userId, locale, testSetId, testRunId, tepId, jsi, mapRunArgs, nextBuildNumber);
        if (retSaveJobInfo.hasError()) {
            log.warn("Invoke jenkinsDao.saveNewJobHistory() ERROR!!! ec = %s, em = %s" + retSaveJobInfo.getEm());
        }
        retResult.setCd("buildId = " + nextBuildNumber);

        return retResult;
    }

    @Override
    public Result<String> runJob(Integer userId, String locale, TestRunDTO testRun) {
        Result<String> retResult = new Result<>();

        Integer testSetId = testRun.getTestSetId();
        String testRunIds = testRun.getIds();
        Integer tepId = testRun.getTepId();

        String[] arrTestRunId = testRunIds.split(",");
        Map<String, Object> mapData = null;
        Integer testRunId = 0;

        try {
            // Step1 - Get JSI info
            Result<JenkinsSvrInfo> retJSI = jenkinsDao.getJSIByTepId(locale, tepId);
            if (retJSI.hasError()) {
                return new Result<>(retJSI);
            }
            JenkinsSvrInfo jsi = retJSI.getCd();
            Job job = null;
            Map<String, String> mapRunArgs = null;
            Integer nextBuildNumber = 0;

            job = this.getJob(jsi);
            nextBuildNumber = ((JobWithDetails) job).getNextBuildNumber();
            job = getExactJob(job);

            // Step2 - Get job parameters
            Result<List<JenkinsJobArgs>> retJJA = jenkinsDao.getJobArgsInfo(locale, tepId);
            if (retJJA.hasError()) {
                return new Result<>(retJJA);
            }
            List<JenkinsJobArgs> listJJA = retJJA.getCd();
            String jobRunToken = commonDao.getConfigValueByKey(ProjectConst.JENKINS_JOB_TOKEN);

            // loop
            List<Integer> listBuildNum = new ArrayList<>();
            for (int i = 0; i < arrTestRunId.length; i++) {
                testRunId = Integer.parseInt(arrTestRunId[i]);
                mapData = JenkinsJobArgsFactory.getExactJobParams(testRun.getParameters(), testRunId);

                if (i > 0) { // incrementation
                    nextBuildNumber++;
                }

                mapRunArgs = JenkinsJobArgsFactory.buildRunMapWithMap(listJJA, jobRunToken, mapData);

                // Step3 - run job
                try {
                    this.run(job, mapRunArgs, new HashMap<>());
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("runJob.run() ERROR!!! " + e.getMessage());
                    return commonDao.getResult("300506", jsi.getJobname(), e.getMessage());
                }

                // Step4 - Get buildId
                Result retSaveJobInfo = jenkinsDao.saveNewJobHistory(userId, locale, testSetId, testRunId, tepId, jsi, mapRunArgs, nextBuildNumber);
                if (retSaveJobInfo.hasError()) {
                    log.warn("Invoke jenkinsDao.saveNewJobHistory() ERROR!!! ec = %s, em = %s" + retSaveJobInfo.getEm());
                }
                listBuildNum.add(nextBuildNumber);
            }

            String msg = String.format("jobCount = %d, buildId = %s", listBuildNum.size(), listBuildNum.toString());
            retResult.setCd(msg);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("runJob.getJob() ERROR!!! " + e.getMessage());
            return commonDao.getExceptionArgsResult("running jenkins job. Message is : " + e.getMessage());
        }

        return retResult;
    }

    /**
     * Implement of fetching TEP data for Run Job
     *
     * @param userId
     * @param locale
     * @param testSetId
     * @return
     */
    @Override
    public Result<TEPInfoDTO> getTEPInfo(Integer userId, String locale, Integer testSetId, Integer tepId) {
        Result<TEPInfoDTO> retTEPInfo = new Result<>();

        TEPInfoDTO dtoTepInfo = new TEPInfoDTO();
        Integer newTEPId = tepId;

        dtoTepInfo.setLocale(locale);
        dtoTepInfo.setRole(7);

        // Step1 - Get TEP List
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

        if (CommonUtil.isEmpty(newTEPId)) {
            newTEPId = listTEP.get(0).getId();
        }
        dtoTepInfo.setTepId(newTEPId);
        dtoTepInfo.setNameList(getNameList(listTEP));

        // Step2 - Get Job args that corresponds to TEP selected
        Result<List<JenkinsJobArgs>> resultJJA = jenkinsDao.getJobArgsInfo(locale, newTEPId);
        List<JenkinsJobArgs> listJJA;
        if (resultJJA.hasError()) {
            if (!resultJJA.getEc().equals(ErrorConst.JENKINS_FETCH_JOBARGS_AUTOMATICALLY)) {
                return new Result(resultJJA);
            }

            // Get Job args automatically
            log.info("Need to fetch Jenkins job args automatically...");

            // Step 2.1 - Save Job Args data if need
            JenkinsSvrInfo jsi = (JenkinsSvrInfo) resultJJA.getCd();
            Result<List<JenkinsJobArgs>> retSaveJJP = saveJobArgsList(jsi);
            if (retSaveJJP.hasError()) {
                return new Result(retSaveJJP);
            } else {
                listJJA = retSaveJJP.getCd();
            }
        } else {
            listJJA = resultJJA.getCd();
        }
        dtoTepInfo.setData(getJJAListMap(listJJA));
        retTEPInfo.setCd(dtoTepInfo);

        return retTEPInfo;
    }

    private Result saveJobArgsList(JenkinsSvrInfo jsi) {
        Result<Map<String, Object>> resultListJB = getParams(jsi);
        if (resultListJB.hasError()) {
            log.error(String.format("saveJobArgsList() ERROR!!! ec=%s, em=%s", resultListJB.getEc(), resultListJB.getEm()));
            return new Result(resultListJB);
        }

        Result<List<JenkinsJobArgs>> resultSaveJobParams = new Result<>();
        try {
            Map<String, Object> mapData = resultListJB.getCd();
            String strJobXML = (String) mapData.get("jobXML");


            List<JenkinsBean> listData = (List<JenkinsBean>) mapData.get("data");
            if (CommonUtil.isEmpty(listData)) {
                log.warn(String.format("Job name[%s] has no run parameters.", jsi.getJobname()));
                return commonDao.getResult(ErrorConst.JENKINS_JOB_NO_PARAMETER, jsi.getJobname());
            }

            log.info("parameters count = " + listData.size());

            resultSaveJobParams = jenkinsDao.saveJobParameters(strJobXML, jsi.getId(), listData);
            if (resultSaveJobParams.hasError()) {
                return new Result(resultSaveJobParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("JenkinsServiceImpl.saveJobArgsList() ERROR!!!" + e.getMessage());
            return commonDao.getExceptionArgsResult("saving job parameters. name = " + jsi.getJobname());
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
