package com.symbio.dashboard.task;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.jenkins.JenkinsJobHistoryServiceImpl;
import com.symbio.dashboard.jenkins.JenkinsServiceImpl;
import com.symbio.dashboard.monitor.service.impl.MonitorServiceImpl;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ZipTransferTask {

    @Autowired
    JenkinsServiceImpl jenkinsService;

    @Autowired
    JenkinsJobHistoryServiceImpl jjHService;

    @Autowired
    private MonitorServiceImpl monitorService;

    @Async
    @Scheduled(cron = "0 0/5 * * * ? ")
//    @Scheduled(cron = "0/30 * * * * ? ")
    public void processReportZipFile() {
        log.debug("ZipTransferTask.processReportZipFile() Enter");

        try {
            Result resultTask = monitorService.checkZipRoot();
            if (resultTask.hasError()) {
                log.error(ErrorConst.getWarningLogMsg("monitorService.checkZipRoot()", resultTask));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.debug("ZipTransferTask.processReportZipFile() Exit");
    }

    @Async
    //@Scheduled(cron = "0 0/3 * * * ? ")
//    @Scheduled(cron = "0/10 * * * * ? ")
    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void processTestResultByUnzipReportFile() {
        log.debug("ZipTransferTask.processTestResultByUnzipReportFile() Enter");
        try {
            Result<String> result = monitorService.parseZip();
            if (result.isSuccess()) {
                if (!CommonUtil.isEmpty(result.getCd())) {
                    log.info("monitorService.parseZip() Success. Return :" + result.getCd());
                }
            } else {
                log.warn(ErrorConst.getWarningLogMsg("monitorService.parseZip()", result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.debug("ZipTransferTask.processTestResultByUnzipReportFile() Exit");
    }

}
