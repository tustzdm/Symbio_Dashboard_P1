package com.symbio.dashboard.business;

import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.dto.FilePathDTO;
import com.symbio.dashboard.model.ScreenShot;
import com.symbio.dashboard.model.json.Logs;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.FileUtil;
import com.symbio.dashboard.util.WebUtil;

import java.io.File;
import java.util.*;

public class ScreenShotFactory {

    public static ScreenShot createNew(String workPath, Integer testResultId, Integer index, Logs log, FilePathDTO dtoFilePathInfo) {
        ScreenShot ss = new ScreenShot();

        ss.setTestResultId(testResultId);
        ss.setStatus(0);
        ss.setStep(index);
        ss.setSource(log.getSource());
        ss.setImage(log.getImage());
        ss.setMessage(log.getMessage());

        if (!CommonUtil.isEmpty(log.getImage())) {
            // FileName in work path
            ss.setOriginalName(FileUtil.getCombineAbsolutePath(workPath, log.getImage()));

            String strSSFileName = getReportJsonFileName(ss.getImage());
            ss.setHttpFilePath(
                    FileUtil.getCombineHttpPath(
                            FileUtil.getScreenShotAccessHttpDir(dtoFilePathInfo), strSSFileName));
            ss.setFilePath(FileUtil.getScreenShotAbsolutePath(dtoFilePathInfo));
            ss.setFileName(strSSFileName);
            ss.setFileSize(0);

            ss.setThumbnailHttpPath(FileUtil.getCombineHttpPath(
                    FileUtil.getScreenShotThumbnailAccessHttpDir(dtoFilePathInfo), strSSFileName));
            ss.setThumbnailFilePath(FileUtil.getThumbnailAbsolutePath(dtoFilePathInfo));
            ss.setThumbnailFileName(strSSFileName);
            ss.setThumbnailFileSize(0);
        }

        if (!CommonUtil.isEmpty(log.getSource())) {
            String strSrcFileName = getReportJsonFileName(log.getSource());
            ss.setSourceFileOriginalName(FileUtil.getCombineAbsolutePath(workPath, log.getSource()));
            //ss.setSourceFilePath(FileUtil.getCombineAbsolutePath(FileUtil.getSourceFileAbsolutePath(dtoFilePathInfo), strSrcFileName));
            ss.setSourceFilePath(FileUtil.getSourceFileAbsolutePath(dtoFilePathInfo));
            ss.setSourceFileName(strSrcFileName);
            ss.setSourceFileSize(0);
        }

        ss.setCreateTime(new Date());

        return ss;
    }

    private static String getReportJsonFileName(String imageInfo) {
        // "screenshots/66e11fe3-bddc-43ec-a667-47e0e4db5be7.png"
        String[] arrFileInfo = imageInfo.split("/");
        return (arrFileInfo.length == 2) ? arrFileInfo[1] : imageInfo;
    }

    public static String getThumbnailFileName(String srcFileName) {
        String strFileName = "";
        String[] arrFolderInfo = srcFileName.split(CommonDef.SCREENSHOTS);
        if (arrFolderInfo.length == 2) {
            strFileName = arrFolderInfo[0];
        }

        strFileName += CommonDef.SCREENSHOTS + CommonDef.THUMBNAIL + File.separator;
        File file = new File(strFileName);
        if (!file.exists()) {
            file.mkdir();
        }

        strFileName = srcFileName.replace(CommonDef.SCREENSHOTS,
                String.format("%s%s%s", CommonDef.SCREENSHOTS, CommonDef.THUMBNAIL, File.separator));

        return strFileName;
    }

    /**
     * Get ScreenShot Info for UI
     *
     * @param data
     * @return
     */
    public static List<Map<String, Object>> getScreenshotUIList(List<ScreenShot> data) {
        if (CommonUtil.isEmpty(data)) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> retListSS = new ArrayList<>();
        Map<String, Object> mapScreenShot = new HashMap<>();
        for (ScreenShot item : data) {
            mapScreenShot = new HashMap<>();

            mapScreenShot.put("id", item.getId());
            mapScreenShot.put("status", item.getStatus());
            mapScreenShot.put("step", item.getStep());
            mapScreenShot.put("source", WebUtil.getItemValue(item.getSource()));
            mapScreenShot.put("message", WebUtil.getItemValue(item.getMessage()));

            mapScreenShot.put("fileName", WebUtil.getItemValue(item.getFileName()));
            mapScreenShot.put("httpFilePath", WebUtil.getItemValue(item.getHttpFilePath()));
            mapScreenShot.put("thumbnailHttpPath", WebUtil.getItemValue(item.getThumbnailHttpPath()));

            mapScreenShot.put("description", WebUtil.getItemValue(item.getDescription()));
            mapScreenShot.put("jiraTicket", WebUtil.getItemValue(item.getJiraTicket()));
            //mapScreenShot.put("message", WebUtil.getItemValue(item));

            retListSS.add(mapScreenShot);
        }

        return retListSS;
    }


}
