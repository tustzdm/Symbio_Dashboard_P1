package com.symbio.dashboard.navigation.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.navigation.dto.upload.ProductUpload;
import org.springframework.stereotype.Service;

@Service
public interface GetProductService {

    Result getProductList(ProductUpload productUpload);
}
