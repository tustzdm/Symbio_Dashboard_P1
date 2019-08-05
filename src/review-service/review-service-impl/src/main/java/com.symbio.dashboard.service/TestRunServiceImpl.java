package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.TestRunVO;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.dao.ProductDao;
import com.symbio.dashboard.data.dao.ReleaseDao;
import com.symbio.dashboard.data.dao.TestSetDao;
import com.symbio.dashboard.dto.TestRunUiDTO;
import com.symbio.dashboard.util.BusinessUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ReleaseDao releaseDao;
    @Autowired
    private TestSetDao testSetDao;

    @Override
    public Result getTestRunList(String locale, TestRunVO testRun) {
        logger.trace("TestRunServiceImpl.getTestRunList() Enter");
        logger.trace(testRun.toString());

        Result<TestRunUiDTO> retResult = new Result<TestRunUiDTO>();

        try {
            TestRunUiDTO data = new TestRunUiDTO();
            // ToDo: Result<TestRunUiDTO> retTRDTO = testRunDAO.getList();
            data = getDemoData(testRun);

            retResult.setCd(data);
        } catch (Exception e) {
            e.printStackTrace();
            retResult = commonDao.getResultArgs(locale,"000102", "getting TestRun List");
        }
        
        logger.trace("TestRunServiceImpl.getTestRunList() Exit");
        return retResult;
    }

    private TestRunUiDTO getDemoData(TestRunVO testRun) {
        TestRunUiDTO data = new TestRunUiDTO();

        data.setProductId(testRun.getProductId());
        if(BusinessUtil.isIdEmpty(testRun.getReleaseId())) {
            data.setReleaseId(1);
        } else {
            data.setReleaseId(testRun.getReleaseId());
        }
        // TestSetId
        if(BusinessUtil.isIdEmpty(testRun.getTestSetId())) {
            data.setTestSetId(1);
        } else {
            data.setTestSetId(testRun.getTestSetId());
        }

        Result retProduct = productDao.getNavigationList(testRun.getLocale(), null);
        if(retProduct.isSuccess()) {
            data.setProductList((List<Map<String, Object>>)retProduct.getCd());
        }

        Result retRelease = releaseDao.getNavigationList(testRun.getLocale(), testRun.getProductId(), null);
        List<Map<String, Object>> listData = null;
        if(retRelease.isSuccess()) {
            listData = (List<Map<String, Object>>)retRelease.getCd();
            if(BusinessUtil.isIdEmpty(testRun.getReleaseId()) && !listData.isEmpty()) {
                data.setReleaseId((Integer) listData.get(0).get("id"));
            }
            data.setReleaseList(listData);
        }
        Result retTestSet = testSetDao.getNavigationList(testRun.getUserId(), testRun.getLocale(), testRun.getReleaseId(),null);
        if(retRelease.isSuccess()) {
            listData = (List<Map<String, Object>>)retTestSet.getCd();
            if(BusinessUtil.isIdEmpty(testRun.getTestSetId()) && !listData.isEmpty()) {
                data.setTestSetId((Integer) listData.get(0).get("id"));
            }
            data.setTestSetList(listData);
        }

        return data;
    }
}
