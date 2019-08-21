package com.symbio.dashboard.monitor.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.data.repository.ParseResultSummaryRep;
import com.symbio.dashboard.model.ParseResultSummary;
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

    /**
     * Unzip report zip file and backup
     *
     * @return
     */
    public Result checkZipRoot() {
        Result retResult = new Result();

        // Fetch FileList
        List<String> listFile = fileService.listPath(CommonDef.FOLDER_PATH_DASHBOARD_ZIP_ROOT);
        if (CommonUtil.isEmpty(listFile)) {
            return retResult;
        }

        int nErrorCount = 0;

        // Unzip file and backup it one by one
        Result<ParseResultSummary> resultUnzip = null;
        for (String fileName : listFile) {
            resultUnzip = zipService.unZip(fileName);
            if (resultUnzip.hasError()) {
                nErrorCount++;
                log.warn(String.format("Unzip report file[%s] ERROR! ec= %s, em = %s", fileName, resultUnzip.getEc(), resultUnzip.getEm()));
            } else {
                ParseResultSummary prs = resultUnzip.getCd();
                if (prs != null) {
                    ParseResultSummary prsInfo = parseResultSumRep.getByFileName(prs.getFileName());
                    if (prsInfo == null) {
                        parseResultSumRep.saveAndFlush(prs);
                    }

                    // Move to Backup folder
                    fileService.moveFileToZipBackup(prs.getFilePath());
                }
            }
        }

        // If all is error, return the last error object
        if (listFile.size() == nErrorCount) {
            return new Result(resultUnzip);
        }

        return retResult;
    }
}
