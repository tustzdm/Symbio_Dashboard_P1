package com.symbio.dashboard.report.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.business.StatListFactory;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.dao.StatisticsDao;
import com.symbio.dashboard.data.service.DataCommonService;
import com.symbio.dashboard.data.utils.SQLUtils;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.SystemListSetting;
import com.symbio.dashboard.model.StatList;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.symbio.dashboard.enums.EnumDef.SQL_TYPE.*;

/**
 *
 */
@Service
@Data
@SuppressWarnings("unchecked")
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private DataCommonService dataCommService;
    @Autowired
    private StatisticsDao statDao;

    @Override
    public Result processTestSet() {
        String funcName = "StatisticsServiceImpl.processTestSet()";

        // Step1: fetch data
        String strFields = "id,done,total";
        String sql = SQLUtils.buildSql(STAT_PROGRESS_TEST_SET);

        Result<List<Map<String, Object>>> resultQuery = dataCommService.executeSqlClause(sql, strFields);
        if (resultQuery.hasError()) {
            return resultQuery;
        }

        // Step2: sync up data
        List<Map<String, Object>> listData = resultQuery.getCd();
        Result<Map<String, Object>> resultProgress = saveStatProgressData(listData, SystemListSetting.TestSet);
        if (resultProgress.hasError()) {
            return resultProgress;
        }

        return resultProgress;
    }

    @Override
    public Result processRelease() {
        String funcName = "StatisticsServiceImpl.processRelease()";

        // Step1: fetch data
        String strFields = "id,done,total";
        String sql = SQLUtils.buildSql(STAT_PROGRESS_RELEASE);
        Result<List<Map<String, Object>>> resultQuery = dataCommService.executeSqlClause(sql, strFields);
        if (resultQuery.hasError()) {
            return resultQuery;
        }

        // Step2: sync up data
        List<Map<String, Object>> listData = resultQuery.getCd();
        Result<Map<String, Object>> resultProgress = saveStatProgressData(listData, SystemListSetting.Release);
        if (resultProgress.hasError()) {
            return resultProgress;
        }

        return resultProgress;
    }

    @Override
    public Result processProduct() {
        String funcName = "StatisticsServiceImpl.processProduct()";

        // Step1: fetch data
        String strFields = "id,done,total";
        String sql = SQLUtils.buildSql(STAT_PROGRESS_PRODUCT);
        Result<List<Map<String, Object>>> resultQuery = dataCommService.executeSqlClause(sql, strFields);
        if (resultQuery.hasError()) {
            return resultQuery;
        }

        // Step2: sync up data
        List<Map<String, Object>> listData = resultQuery.getCd();
        Result<Map<String, Object>> resultProgress = saveStatProgressData(listData, SystemListSetting.Product);
        if (resultProgress.hasError()) {
            return resultProgress;
        }

        return resultProgress;
    }

    private Result<Map<String, Object>> saveStatProgressData(List<Map<String, Object>> data, SystemListSetting progressType) {
        Result<Map<String, Object>> retResult = new Result<>();

        if (CommonUtil.isEmpty(data)) return retResult;
        String funcName = "StatisticsServiceImpl.saveStatProgressData()";

        try {
            Map<String, Object> mapData = new HashMap<>();
            Integer nUpdate = 0, nNewCount = 0;
            String id, strDone, strTotal;
            Integer nFkType = getFKType(progressType);
            for (Map<String, Object> item : data) {
                id = item.get("id").toString();
                strDone = item.get("done").toString();
                strTotal = item.get("total").toString();

                String strProgressJSONData = getProgressJsonInfo(strDone, strTotal);
                StatList statListData = statDao.getProgressStatData(nFkType, Integer.parseInt(id));
                if (CommonUtil.isEmpty(statListData)) {
                    statListData = StatListFactory.createStatList(progressType, Integer.parseInt(id), EnumDef.STAT_LIST_FIELD.PROGRESS, strProgressJSONData);
                    if (!CommonUtil.isEmpty(statListData)) {
                        nNewCount++;
                    }
                } else {
                    statListData.setValueContent(strProgressJSONData);
                    statListData.setValidation(1);
                    nUpdate++;
                }

                if (!CommonUtil.isEmpty(statListData)) {
                    statDao.saveStatisticsData(statListData);
                } else {
                    log.warn(ErrorConst.getWarningLogMsg(funcName, "Build Stat_list data failure."));
                }
            }

            mapData.put("Task", "Sync up Progress - " + progressType.name());
            mapData.put("updateCount", nUpdate);
            mapData.put("createCount", nNewCount);
            retResult.setCd(mapData);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return ErrorConst.getExceptionResult(funcName, e);
        }
        return retResult;

    }

    private String getProgressJsonInfo(String done, String total) {
        Map<String, Integer> mapData = new HashMap<>();

        mapData.put("done", Integer.parseInt(done));
        mapData.put("total", Integer.parseInt(total));

        return JSONUtil.mapToString(mapData);
    }

    private Integer getFKType(EnumDef.SQL_TYPE progressType) {
        Integer nRet = 0;
        switch (progressType) {
            case STAT_PROGRESS_TEST_SET:
                nRet = 2;
                break;
            case STAT_PROGRESS_RELEASE:
                nRet = 1;
                break;
            case STAT_PROGRESS_PRODUCT:
                nRet = 0;
                break;
        }

        return nRet;
    }

    private Integer getFKType(SystemListSetting listSetting) {
        return listSetting.ordinal();
    }
}
