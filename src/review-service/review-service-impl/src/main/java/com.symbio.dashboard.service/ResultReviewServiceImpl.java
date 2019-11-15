package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.dao.*;
import com.symbio.dashboard.data.repository.TestCaseRep;
import com.symbio.dashboard.data.repository.TestResultRep;
import com.symbio.dashboard.data.repository.TestRunRep;
import com.symbio.dashboard.data.repository.TestSetRep;
import com.symbio.dashboard.dto.ResultReviewUiDTO;
import com.symbio.dashboard.model.BugInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public Result saveBugInfo(Integer userId, String locale, BugInfo data) {
        String funcName = "ResultReviewServiceImpl.saveBugInfo()";

        log.trace(String.format("%s Enter", funcName));

        Result<Integer> retTestResult = bugReportDao.getTestResultIdByScreenshotId(locale, data.getScreenShotId());
        if (retTestResult.hasError()) {
            return new Result(retTestResult);
        }
        data.setTestResultId(retTestResult.getCd());

        Result retResult = bugReportDao.saveBugInfo(userId, locale, data);
        if (retResult.hasError()) {
            log.error(ErrorConst.getErrorLogMsg(funcName, retResult));
        }

        log.trace(String.format("%s Exit", funcName));
        return retResult;
    }

    public Result validationBugInfo(BugInfo data) {
        Result<Boolean> retResult = new Result<>();


        return retResult;
    }
}
