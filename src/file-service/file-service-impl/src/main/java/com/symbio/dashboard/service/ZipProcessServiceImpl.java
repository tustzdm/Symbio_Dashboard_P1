package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.util.ZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Shawn
 * @since 2019-08
 */

@Slf4j
@Service
public class ZipProcessServiceImpl implements ZipProcessService {


    private Result<Integer> unZipFile(String fileName) {
        return ZipUtils.unZip(CommonDef.FOLDER_PATH_DASHBOARD_ZIP_ROOT + fileName, CommonDef.FOLDER_PATH_TESTRUN_ROOT, fileName);
    }

    public Result unZip(String fileName) {
        Result retResult = new Result();

        Result<Integer> resultUnzip = unZipFile(fileName);
        if (resultUnzip.hasError()) {
            log.info(String.format("unZip ERROR!!! ec=%s, em=%s", resultUnzip.getEc(), resultUnzip.getEm()));
            return new Result(resultUnzip);
        }

        return retResult;
    }
}
