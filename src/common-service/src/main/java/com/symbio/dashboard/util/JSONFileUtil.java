package com.symbio.dashboard.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.model.json.ReportJson;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class JSONFileUtil {

    public static Result<ReportJson> readJSON(String fileName) {

        try {
            if (CommonUtil.isEmpty(fileName)) {
                log.warn("JSONFileUtil.readJSON() WARNING!!! fileName is empty");
                return new Result(
                        ErrorConst.FILE_UTIL_FILENAME_EMPTY, ErrorConst.FILE_UTIL_FILENAME_EMPTY_M);
            }

            File file = new File(fileName);
            if (!file.exists() || !file.isFile()) {
                log.warn("JSONFileUtil.readJSON() WARNING!!! File does not exist or file is directory");
                return new Result(
                        ErrorConst.FILE_UTIL_NOT_EXACT_FILE,
                        String.format(ErrorConst.FILE_UTIL_NOT_EXACT_FILE_M, fileName));
            }

            final ReportJson reportJson = new ObjectMapper().readValue(file, ReportJson.class);
            return new Result(reportJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("JSONFileUtil.readJSON() ERROR!!! {}", e);
            return ErrorConst.getExceptionResult("invoking JSONFileUtil.readJSON()", e);
        }
    }

    public static void main(String[] args) throws Exception {
        final ReportJson reportJson =
                new ObjectMapper().readValue(new File("C:\\tmp\\report.json"), ReportJson.class);
        System.out.println(reportJson.getReportSummary());
        System.out.println(reportJson.getReportSummary().toJsonString());
//        System.out.println(reportJson.getTestMethods().get(0).getStatus());
//        System.out.println(reportJson.getTestMethods().get(0));
    }
}
