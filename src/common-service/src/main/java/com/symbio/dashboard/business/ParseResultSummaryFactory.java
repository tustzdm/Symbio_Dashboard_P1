package com.symbio.dashboard.business;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.ParseResultSummary;
import com.symbio.dashboard.util.BusinessUtil;

import java.util.Date;
import java.util.Map;

public class ParseResultSummaryFactory {

    public static ParseResultSummary createNewParseResultSummaryByMap(Map<String, Object> mapData) {
        ParseResultSummary prs = new ParseResultSummary();

        try {
            prs.setFilePath((String) mapData.get("filePath"));
            prs.setFileName((String) mapData.get("fileName"));
            prs.setFileSize((Integer) mapData.get("fileSize"));
            prs.setFileWorkPath((String) mapData.get("fileWorkPath"));
            prs.setParseStatus((Integer) mapData.get("parseStatus"));
            prs.setParseCount(1);
            prs.setReceiveTime(new Date());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return prs;
    }

    public static ParseResultSummary setParseErrorInfo(ParseResultSummary data, Result result) {
        ParseResultSummary retData = data;

        Integer parseCount = data.getParseCount();
        if (BusinessUtil.isIdEmpty(parseCount)) {
            parseCount = 0;
        }
        retData.setParseCount(parseCount++);

        if (result.hasError()) {
            retData.setParseErrorCode(result.getEc());
            retData.setParseErrorMsg(result.getEm());
        }

        return retData;
    }

    public static ParseResultSummary clearParseErrorInfo(ParseResultSummary data) {
        ParseResultSummary retData = data;

        Integer parseCount = data.getParseCount();
        if (BusinessUtil.isIdEmpty(parseCount)) {
            parseCount = 0;
        }
        retData.setParseCount(parseCount++);

        retData.setParseErrorCode("");
        retData.setParseErrorMsg("");

        return retData;
    }
}
