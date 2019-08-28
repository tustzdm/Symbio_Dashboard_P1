package com.symbio.dashboard.monitor.service.impl;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.model.ParseResultSummary;
import com.symbio.dashboard.service.FileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProcessMiscellaneousServiceImpl {

    @Autowired
    private FileServiceImpl fileService;

    public Result<Boolean> cleanZipFolder(ParseResultSummary prs) {
        Result<Boolean> retResult = new Result<>();
        String funcName = "ProcessMiscellaneousServiceImpl.cleanZipFolder()";

        try {
            String strWorkPath = prs.getFileWorkPath();
            fileService.delete(strWorkPath);
            retResult.setCd(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn(ErrorConst.getExceptionLogMsg(funcName, e));
            return ErrorConst.getExceptionResult(funcName, e);
        }

        return retResult;
    }

}
