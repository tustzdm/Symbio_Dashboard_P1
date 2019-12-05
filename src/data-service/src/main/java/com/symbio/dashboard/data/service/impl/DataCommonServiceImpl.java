package com.symbio.dashboard.data.service.impl;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.service.DataCommonService;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.SystemListSetting;
import com.symbio.dashboard.model.SysListSetting;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@SuppressWarnings("unchecked")
public class DataCommonServiceImpl implements DataCommonService {
    @Autowired
    private CommonDao commonDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Result getUserPageRole(EnumDef.DASHBOARD_PAGE page, Integer userId) {
        return getUserPageRole(null, page, userId);
    }

    @Override
    public Result getUserPageRole(String invokeFuncName, EnumDef.DASHBOARD_PAGE page, Integer userId) {
        Result<Integer> retResult = new Result<>();

        Integer nRole = commonDao.getUserPageRole(page, userId);

        if (retResult.hasError()) {
            if (!CommonUtil.isEmpty(invokeFuncName)) {
                log.error(ErrorConst.getErrorLogMsg(invokeFuncName, retResult));
            } else {
                log.error(ErrorConst.getErrorLogMsg("CommonService.getUserPageRole()", retResult));
            }
            return retResult;
        }

        retResult.setCd(nRole);

        return retResult;
    }

    @Override
    public List<String> getQueryFields(SystemListSetting listType, List<SysListSetting> listSetting) {
        return commonDao.getQueryFields(listType, listSetting);
    }

    @Override
    public Integer getSqlCount(String sql) {
        Integer nCount = 0;

        try {
            List<Object> listResult = entityManager.createNativeQuery(sql).getResultList();
            if (listResult != null) {
                BigInteger nBigInt = (BigInteger) listResult.get(0);
                nCount = nBigInt.intValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nCount;
    }

    @Override
    public Result executeSqlClause(String sql, String fields) {
        String funcName = "DataCommonServiceImpl.executeSqlClause()";

        List<Map<String, Object>> mapData = new ArrayList<>();
        try {
            List<Object[]> listResult = entityManager.createNativeQuery(sql).getResultList();
            mapData = EntityUtils.castQuerytoMap(listResult, fields);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sql = {}, fields = {}", sql, fields);
            log.error(ErrorConst.getExceptionLogMsg(funcName, e));
            return ErrorConst.getExceptionResult(funcName, e);
        }

        return new Result(mapData);
    }
}
