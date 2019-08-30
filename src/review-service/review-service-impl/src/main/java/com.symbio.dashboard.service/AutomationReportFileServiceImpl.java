package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.constant.ProjectConst;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.repository.ParseResultSummaryRep;
import com.symbio.dashboard.data.repository.RelationTestCaseMethodRep;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.JenkinsJobHistoryMain;
import com.symbio.dashboard.model.ParseResultSummary;
import com.symbio.dashboard.model.RelationTestCaseMethod;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName - AutomationReportFileServiceImpl
 * @Author - Shawn
 * @Description - Automation Reprot file relevant Service
 * @Date - 2019/8/23
 * @Version 1.0
 */

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class AutomationReportFileServiceImpl implements AutomationReportFileService {
    @Autowired
    private CommonDao commonDao;

    @Autowired
    private ParseResultSummaryRep parseResultSumRep;

    @Autowired
    private RelationTestCaseMethodRep relationTestCaseMethodRep;

    @Override
    public ParseResultSummary getParseResultSummaryByFileName(String fileName) {
        return parseResultSumRep.getByFileName(fileName);
    }

    /**
     * Project Config:
     * Jenkins.Automation.ReportFile.Format: {TestSetId}-{buildId}-{JobName}.zip
     *
     * @param fileName
     * @return
     */
    @Override
    public Result<JenkinsJobHistoryMain> getJobInfoFromZipName(String fileName) {
        Result<JenkinsJobHistoryMain> retResult = new Result<>();

        if (CommonUtil.isEmpty(fileName)) {
            return commonDao.getResult("002002");
        }

        String[] arrJobInfo = fileName.split(CommonDef.HORIZONTALLINE);
        if (arrJobInfo.length != 3) {
            return commonDao.getResult("500105", fileName);
        }

        JenkinsJobHistoryMain retJJHMain = new JenkinsJobHistoryMain();
        retJJHMain.setTestSetId(Integer.parseInt(arrJobInfo[0]));
        retJJHMain.setBuildId(Integer.parseInt(arrJobInfo[1]));
        retJJHMain.setJobname(arrJobInfo[2]);
        retResult.setCd(retJJHMain);

        return retResult;
    }

    @Override
    public RelationTestCaseMethod getRelationTestCaseMethodByInfo(String packageInfo, String className, String methodName) {
        return relationTestCaseMethodRep.getByPackageInfoAndClassNameAndMethodName(packageInfo, className, methodName);
    }

    @Override
    public Result<Map<String, Object>> getMapByReportFileName(String fileName) {

        Result<Map<String, Object>> retResult = new Result<>();
        String funcName = "AutomationReportFileServiceImpl.getMapByReportFileName()";

        Map<String, Object> retMap = new HashMap<>();
        Integer testCaseId = 0;
        Integer testRunId = 0;
        String locale = Locales.EN_US.toString();

        retMap.put("testRunId", testRunId);
        retMap.put("testCaseId", testCaseId);
        retMap.put("locale", locale);

        if (CommonUtil.isEmpty(fileName)) {
            return new Result(retMap);
        }

        try {
            String strReportFileFormat = commonDao.getConfigValueByKey(ProjectConst.JENKINS_AUTOMATION_REPORT_FORMAT);
            String[] arrSettingInfo = strReportFileFormat.split(CommonDef.HORIZONTALLINE);

            String[] arrJobInfo = fileName.split(CommonDef.HORIZONTALLINE);
            if (arrJobInfo.length != arrSettingInfo.length) {
                return commonDao.getResult("500105", fileName);
            }

            // {TestRunId}-{buildId}-{JobName}.zip
            if (arrSettingInfo[0].toLowerCase().contains("testrunid")) {
                retMap.put("testRunId", Integer.parseInt(arrJobInfo[0]));
            }

            // {TestSetId}-{locale}-{buildId}-{JobName}.zip
            if (arrSettingInfo[0].toLowerCase().contains("testsetid")) {
                retMap.put("testCaseId", Integer.parseInt(arrJobInfo[0]));

                if (arrSettingInfo[1].toLowerCase().contains("locale")) {
                    retMap.put("locale", arrJobInfo[1]);
                }
            }

            retResult.setCd(retMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return ErrorConst.getExceptionResult(funcName, e);
        }

        return retResult;

    }
}
