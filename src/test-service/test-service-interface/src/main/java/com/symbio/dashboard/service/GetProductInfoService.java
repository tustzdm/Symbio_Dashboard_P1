package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.upload.GetProductInfoUpload;
import org.springframework.stereotype.Service;

@Service
public interface GetProductInfoService {
    Result getProductInfo(GetProductInfoUpload getProductInfoUpload);
}
