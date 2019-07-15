package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.message.GetTestSetListMessage;
import com.symbio.dashboard.dto.message.TestSetMessageData;
import com.symbio.dashboard.dto.upload.GetTestSetListUpload;
import com.symbio.dashboard.model.TestSet;
import com.symbio.dashboard.navigation.dto.download.TestSetMessage;
import com.symbio.dashboard.repository.TestSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class GetTestSetListServiceImpl implements GetTestSetListService {

    @Autowired
    private TestSetRepository testSetRepository;

    @Override
    public Result getTestSetList(GetTestSetListUpload getTestSetListUpload) {
        return getTestSetListResult(getTestSetListUpload);
    }


    private Result getTestSetListResult(GetTestSetListUpload getTestSetListUpload) {
        Result result;
        String token = getTestSetListUpload.getToken();
        String locale = getTestSetListUpload.getLocale();
        Integer releaseId = getTestSetListUpload.getReleaseId();
        Integer pageIndex = getTestSetListUpload.getPageIndex();
        Integer pageSize = getTestSetListUpload.getPageSize();

        result = createTestSetData(pageIndex, pageSize, releaseId);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        List<TestSetMessageData> list = (List<TestSetMessageData>) result.getCd();


        GetTestSetListMessage getTestSetListMessage = new GetTestSetListMessage();
        getTestSetListMessage.setLocale(locale);
        getTestSetListMessage.setRole(token);
        getTestSetListMessage.setReleaseId(releaseId);
        getTestSetListMessage.setPageIndex(pageIndex);
        getTestSetListMessage.setPageSize(pageSize);
        getTestSetListMessage.setTotalRecord((long)testSetRepository.findByReleaseId(releaseId).size());
        getTestSetListMessage.setData(list);

        result = new Result(getTestSetListMessage);


        return result;
    }

    private Result createTestSetData(Integer pageIndex,Integer pageSize,Integer releaseId) {
        Result result = null;

        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        Page<TestSet> byReleaseId = testSetRepository.findByReleaseId(releaseId, pageable);

        List<TestSetMessageData> list = new LinkedList<>();

        for (TestSet testSet : byReleaseId) {
            TestSetMessageData testSetMessageData = new TestSetMessageData();
            testSetMessageData.setId(testSet.getId());
            testSetMessageData.setName(testSet.getName());
            testSetMessageData.setType(testSet.getType());
            testSetMessageData.setStatus(testSet.getStatus());
            Date startTime = testSet.getStartTime();
            Date endTime = testSet.getEndTime();
            if (startTime != null) {
                testSetMessageData.setStartDate(startTime.toString());
            }
            if (endTime != null) {
                testSetMessageData.setEndDate(endTime.toString());
            }
//            testSetMessageData.setTestOwner(testSet.getTestOwner().toString());
            testSetMessageData.setJiraProject(testSet.getJiraProject());
//            testSetMessageData.setBugAssignee(testSet.getBugAssignee().toString());
            testSetMessageData.setDescription(testSet.getDescription());
            testSetMessageData.setExtend(null);

            list.add(testSetMessageData);
        }

        result = new Result(list);

        return result;
    }
}
