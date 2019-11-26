package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.TestRunVO;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.repository.ScreenShotRep;
import com.symbio.dashboard.data.repository.SysListSettingRep;
import com.symbio.dashboard.data.repository.TestRunRep;
import com.symbio.dashboard.dto.TestRunUiDTO;
import com.symbio.dashboard.enums.ListDataType;
import com.symbio.dashboard.enums.SystemListSetting;
import com.symbio.dashboard.model.SysListSetting;
import com.symbio.dashboard.model.TestRun;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName - TestRunDao
 * @Author - admin
 * @Description
 * @Date - 2019/8/5 16:53
 * @Version 1.0
 */

@Repository
@Slf4j
@SuppressWarnings("unchecked")
public class TestRunDao {

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

        Result retProduct = productDao.getNavigationListByUserRole(testRun.getUserId(), testRun.getLocale(), null);
        if (retProduct.isSuccess()) {
            listData = commonDao.decodeResultListData(retProduct);
            if (BusinessUtil.isIdEmpty(testRun.getProductId()) && !listData.isEmpty()) {
                Integer productId = (Integer) listData.get(0).get("id");
                data.setProductId(productId);
            }
            data.setProductList(listData);
        }

        Result retRelease = releaseDao.getNavigationList(testRun.getUserId(), testRun.getLocale(), data.getProductId(), null);
        if (retRelease.isSuccess()) {
            listData = commonDao.decodeResultListData(retRelease);
            if (BusinessUtil.isIdEmpty(testRun.getReleaseId()) && !listData.isEmpty()) {
                Integer releaseId = (Integer) listData.get(0).get("id");
                data.setReleaseId(releaseId);
            }
            data.setReleaseList(listData);
        }
        Result retTestSet = testSetDao.getNavigationList(testRun.getUserId(), testRun.getLocale(), data.getReleaseId(), null);
        if (retTestSet.isSuccess()) {
            listData = commonDao.decodeResultListData(retTestSet);
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


