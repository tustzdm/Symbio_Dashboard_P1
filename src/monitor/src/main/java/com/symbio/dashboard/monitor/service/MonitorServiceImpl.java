package com.symbio.dashboard.monitor.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.data.repository.ParseResultSummaryRep;
import com.symbio.dashboard.service.FileServiceImpl;
import com.symbio.dashboard.service.ZipProcessServiceImpl;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MonitorServiceImpl implements MonitorService {


    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ZipProcessServiceImpl zipService;

    @Autowired
    private ParseResultSummaryRep parseResultSumRep;


    public Result checkZipRoot() {

        Result retResult = new Result();

        // Fetch FileList
        List<String> listFile = fileService.listPath(CommonDef.FOLDER_PATH_DASHBOARD_ZIP_ROOT);
        if (CommonUtil.isEmpty(listFile)) {
            return retResult;
        }

        Result resultUnzip = null;
        for (String fileName : listFile) {
            resultUnzip = zipService.unZip(fileName);
        }

        return retResult;
    }
}
