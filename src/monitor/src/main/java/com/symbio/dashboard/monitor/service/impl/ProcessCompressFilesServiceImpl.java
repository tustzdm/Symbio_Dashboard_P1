package com.symbio.dashboard.monitor.service.impl;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.business.ParseResultSummaryFactory;
import com.symbio.dashboard.model.ParseResultSummary;
import com.symbio.dashboard.service.FileServiceImpl;
import com.symbio.dashboard.service.ZipProcessServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class ProcessCompressFilesServiceImpl {

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ZipProcessServiceImpl zipService;

    public Result<ParseResultSummary> unZip(String fileName) {
        Result<ParseResultSummary> retResult = new Result();

        Result<Map<String, Object>> resultUnzip = zipService.unZipFile(fileName);
        if (resultUnzip.hasError()) {
            log.info(String.format("unZip ERROR!!! ec=%s, em=%s", resultUnzip.getEc(), resultUnzip.getEm()));
            return new Result(resultUnzip);
        }

        Map<String, Object> mapData = resultUnzip.getCd();
        ParseResultSummary prs = ParseResultSummaryFactory.createNewParseResultSummaryByMap(mapData);
        if (prs != null) {
            retResult.setCd(prs);
        }

        return retResult;
    }
}
