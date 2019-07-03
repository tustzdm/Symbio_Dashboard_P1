package com.symbio.dashboard.service;


import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.upload.GetReleaseInfoUpload;
import org.springframework.stereotype.Service;

@Service
public interface GetReleaseInfoService {
    Result getReleaseInfo(GetReleaseInfoUpload getReleaseInfoUpload);
}
