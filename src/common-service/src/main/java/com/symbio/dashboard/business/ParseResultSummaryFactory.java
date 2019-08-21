package com.symbio.dashboard.business;

import com.symbio.dashboard.model.ParseResultSummary;

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

}
