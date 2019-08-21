package com.symbio.dashboard.task;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.jenkins.JenkinsJobHistoryServiceImpl;
import com.symbio.dashboard.jenkins.JenkinsServiceImpl;
import com.symbio.dashboard.util.ZipUtils;
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

    @Async
    //@Scheduled(cron = "0 */5 * * * ? ")
    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void checkAutomationReportFile() {
        log.info("ZipTransferTask.checkAutomationReportFile() Enter.");
        try {
            boolean bUnzipFlag = false;
            Result<Integer> retUnzip = ZipUtils.unZip("", "", "");
            if (retUnzip.isSuccess()) {
                bUnzipFlag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
