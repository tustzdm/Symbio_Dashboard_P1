package com.symbio.dashboard.util;

import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.dto.FilePathDTO;

import java.io.*;

/**
 * @ClassName - FileUtil
 * @Author - Admin
 * @Description
 * @Date - 2019/8/1 12:02
 * @Version 1.0
 */
public class FileUtil {

    public static String readFile(String Path) {
        BufferedReader reader = null;
        String str = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                str += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

    public static String getScreenShotAccessHttpDir(FilePathDTO dtoFilePathInfo) {
        // mock/image/0_1/108_2_1/200/en_US/screenshots/{filename}
        return CommonDef.FOLDER_NAME_SCREENSHOT + getTestSetWorkPath(dtoFilePathInfo);
    }

    public static String getScreenShotAbsolutePath(FilePathDTO dtoFilePathInfo) {
        // /home/shawn/vue/public/mock/image/...
        return CommonDef.DIR_HTTP_SCREENSHOT + getTestSetWorkPath(dtoFilePathInfo);
    }

    public static String getSourceFileAbsolutePath(FilePathDTO dtoFilePathInfo) {
        // /home/shawn/vue/public/mock/image/...
        return CommonDef.DIR_HTTP_SCREENSHOT + getSourceSubFolder(dtoFilePathInfo);
    }

    // Rule for Test Set Work Path
    public static String getTestSetWorkPath(FilePathDTO dtoFilePathInfo) {
        // {company}_{productId}/{releaseId}_{testSetId}_{caseType}/{testCaseId}_{zipFile}/{locale}/screenshot/
        return dtoFilePathInfo.getScreenShotPath();
    }

    public static String getSourceSubFolder(FilePathDTO dtoFilePathInfo) {
        // {company}_{productId}/{releaseId}_{testSetId}_{caseType}/{testCaseId}_{zipFile}/{locale}/source/
        return dtoFilePathInfo.getSourceFilePath();
    }

    public static String getScreenShotThumbnailAccessHttpDir(FilePathDTO dtoFilePathInfo) {
        // mock/image/0_1/108_2_1/200/en_US/screenshots/thumbnail/{filename}
        return CommonDef.FOLDER_NAME_SCREENSHOT + getTestSetWorkPath(dtoFilePathInfo) + CommonDef.THUMBNAIL;
    }

    public static String getThumbnailAbsolutePath(FilePathDTO dtoFilePathInfo) {
        // /home/shawn/vue/public/mock/image/...
        return CommonDef.DIR_HTTP_SCREENSHOT + getThumbnailSubFolder(dtoFilePathInfo);
    }

    public static String getThumbnailSubFolder(FilePathDTO dtoFilePathInfo) {
        // {company}_{productId}/{releaseId}_{testSetId}_{caseType}/{testCaseId}_{zipFile}/{locale}/thumbnail/
        return dtoFilePathInfo.getThumbnailPath();
    }

    /**
     * Combine folder and filename
     *
     * @param path
     * @param fileName
     * @return
     */
    public static String getCombineAbsolutePath(String path, String fileName) {
        if (CommonUtil.isEmpty(path) && CommonUtil.isEmpty(fileName)) {
            return "";
        }

        String strFilePath = "";
        String strFolderPath = CommonUtil.isEmpty(path) ? "" : path;
        String strFileName = CommonUtil.isEmpty(fileName) ? "" : fileName;

        if (strFolderPath.endsWith(File.separator)) {
            strFilePath = strFolderPath + strFileName;
        } else {
            if (strFolderPath.length() > 0) {
                strFilePath = strFolderPath + (strFileName.length() > 0 ? File.separator + fileName : "");
            } else {
                strFilePath = fileName;
            }
        }
        return strFilePath;
    }

    public static String getCombineHttpPath(String path, String fileName) {
        return getCombineAbsolutePath(path, fileName).replace("\\", "/");
    }
}
