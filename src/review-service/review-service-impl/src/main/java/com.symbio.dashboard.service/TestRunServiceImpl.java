package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.TestRunVO;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.dto.TestRunUiDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName - TestRunServiceImpl
 * @Author - Admin
 * @Description - TestRun Service
 * @Date - 2019/8/5
 * @Version 1.0
 */

@Service
@SuppressWarnings("unchecked")
public class TestRunServiceImpl implements TestRunService {

    private static Logger logger = LoggerFactory.getLogger(TestRunServiceImpl.class);

    @Autowired
    private CommonDao commonDao;

    @Override
    public Result getTestRunList(String locale, TestRunVO testRun) {
        logger.trace("TestRunServiceImpl.getTestRunList() Enter");
        logger.trace(testRun.toString());

        Result<TestRunUiDTO> retResult = new Result<TestRunUiDTO>();

        try {
            TestRunUiDTO data = null;
            // ToDo: Result<TestRunUiDTO> retTRDTO = testRunDAO.getList();
            retResult.setCd(data);
        } catch (Exception e) {
            e.printStackTrace();
            retResult = commonDao.getResultArgs(locale,"000102", "getting TestRun List");
        }
        
        logger.trace("TestRunServiceImpl.getTestRunList() Exit");
        return retResult;
    }
}
