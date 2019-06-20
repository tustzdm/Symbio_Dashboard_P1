package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.message.TestSetMessageData;
import com.symbio.dashboard.dto.upload.GetTestSetListUpload;
import com.symbio.dashboard.model.TestSet;
import com.symbio.dashboard.repository.TestSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        Result result = new Result();
        String token = getTestSetListUpload.getToken();
        String locale = getTestSetListUpload.getLocale();
        Integer releaseId = getTestSetListUpload.getReleaseId();
        Integer pageIndex = getTestSetListUpload.getPageIndex();
        Integer pageSize = getTestSetListUpload.getPageSize();

        result = createTestSetData(pageIndex, pageSize, releaseId);

        return result;
    }

    private Result createTestSetData(Integer pageIndex,Integer pageSize,Integer releaseId) {
        Result result = new Result();

        Pageable pageable = new PageRequest(pageIndex,pageSize);
        Page<TestSet> byReleaseId = testSetRepository.findByReleaseId(releaseId, pageable);

        List<TestSetMessageData> list = new LinkedList<>();

        for (TestSet testSet : byReleaseId) {
            System.out.println(testSet);
        }

        return result;
    }
}
