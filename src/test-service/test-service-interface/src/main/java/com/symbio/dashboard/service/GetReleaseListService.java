package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.upload.GetReleaseListUpload;
import org.springframework.stereotype.Service;

@Service
public interface GetReleaseListService {
    Result getReleaseList(GetReleaseListUpload getReleaseListUpload);
}