package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.message.GetReleaseInfoMessage;
import com.symbio.dashboard.dto.message.MessageUiInfo;
import com.symbio.dashboard.dto.message.ReleaseMessageData;
import com.symbio.dashboard.dto.upload.GetReleaseInfoUpload;
import com.symbio.dashboard.model.Release;
import com.symbio.dashboard.repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class GetReleaseInfoServiceImpl implements GetReleaseInfoService {

    @Autowired
    private ReleaseRepository releaseRepository;

    @Override
    public Result getReleaseInfo(GetReleaseInfoUpload getReleaseInfoUpload) {
        return getReleaseInfoRelease(getReleaseInfoUpload);
    }

    private Result getReleaseInfoRelease(GetReleaseInfoUpload getReleaseInfoUpload) {
        Result result;

        String locale = getReleaseInfoUpload.getLocale();
        String token = getReleaseInfoUpload.getToken();
        Integer productId = getReleaseInfoUpload.getProductId();
        Integer releaseId = getReleaseInfoUpload.getReleaseId();
        Integer uiInfo = getReleaseInfoUpload.getUiInfo();

        result = createReleaseData(releaseId);
        if (result.hasError()) {
            return result;
        }
        List<ReleaseMessageData> dataList = (List<ReleaseMessageData>) result.getCd();

        GetReleaseInfoMessage getReleaseInfoMessage = new GetReleaseInfoMessage();
        if (uiInfo == 1) {
            result = createReleaseUiInfo();
            if (result.hasError()) {
                return result;
            }
            MessageUiInfo messageUiInfo = (MessageUiInfo) result.getCd();
            getReleaseInfoMessage.setUiInfo(messageUiInfo);
        }

        getReleaseInfoMessage.setData(dataList);
        getReleaseInfoMessage.setLocale(locale);
        getReleaseInfoMessage.setRole(token);

        return new Result(getReleaseInfoMessage);
    }

    /**
     * 用于创建release的data内容
     *
     * @param releaseId
     * @return
     */
    private Result createReleaseData(Integer releaseId) {
        List<ReleaseMessageData> list = new LinkedList<>();
        Release byId = releaseRepository.getById(releaseId);
        if (byId != null) {
            ReleaseMessageData releaseMessageData = new ReleaseMessageData();
            releaseMessageData.setId(byId.getId());
            releaseMessageData.setName(byId.getName());
            releaseMessageData.setStatus(byId.getStatus());
            Date startTime = byId.getStartTime();
            if (startTime != null) {
                releaseMessageData.setStart(startTime.toString());
            }
            Date endTime = byId.getEndTime();
            if (endTime != null) {
                releaseMessageData.setEnd(endTime.toString());
            }
            releaseMessageData.setDescription(byId.getRemark());
            list.add(releaseMessageData);
        }

        return new Result(list);
    }

    private Result createReleaseUiInfo() {
        MessageUiInfo messageUiInfo = new MessageUiInfo();
        return new Result(messageUiInfo);
    }


}




