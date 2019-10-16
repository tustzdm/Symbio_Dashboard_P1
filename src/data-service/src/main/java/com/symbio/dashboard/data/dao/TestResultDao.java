package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ProjectConst;
import com.symbio.dashboard.data.repository.*;
import com.symbio.dashboard.dto.ResultReviewUiDTO;
import com.symbio.dashboard.enums.*;
import com.symbio.dashboard.model.*;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName - TestResultDao
 * @Author - Shawn
 * @Description
 * @Date - 2019/8/23
 * @Version 1.0
 */

@Repository
@Slf4j
@SuppressWarnings("unchecked")
public class TestResultDao {

    @Autowired
    private CommonDao commonDao;
    @Autowired
    private DictionaryDao dictDao;
    @Autowired
    private TestRunRep testRunRep;
    @Autowired
    private TestRunDao testRunDao;

    @Autowired
    private TestResultRep testResultRep;
    @Autowired
    private ScreenShotRep screenShotRep;
    @Autowired
    private SysListSettingRep sysListSettingRep;
    @Autowired
    private LocalesInfoRep localesInfoRep;

    @PersistenceContext
    private EntityManager entityManager;

    private final String RESULT_REVIEW_COL_TARGET_SCREEN = "target_screen";

    private final String RESULT_REVIEW_FIELD_SOURCE_LOCALE = "sourceLocale";
    private final String RESULT_REVIEW_FIELD_TARGET_LOCALE = "targetLocale";

    private final String RESULT_REVIEW_ITEM_ID = "id";
    private final String RESULT_REVIEW_ITEM_STEP = "step";
    private final String RESULT_REVIEW_ITEM_LOCALE = "locale";
    private final String RESULT_REVIEW_ITEM_HTTPFILEPATH = "httpFilePath";
    private final String RESULT_REVIEW_ITEM_THUMBNAILHTTPPATH = "thumbnailHttpPath";
    private static String VUE_DOMAIN_ADDRESS = null;

    private String getVUEDomainHttp() {
        if (VUE_DOMAIN_ADDRESS == null) {
            VUE_DOMAIN_ADDRESS = commonDao.getConfigValueByKey(ProjectConst.VUE_DOMAIN_NAME); //  "https://vue.symbio.com.cn/";
        }
        return VUE_DOMAIN_ADDRESS;
    }

    public TestResult updateTestResult(TestResult tr) {
        return testResultRep.saveAndFlush(tr);
    }

    public TestResult getTestResultById(Integer id) {
        return testResultRep.getOne(id);
    }

    public TestResult getTestResultByTestRunId(Integer testRunId) {
        return testResultRep.getByTestRunId(testRunId);
    }

    public ScreenShot updateScreenShot(ScreenShot ss) {
        return screenShotRep.saveAndFlush(ss);
    }

    public List<ScreenShot> getScreenShotsByTestResultId(Integer testResultId) {
        List<ScreenShot> listScreenShots = screenShotRep.getByTestResultId(testResultId);
        if (CommonUtil.isEmpty(listScreenShots)) {
            listScreenShots = new ArrayList<>();
        }
        return listScreenShots;
    }

