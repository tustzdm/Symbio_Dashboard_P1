package com.symbio.dashboard.ui.service.impl;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.UiInfo;
import com.symbio.dashboard.repository.UiInfoRepository;
import com.symbio.dashboard.ui.dto.upload.UiInfoUpload;
import com.symbio.dashboard.ui.service.GetUiInfoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName - GetUiInfoListServiceImpl
 * @Author - Danny
 * @Description - 获取页面元素列表实现类
 * @Date - 2019/7/5 11:30
 * @Version 1.0
 */

@Service
public class GetUiInfoListServiceImpl implements GetUiInfoListService {

    @Autowired
    private UiInfoRepository uiInfoRepository;

    @Override
    public Result getUiInfoList(UiInfoUpload uiInfoUpload) {
        return getUiInfoListResult(uiInfoUpload);
    }

    /**
     * @return com.symbio.dashboard.Result
     * @Author - Danny
     * @Description - 获取对应页面元素列表
     * @Date - 2019/7/9
     * @Param - [uiInfoUpload]
     */
    private Result getUiInfoListResult(UiInfoUpload uiInfoUpload) {
        Result result = new Result();

        try {
            String page = uiInfoUpload.getPage();

            // 根据页面查询元素
            List<UiInfo> uiInfoList = uiInfoRepository.getUiInfoListByPageName(page);

            if (uiInfoList == null || uiInfoList.isEmpty()) {
                result.setEc("100011");
                result.setEm("查询失败");
                return result;
            }

            result.setCdAndRightEcAndEm(uiInfoList);

        } catch (Exception e) {
            e.printStackTrace();
            result.setEc("100012");
            result.setEm("SQL Error");
        }
        return result;
    }
}