package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.repository.ParseResultSummaryRep;
import com.symbio.dashboard.data.repository.RelationTestCaseMethodRep;
import com.symbio.dashboard.model.JenkinsJobHistoryMain;
import com.symbio.dashboard.model.ParseResultSummary;
import com.symbio.dashboard.model.RelationTestCaseMethod;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
