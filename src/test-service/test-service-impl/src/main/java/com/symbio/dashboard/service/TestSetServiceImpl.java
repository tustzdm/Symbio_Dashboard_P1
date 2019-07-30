package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.dao.TestSetDao;
import com.symbio.dashboard.data.repository.TestSetRep;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.TestSet;
import com.symbio.dashboard.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    @Override
    public Result getTestSetList(Integer userId, String locale, Integer releaseId, Integer pageIndex, Integer pageSize) {
        Result retResult = testsetDao.getTestSetList(locale, releaseId, pageIndex, pageSize);
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
    public Result updateTestSet(TestSet testSetInfo) {
        Result result;
        TestSet testSet;
        Integer id = testSetInfo.getId();

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String time = simpleDateFormat.format(date);
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            // If id is null, add new Product
            if (id == null) {
                result = verifyInfo(testSetInfo);
                if (result.hasError()) {
                    return result;
                }
                testSet = new TestSet(0, 1);

                testSet.setCreateTime(date);
                testSet.setCreateUser(testSetInfo.getCreateUser());
                testSet.setCreateUserName(testSetInfo.getCreateUserName());

            } else {
                // Get existed Product object
                testSet = testSetRep.getById(id);
                if (!testSetInfo.getName().equalsIgnoreCase(testSet.getName())) {
                    result = verifyInfo(testSetInfo);
                    if (result.hasError()) {
                        return result;
                    }
                }
            }

            testSet.setReleaseId(testSetInfo.getReleaseId());
            testSet.setName(testSetInfo.getName());
            testSet.setType(testSetInfo.getType());
            testSet.setStartTime(testSetInfo.getStartTime());
            testSet.setEndTime(testSetInfo.getEndTime());
            testSet.setTestOwner(testSetInfo.getTestOwner());
            testSet.setJiraProject(testSetInfo.getJiraProject());
            testSet.setBugAssignee(testSetInfo.getBugAssignee());
            testSet.setDescription(testSetInfo.getDescription());
            testSet.setLocales(testSetInfo.getLocales());

            testSet.setUpdateTime(date);
            testSet.setUpdateUser(testSetInfo.getUpdateUser());
            testSet.setUpdateUserName(testSetInfo.getUpdateUserName());

            int flag = 0;
            try {
                // Save or update
                testSetRep.saveAndFlush(testSet);
                if (id == null) {
                    flag++;
                }
                flag++;
            } catch (Exception e) {
                e.printStackTrace();
                return new Result("200110", "TestSet info cannot be saved");
            }

            if (flag == 1) {
                result = new Result("TestSet Updated");
            } else if (flag == 2) {
                result = new Result("TestSet Added");
            } else {
                return new Result("000002", "Error at flag" + flag);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result("000102", "Edit/Add TestSet error");
        }
        return result;
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

//        List<String> allNames = testSetRep.getAllNamesByReleaseId(testSetInfo.getReleaseId());
//        if (allNames != null && !allNames.isEmpty()) {
//            for (String name : allNames) {
//                if (testSetInfo.getName().equalsIgnoreCase(name)) {
//                    return new Result("000123", "TestSet name already exists");
//                }
//            }
//        }

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
}
