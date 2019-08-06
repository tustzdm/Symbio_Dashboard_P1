package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.TestRunVO;
import com.symbio.dashboard.data.repository.SysListSettingRep;
import com.symbio.dashboard.data.repository.TestRunRep;
import com.symbio.dashboard.dto.TestRunUiDTO;
import com.symbio.dashboard.enums.ListDataType;
import com.symbio.dashboard.enums.SystemListSetting;
import com.symbio.dashboard.model.SysListSetting;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * @ClassName - TestRunDao
 * @Author - admin
 * @Description - TODO
 * @Date - 2019/8/5 16:53
 * @Version 1.0
 */

@Repository
@Slf4j
@SuppressWarnings("unchecked")
public class TestRunDao {

    private static Logger logger = LoggerFactory.getLogger(TestSetDao.class);

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private SysListSettingRep sysListSettingRep;
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private TestRunRep testRunRep;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ReleaseDao releaseDao;
    @Autowired
    private TestSetDao testSetDao;

    public Result<TestRunUiDTO> getList(String locale, TestRunVO testRun) {
        TestRunUiDTO testRunUiDTO = new TestRunUiDTO();
        Result retResult = new Result(testRunUiDTO);

        List<SysListSetting> listSetting = sysListSettingRep.getEntityInfoNonUi(SystemListSetting.ResultReview.toString());
        if (CommonUtil.isEmpty(listSetting)) {
            return retResult;
        } else {
            testRunUiDTO.setColumns(BusinessUtil.getListColumnInfo(locale, listSetting));
        }

        List<String> listFields = CommonDao.getQueryFieldsEx(listSetting);
        if (CommonUtil.isEmpty(listFields)) {
            logger.debug("There is no field to query");
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

    private Result getTestRunMapInfoByField(Integer testSetId, String strFields, Integer pageIndex, Integer pageSize, List<String> listUserFields) {

        log.debug("strField = " + strFields);
        Result retResult;

        try {
            TestRunUiDTO testRunUiDTO = new TestRunUiDTO();
            List<Map<String, Object>> listTestRun;

            String sql = String.format("SELECT %s FROM test_run tr " +
                    "JOIN test_case tc ON tr.testcase_id = tc.id " +
                    "WHERE tr.testset_id = %d", strFields, testSetId);
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

            long nCount = testRunRep.count();
            testRunUiDTO.setTotalRecord(new Long(nCount).intValue());

            retResult = new Result(testRunUiDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception happened while invoking ProductDao.getProductInfoByField()", e);
            retResult = new Result("000002", e.getMessage());
        }

        return retResult;
    }


    private TestRunUiDTO getData(TestRunUiDTO data, TestRunVO testRun) {
        List<Map<String, Object>> listData = null;

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
}


