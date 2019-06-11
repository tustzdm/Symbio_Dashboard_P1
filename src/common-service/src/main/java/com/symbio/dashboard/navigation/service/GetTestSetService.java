package com.symbio.dashboard.navigation.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.navigation.dto.upload.TestSetUpload;


public interface GetTestSetService {

    Result getTestSetList(TestSetUpload testSetUpload);
}
