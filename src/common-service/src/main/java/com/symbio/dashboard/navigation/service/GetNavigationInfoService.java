package com.symbio.dashboard.navigation.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.navigation.dto.upload.NavigationInfoUpload;
import org.springframework.stereotype.Service;

@Service
public interface GetNavigationInfoService {

    Result getNavigationInfo(NavigationInfoUpload navigationInfoUpload);
}
