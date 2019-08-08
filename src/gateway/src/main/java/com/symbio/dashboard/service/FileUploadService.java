package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.util.DateUtil;
import com.symbio.dashboard.util.ExcelReadUtil;
import com.symbio.dashboard.util.ImageSizer;
import com.symbio.dashboard.util.SpecialCharacterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

@Slf4j
@Service
public class FileUploadService {

    @Autowired
    private FileService fileService;

    public Result<String> saveFile(HttpServletRequest request, String strUploadFolder, String strPicFolder) {
        return saveFile(request, strUploadFolder, strPicFolder, true);
    }

    public Result<String> saveFile(HttpServletRequest request, String strUploadFolder, String strPicFolder,
                                   boolean IsThumbnail) {
        Result<String> retData = new Result<String>();
        retData.setCd("");
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            String myFileName = "";
            String strDateFolder = DateUtil.getYMD();
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        myFileName = SpecialCharacterUtils.specialCharacterRemover(file.getOriginalFilename());
                        if (myFileName.trim() != "") {
                            String path = CommonDef.IMAGE + CommonDef.LOGO + strDateFolder + "/" + myFileName;
                            fileService.upload(path, file.getInputStream(), FileExtensionEnum.IMAGE);
                            retData.setCd(path);
                        }
                    }
                }
            }
        } catch (IllegalStateException e) {
            log.error("IllegalStateException while FileUploadService.saveFile().", e);
            retData.setEc(ErrorConst.ERROR);
            retData.setEm(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IOException while FileUploadService.saveFile()", e);
            retData.setEc(ErrorConst.ERROR);
            retData.setEm(e.getMessage());
        }

        return retData;
    }

    public Result<String> saveExcel(HttpServletRequest request, String strUploadFolder) {
        Result<String> retData = new Result<String>();
        retData.setCd("");
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());

            String strFileName = "";
            int nFileCount = 0;
            String strDateFolder = DateUtil.formatToYYYYMMDD(new Date());

            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    int pre = (int) System.currentTimeMillis();
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        nFileCount++;
                        String myFileName = file.getOriginalFilename();
                        if (myFileName.trim() != "") {
                            String fileName = file.getOriginalFilename();
                            strFileName = fileName;
                            if (!isAllowExcelExt(fileName)) {
                                retData.setEc(ErrorConst.TESTCASE_IMPEXCEL_NOTSUPPORT);
                                retData.setEm(ErrorConst.TESTCASE_IMPEXCEL_NOTSUPPORT_M);
                            } else {
                                String path = strUploadFolder + strDateFolder;
                                File savedir = new File(path);
                                if (!savedir.exists())
                                    savedir.mkdirs();
                                File localFile = new File(path, fileName);
                                file.transferTo(localFile);
                                retData.setCd(localFile.getPath());
                            }
                        }
                    }
                    int finaltime = (int) System.currentTimeMillis();
                    System.out.println(finaltime - pre);
                }
            }
        } catch (IllegalStateException e) {
            log.error("IllegalStateException while FileUploadService.saveFile().", e);
            retData.setEc(ErrorConst.ERROR);
            retData.setEm(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IOException while FileUploadService.saveFile()", e);
            retData.setEc(ErrorConst.ERROR);
            retData.setEm(e.getMessage());
        }

        return retData;
    }

    public boolean isAllowIMGExt(String fileName) {
        boolean bAllow = false;
        String[] allowExt = {"jpg", "jpeg", "gif", "png", "bmp"};
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase(Locale.ENGLISH);
        for (String str : allowExt) {
            if (ext.equalsIgnoreCase(str)) {
                bAllow = true;
                break;
            }
        }
        return bAllow;
    }

    public boolean isAllowExcelExt(String fileName) {
        boolean bAllow = true;
        ExcelReadUtil excelReadUtil = new ExcelReadUtil();
        if (fileName == null || !(excelReadUtil.isExcel2003(fileName) || excelReadUtil.isExcel2007(fileName))) {
            bAllow = false;
        }
        return bAllow;
    }

    private File saveFile(File savedir, String fileName, File files) throws Exception {
        if (!savedir.exists())
            savedir.mkdirs();
        File file = new File(savedir, fileName);
        FileOutputStream fileoutstream = new FileOutputStream(file);
        FileInputStream input = new FileInputStream(files);
        byte[] bytes = new byte[1024];
        int length = 0;
        while ((length = input.read(bytes)) != -1) {
            fileoutstream.write(bytes, 0, length);
        }
        fileoutstream.flush();
        fileoutstream.close();
        input.close();
        log.info("file [" + savedir.getName() + "/" + fileName + "] upload success ");

        log.info("screenshot's http link:\t" + CommonDef.PIC_HTTP_SCREENSHOT + savedir.getName() + "/" + fileName);
        log.info("screenshot's thumbnail http link:\t" + CommonDef.PIC_HTTP_SCREENSHOT + savedir.getName()
                + "/thumbnail/" + fileName);

        return file;
    }

    private File saveProductImageFile(String strPath, File imagefile, String picName, String pext) throws Exception {
        String ext = pext.toLowerCase(Locale.ENGLISH);
        String realpathdir = null;
        String realpathdir140 = null;
        realpathdir = strPath + DateUtil.getYMD() + "/";
        realpathdir140 = realpathdir + "/thumbnail/";
        File savedir = new File(realpathdir);
        if (!savedir.exists())
            savedir.mkdirs();
        File file = saveFile(savedir, picName + "." + ext, imagefile);
        File savedir140 = new File(realpathdir140);
        if (!savedir140.exists())
            savedir140.mkdirs();
        File file140 = new File(realpathdir140, picName + "." + ext);
        ImageSizer.resize(file, file140, 140, ext);
        return file;
    }
}