    /**
     * Get screenshot result for image diff for a certain test result
     *
     * @param locale
     * @param testRunId
     * @param trLocale
     * @return
     */
    public Result<ResultReviewUiDTO> getResultReviewList(String locale, Integer testRunId, String trLocale, Integer pageIndex, Integer pageSize) {
        log.trace("TestResultDao.getResultReviewList() Enter");

        ResultReviewUiDTO resultReviewDTO = new ResultReviewUiDTO(locale, pageIndex, pageSize);
        Result retResult = new Result(resultReviewDTO);

        // Get column info
        List<SysListSetting> listSetting = sysListSettingRep.getEntityInfoNonUi(SystemListSetting.ImageCompare.toString());
        if (CommonUtil.isEmpty(listSetting)) {
            return retResult;
        } else {
            List<Map<String, Object>> listColumns = BusinessUtil.getListColumnInfo(locale, listSetting);
            resultReviewDTO.setColumns(getAppropriateColumnInfo(SystemListSetting.ImageCompare, trLocale, listColumns));
        }

        List<String> listFields = CommonDao.getQueryFields(SystemListSetting.ImageCompare, listSetting);
        if (CommonUtil.isEmpty(listFields)) {
            log.debug("There is no field to query");
            return retResult;
        }

        resultReviewDTO.setRole(7);
        resultReviewDTO.setSourceLocale(Locales.EN_US.toString());
        resultReviewDTO.setTargetLocale(trLocale);
        resultReviewDTO.setTestRunId(testRunId);

        // Get TestSetId and caseId
        TestRun tr = testRunRep.getValidTestRunByNeighbourIdAndLocale(testRunId, trLocale);
        if (CommonUtil.isEmpty(tr)) {
            log.error("Could not find TestRun recode by neighbour id({}), its locale({})", testRunId, trLocale);
            return commonDao.getTableNoDataArgsLocale(locale, "Test_Run", testRunId);
        }

        // Get Status List info
        List<Map<String, Object>> listStatus =
                dictDao.getDicMapDataByTypeLocale(DictionaryType.SCREEN_SHOT_STATUS_LOCALE.getType(), locale);
        resultReviewDTO.setListStatus(listStatus);

        // Get Locale List info
        List<Map<String, Object>> listLocales = getTestRunLocales(testRunId, locale);
        resultReviewDTO.setListLocales(listLocales);

        // Fetch data from db
        String strFields = String.join(",", listFields);
        Result resultFetchSS = getReviewScreenShotMapInfoByField(tr, resultReviewDTO, strFields);
        if (resultFetchSS.hasError()) {
            retResult = resultFetchSS;
        } else {
            retResult = new Result(resultReviewDTO);
        }

        log.trace("TestResultDao.getResultReviewList() Exit");
        return retResult;
    }

