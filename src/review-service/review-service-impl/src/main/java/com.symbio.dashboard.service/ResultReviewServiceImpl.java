package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.business.CommentInfoFactory;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.dao.*;
import com.symbio.dashboard.data.repository.*;
import com.symbio.dashboard.dto.ResultReviewUiDTO;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.model.BugInfo;
import com.symbio.dashboard.model.CommentInfo;
import com.symbio.dashboard.model.ScreenShot;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.DateUtil;
import com.symbio.dashboard.util.FileUtil;
import com.symbio.dashboard.util.ImageSizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName - ResultReviewServiceImpl
 * @Author - Shawn
 * @Description - Result Review Service
 * @Date - 2019/10/11
 * @Version 1.0
 */

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class ResultReviewServiceImpl implements ResultReviewService {

    @Autowired
    private CommonDao commonDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ReleaseDao releaseDao;
    @Autowired
    private TestSetDao testSetDao;
    @Autowired
    private TestRunDao testRunDao;
    @Autowired
    private TestCaseRep testCaseRep;
    @Autowired
    private TestSetRep testSetRep;
    @Autowired
    private TestRunRep testRunRep;
    @Autowired
    private ExcelImportDao excelImportDao;
    @Autowired
    private TestResultRep testResultRep;

    @Autowired
    private TestResultDao testResultDao;
    @Autowired
    private BugReportDao bugReportDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommentInfoRep commentInfoRep;

    @Override
    public Result<ResultReviewUiDTO> getList(Integer userId, String locale, Integer testRunId, String trLocale, Integer pageIndex, Integer pageSize) {
        log.trace("ResultReviewServiceImpl.getList() Enter");

        Result<ResultReviewUiDTO> retResult;
        try {
            retResult = testResultDao.getResultReviewList(locale, testRunId, trLocale, pageIndex, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            retResult = commonDao.getResultArgs(locale, "000102", "getting Result Review List");
        }

        log.trace("ResultReviewServiceImpl.getList() Exit");
        return retResult;
    }

    /**
     * Feature: Bug report
     * <p>
     * Get bug info
     *
     * @param userId       User id
     * @param locale       UI locale
     * @param screenshotId Screen shot id
     * @return
     */
    public Result getBugInfo(Integer userId, String locale, Integer id, Integer screenshotId) {
        String funcName = "ResultReviewServiceImpl.getBugInfo()";

        log.trace(String.format("%s Enter", funcName));

        Result retResult = bugReportDao.getBugUiInfo(userId, locale, id, screenshotId);
        if (retResult.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, retResult));
        }

        log.trace(String.format("%s Exit", funcName));
        return retResult;
    }

    /**
     * Save bug report data
     *
     * @param userId
     * @param locale
     * @param data
     * @return
     */
    @Override
    public Result<BugInfo> saveBugInfo(Integer userId, String locale, BugInfo data) {
        String funcName = "ResultReviewServiceImpl.saveBugInfo()";
        log.trace(String.format("%s Enter", funcName));

        // Step1:
        Integer screenShotId = data.getScreenShotId();
        Result<ScreenShot> retScreenShot = bugReportDao.getScreenShotById(locale, screenShotId);
        if (retScreenShot.hasError()) {
            return new Result(retScreenShot);
        }
        ScreenShot ss = retScreenShot.getCd();
        data.setTestResultId(ss.getTestResultId());

        // Step2:
        Result<BugInfo> retResult = bugReportDao.getUIBugInfo(userId, locale, data);
        if (retResult.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, retResult));
        }
        BugInfo bugInfo = retResult.getCd();

        Result resValidation = validationBugInfo(bugInfo);
        if (resValidation.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, resValidation));
            return new Result(resValidation);
        }

        // Step3: upload draft image
        Result<BugInfo> resSaveImage = saveDraftImage(ss, data.getFilePath(), bugInfo);
        if (resSaveImage.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, resSaveImage));
            return new Result(resSaveImage);
        }
        bugInfo = resSaveImage.getCd();

        // Step4: store the data
        Result<BugInfo> resultSaveBugInfo = bugReportDao.saveBugInfo(locale, bugInfo, ss);
        if (resultSaveBugInfo.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, resSaveImage));
            return new Result(resultSaveBugInfo);
        }

        log.trace(String.format("%s Exit", funcName));
        return resultSaveBugInfo;
    }

    public Result validationBugInfo(BugInfo data) {
        Result<Boolean> retResult = new Result<>();


        return retResult;
    }

    private Result<BugInfo> saveDraftImage(ScreenShot ss, String imgData, BugInfo bugInfo) {
        String funcName = "ResultReviewServiceImpl.saveDraftImage()";
        Result<BugInfo> retResult = new Result<>();

        try {
            String filePath = ss.getFilePath();
            String strThumbFile = ss.getThumbnailFilePath();
            String fileName = String.format("%d_%s%s.png", bugInfo.getScreenShotId(), DateUtil.getYMD(), DateUtil.getHMS());

            // Create Png file
            FileUtil.checkFilePath(filePath);
            String pngFileName = filePath + fileName;
            if (!CommonUtil.isEmpty(imgData)) {
                ImageSizer.writeCanvasBytesToPng(pngFileName, imgData);
            }

            // Create thumbnail file
            FileUtil.checkFilePath(strThumbFile);
            File ssFile = new File(pngFileName);
            File fileThumbnail = new File(strThumbFile + fileName);
            ImageSizer.resize(ssFile, fileThumbnail, 240, CommonDef.PNG);

            bugInfo.setFilePath(filePath);
            bugInfo.setFileName(fileName);
            bugInfo.setFileUrl(FileUtil.getCombineHttpPath(
                    FileUtil.getCloneHttpDir(ss.getHttpFilePath()), fileName));

            bugInfo.setThumbnailFilePath(strThumbFile);
            bugInfo.setThumbnailFileName(fileName);
            bugInfo.setThumbnailUrl(FileUtil.getCombineHttpPath(
                    FileUtil.getCloneHttpDir(ss.getThumbnailHttpPath()), fileName));

            retResult.setCd(bugInfo);
        } catch (Exception e) {
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return ErrorConst.getExceptionResult("invoking " + funcName, e);
        }

        return retResult;
    }

    @Override
    public Result<Map<String, Object>> changeScreenShotStatus(Integer userId, String locale, Integer screenShotId, String screenShotStatus) {
        String funcName = "ResultReviewServiceImpl.changeScreenShotStatus()";
        log.trace(String.format("%s Enter", funcName));

        Result<Map<String, Object>> retUpdStatus = new Result<>();

        // Step1: Get record
        Result<ScreenShot> retScreenShot = bugReportDao.getScreenShotById(locale, screenShotId);
        if (retScreenShot.hasError()) {
            return new Result(retScreenShot);
        }
        ScreenShot ss = retScreenShot.getCd();

        // Step2: check status validation
        EnumDef.SCREEN_SHOT_STATUS enumSSStatus = null;
        try {
            enumSSStatus = EnumDef.getEnumTypeByValue(EnumDef.SCREEN_SHOT_STATUS.class, screenShotStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (enumSSStatus == null) {
            log.error(commonDao.getMessage(locale, ErrorConst.EXCEPTION_ENUM_CONVERT_ERROR_NAME, "SCREEN_SHOT_STATUS", screenShotStatus));
            return commonDao.getResult(ErrorConst.EXCEPTION_ENUM_CONVERT_ERROR_NAME, "SCREEN_SHOT_STATUS", screenShotStatus);
        }

        // Step3: update record
        ss.setStatus(enumSSStatus.getCode());
        ss = testResultDao.updateScreenShot(ss);

        // Step4: return value
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("id", ss.getId());
        mapData.put("status", ss.getStatus());
        retUpdStatus.setCd(mapData);

        log.trace(String.format("%s Exit", funcName));
        return retUpdStatus;
    }

    public Result updateComment(Integer userId, String locale, Integer id, Integer screenShotId, String content) {

        // Step1: validation args
        if (CommonUtil.isEmpty(id) && CommonUtil.isEmpty(screenShotId)) {
            return commonDao.getEmptyArgsLocale(locale, "id or screenShotId");
        } else if (!CommonUtil.isEmpty(id) && CommonUtil.isEmpty(screenShotId)) {
            return commonDao.getEmptyArgsLocale(locale, "screenShotId");
        }

        // Step2: Get user info
        User userInfo = userDao.getUserById(userId);
        if (CommonUtil.isEmpty(userInfo)) {
            return commonDao.getTableNoDataArgsLocale(locale, "user", userId);
        }

        // Step3: update comment info record
        CommentInfo commentInfo = null;
        if (CommonUtil.isEmpty(id)) {
            commentInfo = CommentInfoFactory.createNewScreenShotComment(screenShotId, content, userInfo);
        } else {
            commentInfo = commentInfoRep.getOne(id);
            if (CommonUtil.isEmpty(commentInfo)) {
                return commonDao.getTableNoDataArgsLocale(locale, "comment_info", id);
            }
            commentInfo = CommentInfoFactory.updateScreenShotComment(commentInfo, screenShotId, content, userInfo);
        }
        commentInfo = commentInfoRep.saveAndFlush(commentInfo);

        return new Result(CommentInfoFactory.renderCommentData(commentInfo));
    }

    public Result removeComment(Integer userId, String locale, Integer id) {
        // Step1: validation args
        if (CommonUtil.isEmpty(id)) {
            return commonDao.getEmptyArgsLocale(locale, "id");
        }

        // Step2: Get user info
        User userInfo = userDao.getUserById(userId);
        if (CommonUtil.isEmpty(userInfo)) {
            return commonDao.getTableNoDataArgsLocale(locale, "user", userId);
        }

        CommentInfo commentInfo = commentInfoRep.getOne(id);
        if (CommonUtil.isEmpty(commentInfo)) {
            return commonDao.getTableNoDataArgsLocale(locale, "comment_info", id);
        }

        // Step3: logic remove
        commentInfo.setValidation(0);
        commentInfo.setUpdateUserId(userInfo.getId());
        commentInfo.setUpdateUserName(userInfo.getFullName());

        return new Result();
    }

    public Result getCommentInfo(Integer userId, String locale, Integer id, Integer screenShotId) {
        // Step1: validation args
        if (CommonUtil.isEmpty(id) && CommonUtil.isEmpty(screenShotId)) {
            return commonDao.getEmptyArgsLocale(locale, "id or screenShotId");
        } else if (!CommonUtil.isEmpty(id) && CommonUtil.isEmpty(screenShotId)) {
            return commonDao.getEmptyArgsLocale(locale, "screenShotId");
        }

        if (!CommonUtil.isEmpty(id)) {
            // Step2-1: Get one record
            CommentInfo commentInfo = commentInfoRep.getOne(id);
            if (CommonUtil.isEmpty(commentInfo)) {
                return commonDao.getTableNoDataArgsLocale(locale, "comment_info", id);
            } else {
                return new Result(commentInfo);
            }
        } else {
            // Step2-2: Get list record
            Result<List<Map<String, Object>>> retCommentList = new Result<>();
            List<Map<String, Object>> data = new ArrayList<>();
            List<CommentInfo> listData = commentInfoRep.getListByFkId(EnumDef.COMMENT_INFO_TYPE.SCREEN_SHOT.getCode(), screenShotId);
            if (!CommonUtil.isEmpty(listData)) {
                data = CommentInfoFactory.renderCommentListData(listData);
            }
            retCommentList.setCd(data);
            return retCommentList;
        }
    }

}
