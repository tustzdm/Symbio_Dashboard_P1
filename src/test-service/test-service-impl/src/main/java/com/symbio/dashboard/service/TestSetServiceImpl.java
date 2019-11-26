package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.charts.BarChart;
import com.symbio.dashboard.data.charts.PieChart;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.dao.TestCaseDao;
import com.symbio.dashboard.data.dao.TestSetDao;
import com.symbio.dashboard.data.dao.UserDao;
import com.symbio.dashboard.data.repository.TestSetRep;
import com.symbio.dashboard.enums.EntityDisplay;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.TestSet;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName - TestSetServiceImpl
 * @Author - Admin
 * @Description
 * @Date - 2019/7/26 15:38
 * @Version 1.0
 */

@Service
@SuppressWarnings("unchecked")
public class TestSetServiceImpl implements TestSetService {

    private static Logger logger = LoggerFactory.getLogger(TestSetServiceImpl.class);

    @Autowired
    private TestSetRep testSetRep;
    @Autowired
    private TestSetDao testsetDao;
    @Autowired
    private TestCaseDao testCaseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private PieChart pieChart;
    @Autowired
    private BarChart barChart;

    @Override
    public Result getTestCaseList(Integer userId, String locale, Integer testSetId, Integer pageIndex, Integer pageSize) {
        Result retResult = testCaseDao.getTestCaseList(userId, locale, testSetId, pageIndex, pageSize);
        if (retResult.hasError()) {
            logger.info(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
        }
        return retResult;
    }

    @Override
    public Result getTestSetList(Integer userId, String locale, Integer releaseId, Integer pageIndex, Integer pageSize) {
        Result retResult = testsetDao.getTestSetList(userId, locale, releaseId, pageIndex, pageSize);
        if(retResult.hasError()) {
            logger.info(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
        }
        return retResult;
    }

    @Override
    public Result getTestSetList(Integer releaseId, Integer pageIndex, Integer pageSize){
        return getTestSetList(null, Locales.EN_US.toString(), releaseId, pageIndex, pageSize);
    }

    @Override
    public Result getTestSetInfo(Integer userId, Integer id) {
        TestSet testSet;
        try {
            testSet = testSetRep.getById(id);
            if (testSet == null || "".equals(testSet)) {
                return new Result("000120", "TestSet Info is empty");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("200102", "Get TestSet Info Failed");
        }
        return new Result(testSet);
    }

    @Override
    public Result updateTestSet(Integer userId, String locale, TestSet testSetInfo) {
        logger.trace("TestSetServiceImpl.updateTestSet() Enter");

        Result retResult = null;
        String strMsg = null;

        try {
            TestSet testSet = testSetInfo;
            User userInfo = userDao.getUserById(userId);

            // If id is null, add new Product
            if (BusinessUtil.isIdEmpty(testSet.getId())) {
                retResult = verifyInfo(testSetInfo);
                if (retResult.hasError()) {
                    return retResult;
                }
                strMsg = "Add";
                testSet.setStatus(0); // 0: Pending
                testSet.setDisplay(EntityDisplay.SHOW.getValue());

                testSet.setCreateTime(new Date());
                if(userInfo != null) {
                    testSet.setCreateUser(userInfo.getId());
                    testSet.setCreateUserName(userInfo.getFullName());
                }

            } else {
                strMsg = "Update";

                // ToDo: fetch entity, then set the value from UI
                // testSet = testSetRep.getById(id);

                if(testSet.getDisplay() == null) {
                    testSet.setDisplay(EntityDisplay.SHOW.getValue());
                }
                if(testSet.getStatus() == null) {
                    testSet.setDisplay(0);
                }
            }
            strMsg = String.format("TestSet %s", strMsg);
            testSet.setUpdateTime(new Date());
            if(userInfo != null) {
                testSet.setUpdateUser(userInfo.getId());
                testSet.setUpdateUserName(userInfo.getFullName());
            }

            try {
                // Save or update
                testSet = testSetRep.saveAndFlush(testSet);

                Map<String, Integer> map = new HashMap<>();
                map.put("id", testSet.getId());
                retResult = new Result(map);
            } catch (Exception e) {
                e.printStackTrace();
                if(e.getMessage().contains("testset_release_name")) {
                    retResult = new Result("000123", "TestSet Name is duplicated. Name = " + testSet.getName());
                } else {
                    retResult = new Result("000102", "Exception happened while operation on " + strMsg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            retResult =  new Result("000102", strMsg + " Exception! " + e.getMessage());
        }

        logger.trace("TestSetServiceImpl.updateTestSet() Exit");
        return retResult;
    }

    @Override
    public Result removeTestSet(Integer id) {
        try {
            TestSet testSet = testSetRep.getById(id);
            testSet.setDisplay(0);
            testSetRep.saveAndFlush(testSet);
        } catch (Exception e) {
            e.printStackTrace();
            if (CommonUtil.match(e.getMessage(), "(No|entity|id|exists)")) {
                return new Result("000122", "Id does not exist");
            }
        }
        return new Result("TestSet Removed");
    }

    private Result verifyInfo(TestSet testSetInfo) {

        if (testSetInfo.getName() == null) {
            return new Result("000101", "Name cannot be empty");
        }
        if (testSetInfo.getReleaseId() == null) {
            return new Result("000101", "Release Id cannot be empty");
        }
        return new Result("Info verified");
    }

    @Override
    public Result getTestSetUiInfo(Integer userId, String locale, Integer uiInfo, Integer id){
        Result retResult = testsetDao.getTestSetUiInfo(userId, locale, uiInfo, id);
        if (retResult.hasError()) {
            logger.info(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
        }
        return retResult;
    }

    @Override
    public Result getNavigationList(Integer userId, String locale, Integer releaseId, Integer total){
        if(BusinessUtil.isIdEmpty(releaseId)) {
            return commonDao.getResult("000101", "Product Id");
        }

        return testsetDao.getNavigationList(userId, locale, releaseId, total);
    }

    @Override
    public Result getTestSetChart(Integer userId, String locale) {
        Map<String, Object> map = new HashMap<>();
        List charts = new ArrayList();
        try {

            Map data = pieChart.setChartData(); //service.getReportData();
            Map data2 = barChart.setChartData(); //service.getReportData();
            Map pieMap = pieChart.getPieScrollLegendChart(userId, locale, data);
            Map barMap = barChart.getBarCategoryStackChart(userId, locale, data2);
            charts.add(pieMap);
            charts.add(barMap);
            map.put("charts", charts);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result("000120", "Chart");
        }
        return new Result(map);
    }
}
