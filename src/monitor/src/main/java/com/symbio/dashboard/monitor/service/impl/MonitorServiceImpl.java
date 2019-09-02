package com.symbio.dashboard.monitor.service.impl;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.business.ParseResultSummaryFactory;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.model.ParseResultSummary;
import com.symbio.dashboard.model.json.ReportJson;
import com.symbio.dashboard.monitor.service.MonitorService;
import com.symbio.dashboard.service.AutomationReportFileServiceImpl;
import com.symbio.dashboard.service.FileServiceImpl;
import com.symbio.dashboard.service.ParseResultSummaryServiceImpl;
import com.symbio.dashboard.service.ZipProcessServiceImpl;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.JSONFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ZipProcessServiceImpl zipService;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private ProcessCompressFilesServiceImpl processComFileService;
    @Autowired
    private ProcessTestRunServiceImpl processTestRunService;
    @Autowired
    private ProcessMiscellaneousServiceImpl processMiscService;

    @Autowired
    private AutomationReportFileServiceImpl reportFileService;

    @Autowired
    private ParseResultSummaryServiceImpl parseResultSumService;

    /**
     * Unzip report zip file and backup
     *
     * @return
     */
    public Result checkZipRoot() {
        log.debug("MonitorServiceImpl.checkZipRoot() Enter");
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
            resultUnzip = processComFileService.unZip(fileName);
            if (resultUnzip.hasError()) {
                nErrorCount++;
                log.warn(String.format("Unzip report file[%s] ERROR! ec= %s, em = %s", fileName, resultUnzip.getEc(), resultUnzip.getEm()));
            } else {
                ParseResultSummary prs = resultUnzip.getCd();
                if (prs != null) {
                    // Move to Backup folder
                    Result<String> resultMoveZip = zipService.backupZipFile(prs.getFilePath());
                    if (resultMoveZip.isSuccess()) {
                        // Append backup path
                        prs.setFileBackupPath(resultMoveZip.getCd());
                    } else {
                        log.warn(String.format("Failure. ec= %s, em = %s", resultMoveZip.getEc(), resultMoveZip.getEm()));
                    }

                    List<ParseResultSummary> prsInfo = reportFileService.getParseResultSummaryByFileName(prs.getFileName());
                    if (prsInfo == null || prsInfo.size() == 0) {
                        parseResultSumService.updateParseResultSummary(prs);
                    } else {
                        log.info("Reparse the file: " + prs.getFileName() + ", old parseStatus is " + prsInfo.get(0).getParseStatus());
                        parseResultSumService.updateParseResultSummary(prs);
                    }
                }
            }
        }

        // If all is error, return the last error object
        if (listFile.size() == nErrorCount) {
            return new Result(resultUnzip);
        }
        log.debug("MonitorServiceImpl.checkZipRoot() Exit");
        return retResult;
    }

    public Result<String> parseZip() {
        log.debug("MonitorServiceImpl.parseZip() Enter");

        List<ParseResultSummary> listPRS = parseResultSumService.getPendingParseZipList();
        if (CommonUtil.isEmpty(listPRS)) {
            return new Result();
        }

        Result<String> retResult = new Result<>();
        int nSuccessCount = 0;
        int nFailCount = 0;
        Result<Boolean> resultParseFile = null;
        for (ParseResultSummary item : listPRS) {
            resultParseFile = parseJSONFile(item);
            if (resultParseFile.isSuccess()) {
                nSuccessCount++;
            } else {
                nFailCount++;
            }
        }

        String strMsg = String.format("MonitorServiceImpl.parseZip() result：\n\tFile count = %d, success: %d, failure:%d", listPRS.size(), nSuccessCount, nFailCount);
        log.info(strMsg);
        retResult.setCd(strMsg);

        log.debug("MonitorServiceImpl.parseZip() Exit");

        return retResult;
    }

    private Result<Boolean> parseJSONFile(ParseResultSummary prs) {
        Result<Boolean> retResult = new Result<>();

        try {
            String strReportJSONFile = CommonDef.getReportFileAbsolutPath(prs.getFileWorkPath());
            prs.setParseStatus(EnumDef.REPORT_FILE_PARSE_STATUS.TEST_RUN.getCode());

            // Step1 - Get JSON data
            Result<ReportJson> resultReport = JSONFileUtil.readJSON(strReportJSONFile);
            if (resultReport.hasError()) {
                updatePRSErrorInfo(prs, resultReport);
                return new Result(resultReport);
            }

            // Step2 - Update record
            ReportJson fileData = resultReport.getCd();
            prs.setReportSummary(fileData.getReportSummary().toJsonString());

            prs = ParseResultSummaryFactory.clearParseErrorInfo(prs);
            parseResultSumService.updateParseResultSummary(prs);

            // Step3 - Update Test Result
            Result<String> resultProcess = processTestRunService.processTestRun(prs, fileData);
            if (resultProcess.hasError()) {
                log.error(ErrorConst.getErrorLogMsg("processTestRunService.processTestRun()", resultProcess));
                updatePRSErrorInfo(prs, resultProcess);
                return new Result(resultProcess);
            }

            // Step4 - Remove directories
            prs.setParseStatus(EnumDef.REPORT_FILE_PARSE_STATUS.CLEAN.getCode());
            prs = ParseResultSummaryFactory.clearParseErrorInfo(prs);
            parseResultSumService.updateParseResultSummary(prs);

            Result resultCleanZipFolder = processMiscService.cleanZipFolder(prs);
            if (resultCleanZipFolder.hasError()) {
                log.warn(ErrorConst.getErrorLogMsg("processTestRunService.processTestRun()", resultCleanZipFolder));
                updatePRSErrorInfo(prs, resultCleanZipFolder);
            }

            retResult.setCd(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("MonitorServiceImpl.parseJSONFile() ERROR!!!", e);
            return commonDao.getExceptionArgsResult("invoking MonitorServiceImpl.parseJSONFile(). ErrMsg: " + e.getMessage());
        }

        return retResult;
    }

    private void updatePRSErrorInfo(ParseResultSummary prs, Result result) {
        ParseResultSummary data = ParseResultSummaryFactory.setParseErrorInfo(prs, result);
        parseResultSumService.updateParseResultSummary(data);
    }
}
