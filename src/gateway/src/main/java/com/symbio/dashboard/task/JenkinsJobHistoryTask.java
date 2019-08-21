package com.symbio.dashboard.task;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.jenkins.JenkinsJobHistoryServiceImpl;
import com.symbio.dashboard.jenkins.JenkinsServiceImpl;
import com.symbio.dashboard.model.JenkinsJobHistoryMain;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JenkinsJobHistoryTask {

    @Autowired
    JenkinsServiceImpl jenkinsService;

    @Autowired
    JenkinsJobHistoryServiceImpl jjHService;

    @Async
    //@Scheduled(cron = "0 */5 * * * ? ")
    //@Scheduled(cron = "*/5 * * * * ? ")
    @Scheduled(cron = "0 0/30 * * * ? ")
    public void checkAutomationReportFile() {
        log.info("JenkinsJobHistoryTask.checkAutomationReportFile() Enter.");
        try {

            String strFolder = CommonDef.FOLDER_PATH_DASHBOARD_ZIP_ROOT;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
//    @Async
//    @Scheduled(cron = " * */10 * * * ? ")
//    public void staticsProductData() {
//        log.info("JenkinsJobHistoryTask.staticsProductData() Enter.");
//        try {
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Ref: https://www.cnblogs.com/zy-l/p/9178704.html
     */
    @Async
    @Scheduled(cron = "0 0 0/1 * * ? ")
    //@Scheduled(cron = "*/5 * * * * ? ")
    public void checkJenkinsJobStatus() {
        try {
            log.info("==================================================================");
            log.info("JenkinsJobHistoryTask.checkJenkinsJobStatus() Enter");
            log.info("==================================================================");
            System.out.println((new Date()).toString());

            // Step1: Get job list
            Result<List<JenkinsJobHistoryMain>> resultListJJHMain = jjHService.getJenkinsJobHistoryCronList();
            if (resultListJJHMain.hasError()) {
                log.warn(String.format("JenkinsJobHistoryTask Cron Task ERROR!!! ec=%s, em=%s", resultListJJHMain.getEc(), resultListJJHMain.getEm()));
            }

            List<JenkinsJobHistoryMain> listData = resultListJJHMain.getCd();
            if (CommonUtil.isEmpty(listData)) {
                return;
            }

            EnumDef.JENKINS_JOB_STATUS enumJobStatus = null;
            String oldStatus = null;
            String newStatus = null;
            Boolean bUpdateTestRun = false;
            Result<String> resultUpdateJJHMain;
            for (JenkinsJobHistoryMain item : listData) {
                log.debug("Status = " + item.getStatus());
                bUpdateTestRun = false;

                try {
                    enumJobStatus = EnumDef.getEnumTypeByCode(EnumDef.JENKINS_JOB_STATUS.class, Integer.parseInt(item.getStatus()));
                    oldStatus = enumJobStatus.getValue();
                    log.debug(String.format("Status code=%d, value=%s", enumJobStatus.getCode(), enumJobStatus.getValue()));
                } catch (Exception enumEx) {
                    enumEx.printStackTrace();
                }

                // Step2. Fetch current job status from Jenkins
                Result<EnumDef.JENKINS_JOB_STATUS> resultJobStatus = jjHService.getJobLastStatus(item);
                if (resultJobStatus.hasError()) {
                    log.warn(String.format("Fetch job status Failure. ec = %s, em = %s", resultJobStatus.getEc(), resultJobStatus.getEm()));
                } else {
                    // Step3. Update job status
                    enumJobStatus = resultJobStatus.getCd();
                    if (EnumDef.isJenkinsEndStatus(enumJobStatus)) {
                        resultUpdateJJHMain = jjHService.updateJobStatus(item, enumJobStatus);
                        if (resultUpdateJJHMain.hasError()) {
                            log.warn(String.format("Update job status failure. ec = %s, em = %s", resultUpdateJJHMain.getEc(), resultUpdateJJHMain.getEm()));
                        } else {
                            newStatus = resultUpdateJJHMain.getCd();
                            log.info(
                                    String.format(
                                            "id = %d, jobName = [%s] buildId = [%d]'s job status was changed from %s to %s\n\ttestSetId = %d, tepId = %d",
                                            item.getId(),
                                            item.getJobname(),
                                            item.getBuildId(),
                                            oldStatus,
                                            newStatus,
                                            item.getTestSetId(),
                                            item.getTepId()));

                            bUpdateTestRun = true;
                        }
                    }
                }

                // Step4 - Update Test Run status
                if (bUpdateTestRun) {
                    // ToDo: update each TestRun Id' status or TestResult's Status
                    // jenkins_job_history_detail -> TestRun or TestResult
                }
            }

            log.info("JenkinsJobHistoryTask.checkJenkinsJobStatus() Exit");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("JenkinsJobHistoryTask task() !!!Exception!!!", e);
            log.info("JenkinsJobHistoryTask.checkJenkinsJobStatus() Exception & Exit");
        }
    }

    private void printf(Thread currThead) {
        // printf(Thread.currentThread());
        System.out.println("ID = " + currThead.getId());
        System.out.println("Priority = " + currThead.getPriority());
        System.out.println("Name =" + currThead.getName());
        System.out.println("activeCount = " + currThead.activeCount());
        System.out.println("ThreadGroup = " + currThead.getThreadGroup());
        System.out.println("StackTrace = " + currThead.getStackTrace());
        System.out.println("hashCode = " + currThead.hashCode());
        System.out.println(currThead.toString());
    }
}
