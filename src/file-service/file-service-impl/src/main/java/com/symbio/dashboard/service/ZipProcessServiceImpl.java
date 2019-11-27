package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.util.ZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @since 2019-08
 */

@Slf4j
@Service
public class ZipProcessServiceImpl implements ZipProcessService {

    @Autowired
    private FileServiceImpl fileService;

    /**
     * Unzip files and store into table for next step
     *
     * @param fileName
     * @return
     */
    @Override
    public Result<Map<String, Object>> unZipFile(String fileName) {
        return ZipUtils.unZip(CommonDef.FOLDER_PATH_DASHBOARD_ZIP_ROOT + fileName, CommonDef.FOLDER_PATH_TESTRUN_ROOT, fileName);
    }

    public Result<String> backupZipFile(String absoluteFile) {
        return fileService.moveFileToZipBackup(absoluteFile);
    }


}
