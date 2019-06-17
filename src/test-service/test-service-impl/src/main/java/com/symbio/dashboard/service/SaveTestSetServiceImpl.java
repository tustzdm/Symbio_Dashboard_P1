package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.upload.SaveTestSetUpload;
import com.symbio.dashboard.ec.test.SaveReleaseErrorCode;
import com.symbio.dashboard.ec.test.SaveTestSetErrorCode;
import com.symbio.dashboard.model.TestSet;
import com.symbio.dashboard.repository.ReleaseRepository;
import com.symbio.dashboard.repository.TestSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SaveTestSetServiceImpl implements SaveTestSetService {

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private TestSetRepository testSetRepository;

    @Override
    public Result saveTestSet(SaveTestSetUpload saveTestSetUpload) {
        return saveTestSetResult(saveTestSetUpload);
    }

    private Result saveTestSetResult(SaveTestSetUpload saveTestSetUpload) {
        Result result;
        String token = saveTestSetUpload.getToken();
        //通过token得到相应的create user
        Integer createUser = Integer.valueOf(token);

        String locale = saveTestSetUpload.getLocale();
        Integer releaseId = saveTestSetUpload.getReleaseId();
        Integer testSetId = saveTestSetUpload.getTestSetId();
        String name = saveTestSetUpload.getName();
        Integer type = saveTestSetUpload.getType();
        Integer status = saveTestSetUpload.getStatus();
        String startDate = saveTestSetUpload.getStartDate();
        String endDate = saveTestSetUpload.getEndDate();
        Integer testOwner = saveTestSetUpload.getTestOwner();
        String jiraProduct = saveTestSetUpload.getJiraProduct();
        Integer butAssignee = saveTestSetUpload.getButAssignee();
        String description = saveTestSetUpload.getDescription();
        String extend = saveTestSetUpload.getExtend();

        result = createTestSet(testSetId, releaseId, name, type, status, startDate, endDate, testOwner, jiraProduct, butAssignee, description, locale, createUser, extend);

        if (!"0".equals(result.getEc())) {
            return result;
        }
        TestSet testSet = (TestSet) result.getCd();
        int flag = 0;
        try {
            testSetRepository.saveAndFlush(testSet);
            if (testSetId == null) {
                flag++;
            }
            flag++;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (flag == 1) {
            result.setCdAndRightEcAndEm("edit");
        } else if (flag == 2) {
            result.setCdAndRightEcAndEm("add");
        } else {
            result.setEc(SaveTestSetErrorCode.STSE002.toString());
            result.setEm("没有正确的操作"+flag);
            return result;
        }


        return result;
    }


    private Result createTestSet(Integer testSetId,Integer releaseId,String name,
                                 Integer type,Integer status,String startDate,
                                 String endDate,Integer testOwner,String jiraProduct,
                                 Integer bugAssignee,String description,String locale,
                                 Integer createUser,String extend) {
        Result result = new Result();

        List<String> allName ;

        List<Integer> allId = releaseRepository.getAllId();
        boolean existentReleaseId = false;
        for (Integer i : allId) {
            if (releaseId.equals(i)) {
                existentReleaseId = true;
            }
        }
        if (!existentReleaseId) {
            result.setEc(SaveTestSetErrorCode.STSE001.toString());
            result.setEm("没有此release id");
            return result;
        }

        TestSet testSet ;

        Date date = new Date();
        SimpleDateFormat systemTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            String nowtime = systemTime.format(date);
            Date time = systemTime.parse(nowtime);
            if (testSetId == null) {
                allName = testSetRepository.getAllName(0);
                for (String s : allName) {
                    if (name.equals(s)) {
                        result.setEc(SaveReleaseErrorCode.SR0004.toString());
                        result.setEm("已有testSet的名字，请重新填写");
                        return result;
                    }
                }
                testSet = new TestSet();
                testSet.setCreate_user(createUser);
                testSet.setCreate_time(time);
            } else {
                allName = testSetRepository.getAllName(testSetId);
                for (String s : allName) {
                    if (name.equals(s)) {
                        result.setEc(SaveReleaseErrorCode.SR0004.toString());
                        result.setEm("已有testSet的名字，请重新填写");
                        return result;
                    }
                }
                testSet = testSetRepository.getById(testSetId);
            }

            testSet.setId(testSetId);
            testSet.setRelease_id(releaseId);
            testSet.setName(name);
            testSet.setType(type);
            testSet.setStatus(status);
            testSet.setTest_owner(testOwner);
            testSet.setJira_project(jiraProduct);
            testSet.setBug_assignee(bugAssignee);
            testSet.setDescription(description);
            testSet.setLocales(locale);
            testSet.setUpdate_time(time);

            if (startDate != null) {
                Date startTime = systemTime.parse(startDate);
                testSet.setStart_time(startTime);
            }

            if (endDate != null) {
                Date endTime = systemTime.parse(endDate);
                testSet.setEnd_time(endTime);
            }
            result.setCdAndRightEcAndEm(testSet);

        } catch (Exception ex) {
            ex.printStackTrace();
        }



        return result;
    }
}
