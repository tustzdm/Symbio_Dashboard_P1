package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.upload.GetProductListUpload;

public interface GetProductListService {
    Result getProductList(GetProductListUpload getProductListUpload);
}
