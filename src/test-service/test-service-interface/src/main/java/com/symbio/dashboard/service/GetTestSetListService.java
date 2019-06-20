package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.upload.GetTestSetListUpload;
import org.springframework.stereotype.Service;

@Service
public interface GetTestSetListService {

    Result getTestSetList(GetTestSetListUpload getTestSetListUpload);
}
