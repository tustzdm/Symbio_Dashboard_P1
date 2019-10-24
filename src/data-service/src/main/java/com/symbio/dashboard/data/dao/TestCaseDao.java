package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.repository.SysListSettingRep;
import com.symbio.dashboard.data.repository.TestCaseRep;
import com.symbio.dashboard.data.repository.TestSetRep;
import com.symbio.dashboard.dto.TestCaseListDTO;
import com.symbio.dashboard.enums.DictionaryType;
import com.symbio.dashboard.enums.ListDataType;
import com.symbio.dashboard.enums.SystemListSetting;
import com.symbio.dashboard.model.SysListSetting;
import com.symbio.dashboard.model.TestCase;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @ClassName - TestCaseDao
 * @Author - Shawn
 * @Description
 * @Date - 2019/9/18
 * @Version 1.0
 */
@Slf4j
@Repository
@SuppressWarnings("unchecked")
public class TestCaseDao {

    @Autowired
    private SysListSettingRep sysListSettingRep;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private TestSetRep testSetRep;

    @Autowired
    private TestCaseRep testCaseRep;

    @Autowired
    private DictionaryDao dictDao;

    /**
     * 得到Test Set 列表信息
     *
     * @param locale    Locale
     * @param testSetId TestSet Id
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public Result getTestCaseList(String locale, Integer testSetId, Integer pageIndex, Integer pageSize) {
        log.trace("TestCaseDao.getTestCaseList() Enter.");
        log.debug(String.format("Args: locale = %s, testSetId = &d, pageIndex = %d, pageSize = %d", locale, testSetId, pageIndex, pageSize));

        TestCaseListDTO retProdDTO = new TestCaseListDTO(locale, pageIndex, pageSize);
        Result retResult = new Result(retProdDTO);

        List<SysListSetting> listSetting = sysListSettingRep.getEntityInfo(SystemListSetting.TestCase.toString());
        if (CommonUtil.isEmpty(listSetting)) {
            return retResult;
        }

        // Get Columns Info
        List<SysListSetting> listColumns = sysListSettingRep.getListColumnsInfo(SystemListSetting.TestCase.toString());
        if (CommonUtil.isEmpty(listColumns)) {
            return new Result("000121", "List columns info is empty");
        } else {
            retProdDTO.setColumns(BusinessUtil.getListColumnInfo(locale, listColumns));
        }

        List<String> listAppend = new ArrayList<>();
        //listAppend.add("");
        List<String> listFields = CommonDao.getQueryFieldsAppend(listSetting, listAppend);
        if (CommonUtil.isEmpty(listFields)) {
            log.debug("There is no field to query");
            return retResult;
        }

        // Get Status List info
        List<Map<String, Object>> listStatus =
                dictDao.getDicMapDataByTypeLocale(DictionaryType.TEST_CASE_TYPE_LOCALE.getType(), locale);
        retProdDTO.setListCaseType(listStatus);

        List<String> listUserFields = CommonDao.getQueryUserRefFields(listSetting);

        String strFields = String.join(",", listFields);
        Result retTestCaseResult = getTestCaseMapInfoByField(strFields, testSetId, pageIndex, pageSize, listUserFields);
        if (retTestCaseResult.hasError()) {
            retResult = retTestCaseResult;
        } else {
            TestCaseListDTO retProduct = (TestCaseListDTO) retTestCaseResult.getCd();
            retProdDTO.setTotalRecord(retProduct.getTotalRecord());
            retProdDTO.setFields(retProduct.getFields());
            retProdDTO.setDataType(retProduct.getDataType());
            retProdDTO.setData(retProduct.getData());
            retResult = new Result(retProdDTO);
        }

        log.trace("TestCaseDao.getTestCaseList() Exit");
        return retResult;
    }

    /**
     * 得到TestSet List Map数据对象
     *
     * @param strFields
     * @param pageIndex
     * @param pageSize
     * @param listUserFields
     * @return
     */
    public Result getTestCaseMapInfoByField(String strFields, Integer testSetId, Integer pageIndex, Integer pageSize, List<String> listUserFields) {
        Result retResult = null;
        String funcName = "TestCaseDao.getTestCaseMapInfoByField()";

        try {
            TestCaseListDTO retListDTO = new TestCaseListDTO();
            List<Map<String, Object>> listTestSet = new ArrayList<Map<String, Object>>();

            // Get query sql
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT ").append(strFields)
                    .append(" FROM (SELECT DISTINCT tc.* from test_case tc")
                    .append(" INNER join test_run tr on tr.testcase_id = tc.id AND tr.validation = 1")
                    .append(" WHERE tr.`testset_id` = ").append(testSetId)
                    .append(" AND tc.case_status = 0 AND tc.display = 1 AND tc.validation = 1")
                    .append(") A")
                    .append(" ORDER BY A.id");
            String sql = sb.toString();

            if (pageIndex != null && pageSize != null) {
                sql += String.format(" LIMIT %d,%d", pageIndex, pageSize);
            }

            List<Object[]> listResult = entityManager.createNativeQuery(sql).getResultList();
            ListDataType dataType = ListDataType.Map;

            if (dataType == ListDataType.Map) {
                //List<Map<String, Object>> listReleaseInfo = EntityUtils.castMap(listResult, TestCase.class, strFields);
                List<Map<String, Object>> listReleaseInfo = EntityUtils.castQuerytoMap(listResult, strFields);

                List<String> listDBFields = Arrays.asList(strFields.split(","));
                retListDTO.setFields(EntityUtils.getDTOFields(listDBFields));
                retListDTO.setDataType(ListDataType.Map.getDataType());

                if (CommonUtil.isEmpty(listUserFields)) {
                    retListDTO.setData(listReleaseInfo);
                } else {
                    listReleaseInfo = commonDao.setUserMapInfo(listReleaseInfo, listUserFields);
                    retListDTO.setData(listReleaseInfo);
                }
            } else if (dataType == ListDataType.JSONArray) {

            }

            int nCount = testCaseRep.getTestCaseCountByTestSetId(testSetId);
            retListDTO.setTotalRecord(nCount);

            retResult = new Result(retListDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            retResult = ErrorConst.getExceptionResult(funcName, e);
        }

        return retResult;
    }


    public TestCase getById(Integer id) {
        return testCaseRep.getById(id);
    }
}
