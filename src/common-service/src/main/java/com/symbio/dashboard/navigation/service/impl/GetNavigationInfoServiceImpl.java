package com.symbio.dashboard.navigation.service.impl;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.navigation.dto.upload.NavigationInfoUpload;
import com.symbio.dashboard.navigation.service.GetNavigationInfoService;

public class GetNavigationInfoServiceImpl implements GetNavigationInfoService {
    @Override
    public Result getNavigationInfo(NavigationInfoUpload navigationInfoUpload) {
        return null;
    }

    private Result getNavigationInfoResult(NavigationInfoUpload navigationInfoUpload) {
        Result result = new Result();
        String locale = navigationInfoUpload.getLocale();
        String token = navigationInfoUpload.getToken();


        return result;
    }


}
