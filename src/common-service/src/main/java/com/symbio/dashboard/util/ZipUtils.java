package com.symbio.dashboard.util;

import com.symbio.dashboard.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.util.Enumeration;
import java.util.Locale;
import java.util.zip.ZipException;

@Slf4j
public class ZipUtils {

    private static final String CHINESE_CHARSET = "GBK";

    private static final int CACHE_SIZE = 1024;

    public static Result<Integer> unZip(String zipFilePath, String destDir, String fileName) {
        log.info(
                " unZip <<<	zipFilePath = "
                        + zipFilePath
                        + " <<<  destDir = "
                        + destDir
                        + " <<< fileName = "
                        + fileName);
        Result<Integer> retResult = new Result<Integer>();
        if (isAllowExt(zipFilePath)) {
            String baseName = FilenameUtils.getBaseName(fileName);
            ZipFile zipFile = null;
            try {
                zipFile = new ZipFile(zipFilePath, CHINESE_CHARSET);
                Enumeration<?> emu = zipFile.getEntries();
                BufferedInputStream bis;
                FileOutputStream fos;
                BufferedOutputStream bos;
                File file, parentFile;
                ZipEntry entry;
                byte[] cache = new byte[CACHE_SIZE];
                while (emu.hasMoreElements()) {
                    entry = (ZipEntry) emu.nextElement();
                    if (entry.isDirectory()) {
                        new File(destDir + baseName + File.separator + entry.getName()).mkdirs();
                        continue;
                    }
                    bis = new BufferedInputStream(zipFile.getInputStream(entry));
                    file = new File(destDir + baseName + File.separator + entry.getName());
                    parentFile = file.getParentFile();
                    if (parentFile != null && (!parentFile.exists())) {
                        parentFile.mkdirs();
                    }
                    fos = new FileOutputStream(file);
                    bos = new BufferedOutputStream(fos, CACHE_SIZE);
                    int nRead = 0;
                    while ((nRead = bis.read(cache, 0, CACHE_SIZE)) != -1) {
                        fos.write(cache, 0, nRead);
                    }
                    bos.flush();
                    bos.close();
                    fos.close();
                    bis.close();
                }

                log.info(" unZip success !!");
            } catch (ZipException e) {
                log.info("ZipUtils unZip ZipException " + e);
                retResult = new Result<>("500101", "Exception happened while parsing TestRun zip file");
            } catch (FileNotFoundException e) {
                log.info("ZipUtils unZip FileNotFoundException " + e);
                retResult =
                        new Result<>("500102", "File Exception happened while parsing TestRun zip file");
            } catch (IOException e) {
                log.info("ZipUtils unZip IOException " + e);
                retResult = new Result<>("", "IO Exception happened while parsing TestRun zip file");
            } finally {
                if (null != zipFile) {
                    try {
                        zipFile.close();
                        retResult.setCd(1);
                    } catch (IOException e) {
                        log.info("ZipUtils unZip exception " + e);
                        retResult =
                                new Result<>("000004", "Exception happened while unzipping TestRun Zip data");
                    }
                }
            }
        }
        return retResult;
    }

    private static boolean isAllowExt(String fileName) {
        boolean bRet = false;
        String ext =
                fileName
                        .substring(fileName.lastIndexOf(".") + 1, fileName.length())
                        .toLowerCase(Locale.ENGLISH);
        if ("zip".equalsIgnoreCase(ext)) {
            bRet = true;
        }
        return bRet;
    }
}
