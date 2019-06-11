package com.symbio.dashboard.navigation.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.navigation.dto.upload.ReleaseUpload;

public interface GetReleaseService {
    Result getReleaseList(ReleaseUpload releaseUpload);
}