    /**
     * Get Screenshot Review info according to testRunId and Locale
     *
     * @param testRun   Provide testSetId and testCaseId
     * @param dtoRR     Provide pageIndex and pageSize and query locale ifo
     * @param strFields Query fields info
     * @return
     */
    private Result getReviewScreenShotMapInfoByField(TestRun testRun, ResultReviewUiDTO dtoRR, String strFields) {
        log.debug("strField = " + strFields);
        Result retResult;

        try {
            ResultReviewUiDTO resultreviewUiDTO = dtoRR;
            List<Map<String, Object>> listScreenShot;

            String sql = getImageCompareQuerySql(testRun, resultreviewUiDTO, strFields);
            List<Object[]> listResult = entityManager.createNativeQuery(sql).getResultList();
            ListDataType dataType = ListDataType.Map;

            if (dataType == ListDataType.Map) {
                listScreenShot = EntityUtils.castQuerytoMap(listResult, strFields);

                resultreviewUiDTO.setFields(CommonUtil.getListByMergeString(SystemListSetting.ImageCompare, strFields));
                resultreviewUiDTO.setDataType(ListDataType.Map.getDataType());

                // Merge data if need
                listScreenShot = mergeImageCompareData(listScreenShot, resultreviewUiDTO);

                int nCount = 0;
                if (!CommonUtil.isEmpty(listScreenShot)) {
                    nCount = listScreenShot.size();
                    resultreviewUiDTO.setData(listScreenShot);
                } else {
                    resultreviewUiDTO.setData(listResult);
                }
                resultreviewUiDTO.setTotalRecord(nCount);
            }
            retResult = new Result(resultreviewUiDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception happened while invoking TestResultDao.getReviewScreenShotMapInfoByField()", e);
            retResult = new Result("000002", e.getMessage());
        }

        return retResult;
    }

    /**
     * Get sql clause
     *
     * @param testRun
     * @param dtoRR
     * @param strFields
     * @return
     */
    private String getImageCompareQuerySql(TestRun testRun, ResultReviewUiDTO dtoRR, String strFields) {
        String sql = null;
        StringBuffer sb = new StringBuffer();

        String strBaseLocale = dtoRR.getSourceLocale();
        String queryLocale = dtoRR.getTargetLocale();
        Integer pageIndex = dtoRR.getPageIndex();
        Integer pageSize = dtoRR.getPageSize();

        Integer testSetId = testRun.getTestsetId();
        Integer testCaseId = testRun.getTestcaseId();

        sb.append("SELECT ").append(strFields).append(" FROM screen_shot ss")
                .append(" LEFT JOIN test_result tresult ON ss.testResultId = tresult.id")
                .append(" LEFT JOIN test_run tr ON tr.id = tresult.test_run_id ")
                .append(" WHERE tr.testset_id = ").append(testSetId)
                .append(" AND tr.testcase_id = ").append(testCaseId).append(" AND ss.validation = 1");

        if (strBaseLocale.equalsIgnoreCase(queryLocale)) {
            log.info("sql for BaseLocale {}：", strBaseLocale);

            // Only one locale
            sb.append(" AND tr.locale = '" + queryLocale + "'");
            if (pageIndex != null && pageSize != null) {
                sb.append(String.format(" LIMIT %d, %d", pageIndex, pageSize));
            }

            sql = sb.toString();
        } else {
            log.info("sql for {} and {} ==>", strBaseLocale, queryLocale);

            // Diff from base locale
            sb.append(" AND tr.locale = '%s'");
            if (pageIndex != null && pageSize != null) {
                sb.append(String.format(" LIMIT %d, %d", pageIndex, pageSize));
            }

            String sqlBaseLocale = String.format(sb.toString(), strBaseLocale);
            String sqlQueryLocale = String.format(sb.toString(), queryLocale);

            sql = String.format("(%s) UNION (%s)", sqlBaseLocale, sqlQueryLocale);
        }

        log.info(sql);

        return sql;
    }

    private List<Map<String, Object>> getAppropriateColumnInfo(SystemListSetting listType, String trLocale, List<Map<String, Object>> data) {
        List<Map<String, Object>> retList = data;

        try {
            switch (listType) {
                default:
                    break;
                case ImageCompare:
                    String strBaseLocale = Locales.EN_US.toString();
                    for (Map<String, Object> item : data) {
                        if (RESULT_REVIEW_COL_TARGET_SCREEN.equalsIgnoreCase(item.get(ListColumns.KEY.getKey()).toString())) {
                            String strLabel = item.get(ListColumns.LABEL.getKey()).toString();
                            item.put(ListColumns.LABEL.getKey(), String.format(strLabel, strBaseLocale.equals(trLocale) ? "" : trLocale));
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("TestResultDao.getAppropriateColumnInfo() !!!ERROR!!!", e);
        }

        return retList;
    }

    private List<Map<String, Object>> mergeImageCompareData(List<Map<String, Object>> data, ResultReviewUiDTO dto) {

        String strBaseLocale = dto.getSourceLocale();
        String strTargetLocale = dto.getTargetLocale();
        List<Map<String, Object>> retListData = new ArrayList<>();

        try {
            List<Map<String, Object>> listBase = null;
            List<Map<String, Object>> listTarget = null;

            if (strTargetLocale.equalsIgnoreCase(strBaseLocale)) {
                listBase = filterListLocaleData(data, strBaseLocale);
                listTarget = new ArrayList<>();

                if (CommonUtil.isEmpty(listBase)) {
                    return new ArrayList<>();
                }
                retListData = getListData(listBase, null, strBaseLocale, null);

            } else {
                listBase = filterListLocaleData(data, strBaseLocale);
                listTarget = filterListLocaleData(data, strTargetLocale);

                if (CommonUtil.isEmpty(listBase)) {
                    if (CommonUtil.isEmpty(listTarget)) {
                        return new ArrayList<>();
                    } else {
                        retListData = getListData(null, listTarget, null, strTargetLocale);
                    }
                } else {
                    retListData = getListData(listBase, listTarget, strBaseLocale, strTargetLocale);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retListData;
    }

    private List<Map<String, Object>> filterListLocaleData(List<Map<String, Object>> data, String locale) {
        List<Map<String, Object>> retList = new ArrayList<>();

        String currLocale = "";
        for (Map<String, Object> item : data) {
            if (item.containsKey("locale")) {
                currLocale = item.get("locale").toString();
                if (currLocale.equals(locale)) {
                    retList.add(item);
                }
            }
        }

        return retList;
    }

    private List<Map<String, Object>> getListData(List<Map<String, Object>> listBase, List<Map<String, Object>> listTarget, String localeBase, String localeTarget) {
        List<Map<String, Object>> retList = new ArrayList<>();

        List<Map<String, Object>> listMapData = null;
        if (listBase == null) {
            listMapData = listTarget;
        } else {
            listMapData = listBase;
        }

        Map<String, Object> mapData = null;
        Map<String, Object> mapSubData = null;
        String url, thumbnail;
        Integer ssId, step;
        for (Map<String, Object> item : listMapData) {
            mapData = item;

            ssId = Integer.parseInt(CommonUtil.getMapKey(item, RESULT_REVIEW_ITEM_ID));
            step = Integer.parseInt(CommonUtil.getMapKey(item, RESULT_REVIEW_ITEM_STEP));
            url = CommonUtil.getMapKey(item, RESULT_REVIEW_ITEM_HTTPFILEPATH);
            thumbnail = CommonUtil.getMapKey(item, RESULT_REVIEW_ITEM_THUMBNAILHTTPPATH);

            mapData.remove(RESULT_REVIEW_ITEM_ID);
            mapData.remove(RESULT_REVIEW_ITEM_HTTPFILEPATH);
            mapData.remove(RESULT_REVIEW_ITEM_THUMBNAILHTTPPATH);
            mapData.remove(RESULT_REVIEW_ITEM_LOCALE);

            if (listBase == null) {
                mapData.put(RESULT_REVIEW_FIELD_SOURCE_LOCALE, getDummyLocaleMapData());
                mapData.put(RESULT_REVIEW_FIELD_TARGET_LOCALE, getReviewLocaleMapData(ssId, url, thumbnail));
            } else if (listTarget == null) {
                mapData.put(RESULT_REVIEW_FIELD_SOURCE_LOCALE, getReviewLocaleMapData(ssId, url, thumbnail));
                mapData.put(RESULT_REVIEW_FIELD_TARGET_LOCALE, getDummyLocaleMapData());
            } else {
                mapData.put(RESULT_REVIEW_FIELD_SOURCE_LOCALE, getReviewLocaleMapData(ssId, url, thumbnail));
                mapData.put(RESULT_REVIEW_FIELD_TARGET_LOCALE, getTargetLocaleMapData(listTarget, step));
            }
            retList.add(mapData);
        }

        return retList;
    }

    private Map<String, Object> getTargetLocaleMapData(List<Map<String, Object>> list, Integer step) {
        Integer id = 0;
        String url = "", thumbnail = "";

        try {
            Integer currStep = 0;
            for (Map<String, Object> item : list) {
                currStep = Integer.parseInt(CommonUtil.getMapKey(item, RESULT_REVIEW_ITEM_STEP));
                if (currStep == step) {
                    id = Integer.parseInt(CommonUtil.getMapKey(item, RESULT_REVIEW_ITEM_ID));
                    url = CommonUtil.getMapKey(item, RESULT_REVIEW_ITEM_HTTPFILEPATH);
                    thumbnail = CommonUtil.getMapKey(item, RESULT_REVIEW_ITEM_THUMBNAILHTTPPATH);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return getReviewLocaleMapData(id, url, thumbnail);
    }

    private Map<String, Object> getDummyLocaleMapData() {
        return getReviewLocaleMapData(0, "", "");
    }

    private Map<String, Object> getReviewLocaleMapData(Integer id, String url, String thumbnail) {
        Map<String, Object> data = new HashMap<>();

        if (id != null && id > 0) {
            data.put("id", id);
            data.put("url", getVUEDomainHttp() + url);
            data.put("thumbnail", getVUEDomainHttp() + thumbnail);
        } else {
            data.put("id", 0);
            data.put("url", "");
            data.put("thumbnail", "");
        }

        return data;
    }

    /**
     * Get Locale info list
     *
     * @param testRunId
     * @param locale
     * @return
     */
    private List<Map<String, Object>> getTestRunLocales(Integer testRunId, String locale) {

        List<Map<String, Object>> retListLocalesInfo = new ArrayList<>();
        try {
            String sql = testRunDao.getLocalesInfoSQLById(testRunId);
            List<Object[]> listResult = entityManager.createNativeQuery(sql).getResultList();
            List<Map<String, Object>> listMapData = EntityUtils.castQuerytoMap(listResult, "locale");

            List<String> listLocales = new ArrayList<>();
            for (Map<String, Object> item : listMapData) {
                listLocales.add(item.get("locale").toString());
            }

            String strQuery = String.join(",", listLocales);
            if (!CommonUtil.isEmpty(strQuery)) {
                List<LocaleInfo> listLocaleInfo = new ArrayList<>();
                List<LocaleInfo> listAllLocale = localesInfoRep.findAll();
                String strBaseLocale = Locales.EN_US.toString();
                for (LocaleInfo item : listAllLocale) {
                    if (listLocales.indexOf(item.getCode()) != -1 && !item.getCode().equals(strBaseLocale)) {
                        listLocaleInfo.add(item);
                    }
                }

                Map<String, Object> mapData = null;
                for (LocaleInfo item : listLocaleInfo) {
                    mapData = new HashMap<>();
                    mapData.put("code", item.getCode());
                    mapData.put("value", Locales.ZH_CN.toString().equals(locale) ? item.getZhCn() : item.getEnUs());
                    retListLocalesInfo.add(mapData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retListLocalesInfo;
    }

}


