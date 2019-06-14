package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.upload.SaveReleaseUpload;
import org.springframework.stereotype.Service;

@Service
public interface SaveReleaseService {
    Result saveRelease(SaveReleaseUpload saveReleaseUpload);
}
