package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.business.ParseResultSummaryFactory;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.model.ParseResultSummary;
import com.symbio.dashboard.util.ZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Shawn
 * @since 2019-08
 */

@Slf4j
@Service
public class ZipProcessServiceImpl implements ZipProcessService {


    public Result buildTestRun(String fileName) {

//        Result<ParseResultSummary> resultUnzip = unZip(fileName);
//        if (resultUnzip.hasError()) {
//            return resultUnzip;
//        }


        return new Result();
    }

    private Result<Map<String, Object>> unZipFile(String fileName) {
        return ZipUtils.unZip(CommonDef.FOLDER_PATH_DASHBOARD_ZIP_ROOT + fileName, CommonDef.FOLDER_PATH_TESTRUN_ROOT, fileName);
    }

    /**
     * Unzip files and store into table for next step
     *
     * @param fileName
     * @return
     */
    public Result<ParseResultSummary> unZip(String fileName) {
        Result retResult = new Result();

        Result<Map<String, Object>> resultUnzip = unZipFile(fileName);
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

    public Result<String> backupZipFile(String absoluteFile) {
        Result<String> retResult = new Result<>();

        return retResult;
    }


}
