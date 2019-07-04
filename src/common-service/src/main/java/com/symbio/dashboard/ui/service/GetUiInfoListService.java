package com.symbio.dashboard.ui.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.ui.dto.upload.UiInfoUpload;
import org.springframework.stereotype.Service;

@Service
public interface GetUiInfoListService {

    Result getUiInfoList(UiInfoUpload uiInfoUpload);
}