package com.symbio.dashboard.data.service.impl;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.service.DataCommonService;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@SuppressWarnings("unchecked")
public class DataCommonServiceImpl implements DataCommonService {
    @Autowired
    private CommonDao commonDao;

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
}
