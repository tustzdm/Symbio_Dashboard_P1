package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.upload.SaveTestSetUpload;
import org.springframework.stereotype.Service;


@Service
public interface SaveTestSetService {
    Result saveTestSet(SaveTestSetUpload saveTestSetUpload);
}
