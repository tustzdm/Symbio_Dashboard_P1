package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.TestRunVO;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.repository.BugInfoRep;
import com.symbio.dashboard.data.repository.ScreenShotRep;
import com.symbio.dashboard.data.repository.SysListSettingRep;
import com.symbio.dashboard.data.repository.TestRunRep;
import com.symbio.dashboard.dto.BugInfoUiDTO;
import com.symbio.dashboard.dto.BugReportUiDTO;
import com.symbio.dashboard.dto.TestRunUiDTO;
import com.symbio.dashboard.enums.ListDataType;
import com.symbio.dashboard.enums.SystemListSetting;
import com.symbio.dashboard.enums.UIInfoKey;
import com.symbio.dashboard.enums.UIInfoPage;
import com.symbio.dashboard.model.BugInfo;
import com.symbio.dashboard.model.SysListSetting;
import com.symbio.dashboard.model.TestRun;
import com.symbio.dashboard.model.UiInfo;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * @ClassName - BugReportDao
 * @Author - Shawn
 * @Description
 * @Date - 2019/11/08
 * @Version 1.0
 */

@Repository
@Slf4j
@SuppressWarnings("unchecked")
public class BugReportDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private SysListSettingRep sysListSettingRep;
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private TestRunRep testRunRep;
    @Autowired
    private ScreenShotRep screenshotRep;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ReleaseDao releaseDao;
    @Autowired
    private TestSetDao testSetDao;

    @Autowired
    private BugInfoRep buginfoRep;

    public Result getBugUiInfo(Integer userId, String locale, Integer id, Integer testRunId, Integer screenshotId, String trLocale) {
        String funcName = "BugReportDao.getBugUiInfo()";

        log.trace(funcName + " Enter");
        Result retResult = new Result();

        try {
            Integer testResultId = testRunId;

            BugInfoUiDTO bugInfoUiDTO = new BugInfoUiDTO();
            List<UiInfo> listUiInfo = new ArrayList<UiInfo>();
            listUiInfo = commonDao.getUIInfoByPage(UIInfoPage.BugReport.toString());
            bugInfoUiDTO.setLocale(locale);
            bugInfoUiDTO.setRole(7);

            List<Map<String, Object>> listMap = commonDao.getUiInfoList(locale, UIInfoPage.BugReport.toString(), listUiInfo);
            bugInfoUiDTO.setUiInfo(getBugInfoUiData(listMap));

            bugInfoUiDTO.setScreenshotId(screenshotId);
            bugInfoUiDTO.setTestRunId(testRunId);
            bugInfoUiDTO.setTestResultId(testResultId);

            BugInfo bugInfo = null;
            if (!CommonUtil.isEmpty(id) && id > 0) { // Not add
                bugInfo = buginfoRep.getOne(id);
            } else if (!(CommonUtil.isEmpty(testRunId) || CommonUtil.isEmpty(screenshotId))) {

                bugInfo = buginfoRep.getByTestResultScreenLocale(testResultId, screenshotId, trLocale);
            }

            if (CommonUtil.isEmpty(bugInfo)) {
                bugInfoUiDTO.setData(new HashMap<String, Object>());
            } else {
                // TODO: class to map
                bugInfoUiDTO.setData(new HashMap<String, Object>());
            }

            bugInfoUiDTO.setUserList(commonDao.getUserUIList(listUiInfo));
            retResult = new Result(bugInfoUiDTO);
        } catch (Exception e) {
            e.printStackTrace();
            retResult = new Result("000102", "Product UI Info");
        }

        log.trace(funcName + " Exit");
        return retResult;
    }

    private Map<String, Object> getBugInfoUiData(List<Map<String, Object>> data) {
        Map<String, Object> mapData = new HashMap<>();

        if (CommonUtil.isEmpty(data)) {
            mapData.put("Step1", new ArrayList<>());
            mapData.put("Step2", new ArrayList<>());
            return mapData;
        }

        List<Map<String, Object>> listStep1 = new ArrayList<>();
        List<Map<String, Object>> listStep2 = new ArrayList<>();

        for (Map<String, Object> mapItem : data) {
            String step = (String) mapItem.get(UIInfoKey.ConstCondition.getKey());
            mapItem.remove(UIInfoKey.ConstCondition.getKey());

            if ("Step1".equals(step)) {
                listStep1.add(mapItem);
            } else if ("Step2".equals(step)) {
                listStep2.add(mapItem);
            }
        }
        mapData.put("Step1", listStep1);
        mapData.put("Step2", listStep2);
        return mapData;
    }

    public Result getBugInfoAsListSetting(Integer userId, String locale, Integer testRunId, Integer screenshotId, String trLocale) {

        Integer testResultId = 1;

        BugReportUiDTO bugReportUiDTO = new BugReportUiDTO(locale);
        Result retResult = new Result(bugReportUiDTO);

        // Step1: Get setting
        List<SysListSetting> listSetting = sysListSettingRep.getEntityInfoNonUi(SystemListSetting.BugReport.toString());
        if (CommonUtil.isEmpty(listSetting)) {
            return retResult;
        } else {
            bugReportUiDTO.setColumns(BusinessUtil.getListColumnInfo(locale, listSetting));
        }

        // Step2:
        List<String> listFields = CommonDao.getQueryFields(SystemListSetting.BugReport, listSetting);
        if (CommonUtil.isEmpty(listFields)) {
            log.debug("There is no field to query");
            return retResult;
        }

        // Step3: check user field
        List<String> listUserFields = CommonDao.getQueryUserRefFields(listSetting);
        bugReportUiDTO.setRole(7);
        bugReportUiDTO.setLocale(locale);

        // Step4: Get bug info
        bugReportUiDTO.setFields(listFields);
        bugReportUiDTO.setDataType(ListDataType.Map.getDataType());

        bugReportUiDTO.setTestResultId(testResultId);
        bugReportUiDTO.setScreenshotId(screenshotId);
        bugReportUiDTO.setTestRunId(testRunId);

        BugInfo bugInfo = buginfoRep.getByTestResultScreenLocale(testResultId, screenshotId, trLocale);
        bugReportUiDTO.setBugInfo(bugInfo);

        String strFields = String.join(",", listFields);
        Result retTestRunResult = getBugInfoByField(testResultId, screenshotId, trLocale, strFields, listUserFields);
        if (retTestRunResult.hasError()) {
            retResult = retTestRunResult;
        } else {
            BugReportUiDTO retBugInfo = (BugReportUiDTO) retTestRunResult.getCd();
            bugReportUiDTO.setFields(EntityUtils.getDTOFields(retBugInfo.getFields()));
            bugReportUiDTO.setDataType(retBugInfo.getDataType());
            bugReportUiDTO.setData(retBugInfo.getData());
            retResult = new Result(bugReportUiDTO);
        }

        return retResult;
    }

    private Result getBugInfoByField(Integer testRunId, Integer screenshotId, String trLocale, String strFields, List<String> listUserFields) {

        String funcName = "BugReportDao.getBugInfoByField()";

        log.debug("strField = " + strFields);
        Result retResult;

        try {
            BugReportUiDTO bugInfoUiDTO = new BugReportUiDTO();
            List<Map<String, Object>> listBugInfo;

            String sql = String.format("SELECT %s FROM bug_info " +
                            " WHERE test_result_id = %d AND screen_shot_id = %d AND locale = '%s' AND validation = 1",
                    strFields, testRunId, screenshotId, trLocale);

            List<Object[]> listResult = entityManager.createNativeQuery(sql).getResultList();
            ListDataType dataType = ListDataType.Map;

            if (dataType == ListDataType.Map) {
                listBugInfo = EntityUtils.castQuerytoMap(listResult, strFields);
                bugInfoUiDTO.setFields(CommonUtil.getListByMergeString(strFields));
                bugInfoUiDTO.setDataType(ListDataType.Map.getDataType());

                if (CommonUtil.isEmpty(listUserFields)) {
                    bugInfoUiDTO.setData(listBugInfo);
                } else {
                    listBugInfo = commonDao.setUserMapInfo(listBugInfo, listUserFields);
                    bugInfoUiDTO.setData(listBugInfo);
                }
            }
            retResult = new Result(bugInfoUiDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception happened while invoking " + funcName, e);
            retResult = new Result("000002", e.getMessage());
        }

        return retResult;
    }


    //==================================================================================================================
    // No use
    //==================================================================================================================

    public Result<TestRunUiDTO> getList(String locale, TestRunVO testRun) {
        TestRunUiDTO testRunUiDTO = new TestRunUiDTO(locale, testRun.getPageIndex(), testRun.getPageSize());
        Result retResult = new Result(testRunUiDTO);

        List<SysListSetting> listSetting = sysListSettingRep.getEntityInfoNonUi(SystemListSetting.ResultReview.toString());
        if (CommonUtil.isEmpty(listSetting)) {
            return retResult;
        } else {
            testRunUiDTO.setColumns(BusinessUtil.getListColumnInfo(locale, listSetting));
        }

        List<String> listFields = CommonDao.getQueryFieldsEx(listSetting);
        if (CommonUtil.isEmpty(listFields)) {
            log.debug("There is no field to query");
            return retResult;
        }

        List<String> listUserFields = CommonDao.getQueryUserRefFields(listSetting);
        testRunUiDTO.setRole(7);
        testRunUiDTO.setLocale(testRun.getLocale());
        getData(testRunUiDTO, testRun);

        String strFields = String.join(",", listFields);
        Result retTestRunResult = getTestRunMapInfoByField(testRunUiDTO.getTestSetId(), strFields, testRun.getPageIndex(), testRun.getPageSize(), listUserFields);
        if (retTestRunResult.hasError()) {
            retResult = retTestRunResult;
        } else {
            TestRunUiDTO retTestRun = (TestRunUiDTO) retTestRunResult.getCd();
            testRunUiDTO.setTotalRecord(retTestRun.getTotalRecord());
            testRunUiDTO.setFields(EntityUtils.getDTOFields(retTestRun.getFields()));
            testRunUiDTO.setDataType(retTestRun.getDataType());
            testRunUiDTO.setData(retTestRun.getData());
            retResult = new Result(testRunUiDTO);
        }

        log.trace("TestRunDao.getList() Exit");
        return retResult;
    }

    public Result<TestRunUiDTO> demoRunResult(Result<TestRunUiDTO> data) {
        Result<TestRunUiDTO> retResult = data;
        if (data.isSuccess()) {
            TestRunUiDTO dtoTR = retResult.getCd();
            List<Map<String, Object>> listTestRun = dtoTR.getData();
            listTestRun = demoRun(listTestRun);
            dtoTR.setData(listTestRun);
        }

        return retResult;
    }

    private List<Map<String, Object>> demoRun(List<Map<String, Object>> data) {
        if (CommonUtil.isEmpty(data)) return new ArrayList<>();

        List<Map<String, Object>> retList = data;
        String[] TRunStatus = {"0", "1", "4", "5"}; // EnumDef.TEST_RUN_STATUS

        for (Map<String, Object> item : retList) {
            String strStatus = (String) item.get("status");

            if ("0".equals(strStatus)) {
                Random random = new Random();
                int idxTRStatus = random.nextInt(4);
                if (idxTRStatus == 0) idxTRStatus = 1; // Not run -> success

                item.put("status", TRunStatus[idxTRStatus]);
                if (idxTRStatus != 0) {
                    item.put("screenshotFlag", "1");
                }
            }
        }

        return retList;
    }

    private Result getTestRunMapInfoByField(Integer testSetId, String strFields, Integer pageIndex, Integer pageSize, List<String> listUserFields) {

        log.debug("strField = " + strFields);
        Result retResult;

        try {
            TestRunUiDTO testRunUiDTO = new TestRunUiDTO();
            List<Map<String, Object>> listTestRun;

            String sql = String.format("SELECT %s FROM test_run tr " +
                    " JOIN test_case tc ON tr.testcase_id = tc.id " +
                    " WHERE tr.testset_id = %d AND tr.display = 1 AND tr.validation = 1", strFields, testSetId);

            if (strFields.contains("tc.case_id") && strFields.contains("tr.locale")) {
                sql += " ORDER BY tc.case_id, tr.locale";
            } else {
                sql += " ORDER BY tr.id";
            }

            if (pageIndex != null && pageSize != null) {
                sql += String.format(" LIMIT %d,%d", pageIndex, pageSize);
            }

            List<Object[]> listResult = entityManager.createNativeQuery(sql).getResultList();
            ListDataType dataType = ListDataType.Map;

            if (dataType == ListDataType.Map) {
                listTestRun = EntityUtils.castQuerytoMap(listResult, strFields);
                testRunUiDTO.setFields(CommonUtil.getListByMergeString(strFields));
                testRunUiDTO.setDataType(ListDataType.Map.getDataType());

                if (CommonUtil.isEmpty(listUserFields)) {
                    testRunUiDTO.setData(listTestRun);
                } else {
                    //listTestRun = commonDao.setUserMapInfo(listTestRun, listUserFields);
                    testRunUiDTO.setData(listTestRun);
                }
            }

            long nCount = testRunRep.getCountByTestSetId(testSetId);
            testRunUiDTO.setTotalRecord(new Long(nCount).intValue());

            retResult = new Result(testRunUiDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception happened while invoking TestRunDao.getTestRunMapInfoByField()", e);
            retResult = new Result("000002", e.getMessage());
        }

        return retResult;
    }


    private TestRunUiDTO getData(TestRunUiDTO data, TestRunVO testRun) {
        List<Map<String, Object>> listData;

        data.setProductId(testRun.getProductId());

        if (BusinessUtil.isIdEmpty(testRun.getReleaseId())) {
            data.setReleaseId(0);
        } else {
            data.setReleaseId(testRun.getReleaseId());
        }
        // TestSetId
        if (BusinessUtil.isIdEmpty(testRun.getTestSetId())) {
            data.setTestSetId(0);
        } else {
            data.setTestSetId(testRun.getTestSetId());
        }

        Result retProduct = productDao.getNavigationList(testRun.getLocale(), null);
        if (retProduct.isSuccess()) {
            listData = (List<Map<String, Object>>) retProduct.getCd();
            if (BusinessUtil.isIdEmpty(testRun.getProductId()) && !listData.isEmpty()) {
                data.setProductId((Integer) listData.get(0).get("id"));
            }
            data.setProductList(listData);
        }

        Result retRelease = releaseDao.getNavigationList(testRun.getLocale(), testRun.getProductId(), null);
        if (retRelease.isSuccess()) {
            listData = (List<Map<String, Object>>) retRelease.getCd();
            if (BusinessUtil.isIdEmpty(testRun.getReleaseId()) && !listData.isEmpty()) {
                data.setReleaseId((Integer) listData.get(0).get("id"));
            }
            data.setReleaseList(listData);
        }
        Result retTestSet = testSetDao.getNavigationList(testRun.getUserId(), testRun.getLocale(), data.getReleaseId(), null);
        if (retRelease.isSuccess()) {
            listData = (List<Map<String, Object>>) retTestSet.getCd();
            if (BusinessUtil.isIdEmpty(testRun.getTestSetId()) && !listData.isEmpty()) {
                data.setTestSetId((Integer) listData.get(0).get("id"));
            }
            data.setTestSetList(listData);
        }

        return data;
    }

    public void removeScreenShotByTestResultId(Integer testResultId) {
        try {
            screenshotRep.deleteByTestResultId(testResultId);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn(ErrorConst.getExceptionLogMsg("TestRunDao.removeScreenShotByTestRunId()", e));
        }
    }

    public TestRun getTestRunById(Integer id) {
        return testRunRep.getById(id);
    }

    public String getLocalesInfoSQLById(Integer testRunId) {
        return "SELECT DISTINCT locale FROM test_run tr"
                + " JOIN (select id, testset_id, testcase_id from test_run) base on tr.testset_id = base.testset_id AND tr.testcase_id = base.testcase_id"
                + " WHERE base.id = " + testRunId
                + " AND tr.display = 1 AND tr.validation = 1"
                + " ORDER BY locale";
    }
}


