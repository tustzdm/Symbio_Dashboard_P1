package com.symbio.dashboard.navigation.service.impl;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.navigation.dto.download.ReleaseData;
import com.symbio.dashboard.navigation.dto.download.ReleaseMessage;
import com.symbio.dashboard.navigation.dto.upload.ReleaseUpload;
import com.symbio.dashboard.model.Release;
import com.symbio.dashboard.repository.ReleaseRepository;
import com.symbio.dashboard.navigation.service.GetReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GetReleaseServiceImpl implements GetReleaseService {

    @Autowired
    private ReleaseRepository releaseRepository;


    @Override
    public Result getReleaseList(ReleaseUpload releaseUpload) {
        return getReleaseListResult(releaseUpload);
    }


    /**
     * 此方法为封装导航条中的release返回信息
     * @param releaseUpload release的上送信息
     * @return 返回release的全部信息
     */
    private Result getReleaseListResult(ReleaseUpload releaseUpload) {
        String token = releaseUpload.getToken();
        String locale = releaseUpload.getLocale();
        Integer productId = releaseUpload.getProductId();
        Integer total = releaseUpload.getTotal();

        Result result = createReleaseList(total, productId);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        ReleaseMessage releaseMessage = (ReleaseMessage) result.getCd();
        releaseMessage.setLocale(locale);
        releaseMessage.setRole(token);

        result.setCdAndRightEcAndEm(releaseMessage);
        return result;
    }


    /**
     * 此方法用于创建release返回信息中的部分信息，只封装了isShowMore、productId和data
     *
     * @param total 需要的data的长度
     * @param productId product的id
     *
     * @return 返回创建的部分release返回信息
     */
    private Result createReleaseList(Integer total, Integer productId) {
        Result result = new Result();
        ReleaseMessage releaseMessage = new ReleaseMessage();
        boolean isShowMore = false;

        if (total == null) {
            //相应操作

            releaseMessage.setIsShowMore(isShowMore);
            result.setCdAndRightEcAndEm(releaseMessage);
            return result;
        }

        List<Release> releaseList = releaseRepository.findByProduct_idAndOrderByUpdate_timeAtDesc(productId);

        int flag = releaseList.size();

        List<ReleaseData> list = new LinkedList<>();

        if (total == 0) {
            list = createReleaseData(releaseList, flag);
        } else if (total >= flag) {
            list = createReleaseData(releaseList, flag);

        } else if (total < flag) {
            list = createReleaseData(releaseList, total);
            isShowMore = true;
        }

        releaseMessage.setData(list);
        releaseMessage.setIsShowMore(isShowMore);
        releaseMessage.setProductId(productId);


        result.setCdAndRightEcAndEm(releaseMessage);

        return result;
    }


    /**
     * 此方法用于封装release中的data
     * @param releaseList 获取的实体类列表
     * @param size data的长度
     * @return 返回一个封装好的data
     */
    private List<ReleaseData> createReleaseData(List<Release> releaseList,int size) {
        List<ReleaseData> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            ReleaseData releaseData = new ReleaseData();
            releaseData.setId(releaseList.get(i).getId());
            releaseData.setName(releaseList.get(i).getName());
            list.add(releaseData);
        }
        return list;
    }

}
