package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.repository.*;
import com.symbio.dashboard.dto.CommonListDTO;
import com.symbio.dashboard.enums.ListDataType;
import com.symbio.dashboard.enums.SystemListSetting;
import com.symbio.dashboard.model.SysListSetting;
import com.symbio.dashboard.model.TestSet;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.EntityUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName - TestSetDao
 * @Author - Admin
 * @Description
 * @Date - 2019/7/26 15:33
 * @Version 1.0
 */
@SuppressWarnings("unchecked")
@Repository
public class TestSetDao {

    private static Logger logger = LoggerFactory.getLogger(TestSetDao.class);

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CommonDao commonDao;

    @Autowired
    private TestSetRep testSetRep;
    @Autowired
    private UiInfoRep uiInfoRep;
    @Autowired
    private UserRep userRep;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SysListSettingRep sysListSettingRep;
    @Autowired
    private ReleaseRep releaseRep;
    @Autowired
    private ProductRep productRep;

    public List<Map<String, Object>> mergeStaticticsData(List<Map<String, Object>> entityMap) {
        List<Map<String, Object>> retMap = entityMap;

        try {
            // ToDo: Product - Get actual List statistics column info
            List<SysListSetting> listSetting = sysListSettingRep.getStatisticsInfo(SystemListSetting.TestSet.toString());
            if (CommonUtil.isEmpty(listSetting)) {
                return retMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Progress progress;
        Random random = new Random();
        for (Map item : retMap) {
            int total = random.nextInt(500);
            int done = random.nextInt(500);
            if (done > total) done = total;
            progress = new Progress(done, total);
            item.put("progress", progress);
        }
        return retMap;
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
    public Result getTestSetMapInfoByField(String strFields, Integer releaseId, Integer pageIndex, Integer pageSize, List<String> listUserFields) {
        Result retResult = null;

        try {
            CommonListDTO retListDTO = new CommonListDTO();
            List<Map<String, Object>> listTestSet = new ArrayList<Map<String, Object>>();

            String sql = String.format("SELECT %s FROM `test_set` WHERE release_id = %d and display = 1 ORDER by id",  strFields, releaseId);
            if (pageIndex != null && pageSize != null) {
                sql += String.format(" LIMIT %d,%d", pageIndex, pageSize);
            }

            List<Object[]> listResult = entityManager.createNativeQuery(sql).getResultList();
            ListDataType dataType = ListDataType.Map;

            if (dataType == ListDataType.Map) {
                listTestSet = EntityUtils.castMap(listResult, TestSet.class, strFields);
                List<Map<String, Object>> listReleaseInfo = mergeStaticticsData(listTestSet);
                retListDTO.setFields(CommonUtil.getListByMergeString(strFields, "progress"));
                retListDTO.setDataType(ListDataType.Map.getDataType());

                if (CommonUtil.isEmpty(listUserFields)) {
                    retListDTO.setData(listReleaseInfo);
                } else {
                    listReleaseInfo = commonDao.setUserMapInfo(listReleaseInfo, listUserFields);
                    retListDTO.setData(listReleaseInfo);
                }
            } else if (dataType == ListDataType.JSONArray) {

            }

            int nCount = testSetRep.getReleaseCount(releaseId);
            retListDTO.setTotalRecord(nCount);

            retResult = new Result(retListDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception happened while invoking TestSetDao.getTestSetMapInfoByField()", e);
            retResult = new Result("000002", e.getMessage());
        }

        return retResult;
    }

    /**
     * 得到Test Set 列表信息
     *
     * @param locale        Locale
     * @param releaseId     Release Id
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public Result getTestSetList(String locale, Integer releaseId, Integer pageIndex, Integer pageSize) {
        logger.trace("TestSetDao.getTestSetList() Enter.");
        logger.trace(String.format("Args: locale = %s, releaseId = &d, pageIndex = %d, pageSize = %d", locale, releaseId, pageIndex, pageSize));

        CommonListDTO retProdDTO = new CommonListDTO(locale, pageIndex, pageSize);
        Result retResult = new Result(retProdDTO);

        List<SysListSetting> listSetting = sysListSettingRep.getEntityInfo(SystemListSetting.TestSet.toString());
        if (CommonUtil.isEmpty(listSetting)) {
            return retResult;
        }

        // Get Columns Info
        List<SysListSetting> listColumns = sysListSettingRep.getListColumnsInfo(SystemListSetting.TestSet.toString());
        if (CommonUtil.isEmpty(listColumns)) {
            return new Result("000121", "List columns info is empty");
        } else {
            retProdDTO.setColumns(BusinessUtil.getListColumnInfo(locale, listColumns));
        }

        List<String> listAppend = new ArrayList<>();
        //listAppend.add("");
        List<String> listFields = CommonDao.getQueryFieldsAppend(listSetting, listAppend);
        if (CommonUtil.isEmpty(listFields)) {
            logger.debug("There is no field to query");
            return retResult;
        }

        List<String> listUserFields = CommonDao.getQueryUserRefFields(listSetting);

        String strFields = String.join(",", listFields);
        Result retReleaseResult = getTestSetMapInfoByField(strFields, releaseId, pageIndex, pageSize, listUserFields);
        if (retReleaseResult.hasError()) {
            retResult = retReleaseResult;
        } else {
            CommonListDTO retProduct = (CommonListDTO) retReleaseResult.getCd();
            retProdDTO.setTotalRecord(retProduct.getTotalRecord());
            retProdDTO.setFields(retProduct.getFields());
            retProduct.setDataType(retProduct.getDataType());
            retProdDTO.setData(retProduct.getData());
            retResult = new Result(retProdDTO);
        }

        logger.trace("TestSetDao.getTestSetList() Exit");
        return retResult;
    }

    @Data
    public class Progress {
        private int total;
        private int done;
        private String progress;

        public Progress(int done, int total) {
            this.done = done;
            this.total = total;
            if (total > 0) {
                this.progress = String.format("%d%%", Integer.valueOf(done * 100 / total));
            } else {
                this.progress = "";
            }
        }
    }
}
