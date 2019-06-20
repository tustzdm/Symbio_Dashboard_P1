package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.message.GetReleaseListMessage;
import com.symbio.dashboard.dto.message.ReleaseMessageData;
import com.symbio.dashboard.dto.upload.GetReleaseListUpload;
import com.symbio.dashboard.model.Release;
import com.symbio.dashboard.navigation.dto.download.ReleaseData;
import com.symbio.dashboard.repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class GetReleaseListServiceImpl implements GetReleaseListService {


    @Autowired
    private ReleaseRepository releaseRepository;

    @Override
    public Result getReleaseList(GetReleaseListUpload getReleaseListUpload) {
        return getReleaseListResult(getReleaseListUpload);
    }

    private Result getReleaseListResult(GetReleaseListUpload getReleaseListUpload) {
        Result result;
        String token = getReleaseListUpload.getToken();
        String locale = getReleaseListUpload.getLocale();
        Integer productId = getReleaseListUpload.getProductId();
        Integer pageIndex = getReleaseListUpload.getPageIndex();
        Integer pageSize = getReleaseListUpload.getPageSize();

        result = createReleaseList(pageIndex, pageSize, productId);

        if (!"0".equals(result.getEc())) {
            return result;
        }

        List<ReleaseMessageData> list = (List<ReleaseMessageData>) result.getCd();

        GetReleaseListMessage getReleaseListMessage = new GetReleaseListMessage();
        getReleaseListMessage.setProductId(productId);
        getReleaseListMessage.setRole(token);
        getReleaseListMessage.setLocale(locale);
        getReleaseListMessage.setTotalRecord((long)releaseRepository.findByProductId(productId).size());
        getReleaseListMessage.setPageSize(pageSize);
        getReleaseListMessage.setPageIndex(pageIndex);
        getReleaseListMessage.setData(list);

        result.setCdAndRightEcAndEm(getReleaseListMessage);

        return result;
    }

    private Result createReleaseList(Integer pageIndex,Integer pageSize,Integer productId) {
        Result result = new Result();

        Pageable pageable = new PageRequest(pageIndex, pageSize);
        Page<Release> byId = releaseRepository.findByProductId(productId, pageable);
        List<ReleaseMessageData> list = new LinkedList<>();

        for (Release release : byId) {
            ReleaseMessageData releaseMessageData = new ReleaseMessageData();
            releaseMessageData.setId(release.getId());
            releaseMessageData.setName(release.getName());
            releaseMessageData.setStatus(release.getStatus());
            Date startTime = release.getStartTime();
            Date endTime = release.getEndTime();
            if (startTime != null) {
                releaseMessageData.setStart(startTime.toString());
            }
            if (endTime != null) {
                releaseMessageData.setEnd(endTime.toString());
            }
            releaseMessageData.setDescription(release.getRemark());
            list.add(releaseMessageData);
        }

        result.setCdAndRightEcAndEm(list);
        return result;
    }
}
