package com.symbio.dashboard.navigation.service.impl;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.TestSet;
import com.symbio.dashboard.navigation.dto.download.TestSetData;
import com.symbio.dashboard.navigation.dto.download.TestSetMessage;
import com.symbio.dashboard.navigation.dto.upload.TestSetUpload;
import com.symbio.dashboard.navigation.service.GetTestSetService;
import com.symbio.dashboard.repository.TestSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GetTestSetServiceImpl implements GetTestSetService {

    @Autowired
    private TestSetRepository testSetRepository;

    @Override
    public Result getTestSetList(TestSetUpload testSetUpload) {
        return getTestSetListResult(testSetUpload);
    }

    /**
     * 此方法用于返回导航条中的testset所需要的列表信息
     *
     * @param testSetUpload 上送的信息对象
     *
     * @return 返回封装好的testset列表返回信息
     */
    private Result getTestSetListResult(TestSetUpload testSetUpload) {
        String locale = testSetUpload.getLocale();
        Integer productId = testSetUpload.getProductId();
        Integer releaseId = testSetUpload.getReleaseId();
        String token = testSetUpload.getToken();
        Integer total = testSetUpload.getTotal();

        Result result = createTestSetList(total, releaseId);

        if (result.hasError()) {
            return result;
        }

        TestSetMessage testSetMessage = (TestSetMessage) result.getCd();
        testSetMessage.setLocale(locale);
        testSetMessage.setProductId(productId);
        testSetMessage.setRole(token);

        return new Result(testSetMessage);
    }


    /**
     * 此方法用于封装testset反馈信息中的部分信息，只包含了 release_id、isShowMore和data数据列表
     *
     * @param total data的长度大小
     * @param releaseId release_id
     *
     * @return 返回部分release的信息
     */
    private Result createTestSetList(Integer total, Integer releaseId) {
        boolean isShowMore = false;
        TestSetMessage testSetMessage = new TestSetMessage();

        if (total == null) {
            testSetMessage.setIsShowMore(isShowMore);
            return new Result(testSetMessage);
        }

        List<TestSet> testSetList = testSetRepository.findByRelease_idAndOrderByUpdate_timeAtDesc(releaseId);
        int flag = testSetList.size();

        List<TestSetData> list = new LinkedList<>();

        if (total == 0) {
            list = createTestSetData(testSetList, flag);
        } else if (total >= flag) {
            list = createTestSetData(testSetList, flag);
        } else if (total < flag) {
            list = createTestSetData(testSetList, total);
            isShowMore = true;
        }

        testSetMessage.setIsShowMore(isShowMore);
        testSetMessage.setData(list);
        testSetMessage.setReleaseId(releaseId);

        return new Result(testSetMessage);
    }


    /**
     * 此方法用于封装 testset 中的data列表
     *
     * @param testSetList 所有的test_set的实体类列表
     * @param size data列表的长度大小
     *
     * @return 返回一个封装好的test_set中的data
     */
    private List<TestSetData> createTestSetData(List<TestSet> testSetList, int size) {
        List<TestSetData> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            TestSetData testSetData = new TestSetData();
            testSetData.setId(testSetList.get(i).getId());
            testSetData.setName(testSetList.get(i).getName());
            testSetData.setType(testSetList.get(i).getType());
            list.add(testSetData);
        }
        return list;
    }
}
