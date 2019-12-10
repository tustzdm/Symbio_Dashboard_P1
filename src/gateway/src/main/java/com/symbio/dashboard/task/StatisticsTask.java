package com.symbio.dashboard.task;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.report.service.StatisticsService;
import com.symbio.dashboard.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class StatisticsTask {

    @Autowired
    private StatisticsService statService;

    @Async
    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void statProgress() {
        String funcName = "StatisticsTask.statProcess()";
        log.debug(funcName + " Enter");
        TimeUtil tu = new TimeUtil();

        try {
            Result resultTask = statService.processTestSet();
            if (resultTask.hasError()) {
                log.error(ErrorConst.getWarningLogMsg(funcName, resultTask));
            } else {
                Map<String, Object> mapData = (Map<String, Object>) resultTask.getCd();
                log.info(mapData.toString());
            }

            resultTask = statService.processRelease();
            if (resultTask.hasError()) {
                log.error(ErrorConst.getWarningLogMsg(funcName, resultTask));
            } else {
                Map<String, Object> mapData = (Map<String, Object>) resultTask.getCd();
                log.info(mapData.toString());
            }

            resultTask = statService.processProduct();
            if (resultTask.hasError()) {
                log.error(ErrorConst.getWarningLogMsg(funcName, resultTask));
            } else {
                Map<String, Object> mapData = (Map<String, Object>) resultTask.getCd();
                log.info(mapData.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(tu.runTime());

        log.debug(funcName + " Exit");
    }


}
