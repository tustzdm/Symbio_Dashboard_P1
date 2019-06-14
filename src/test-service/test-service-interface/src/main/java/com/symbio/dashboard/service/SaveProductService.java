package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.upload.SaveProductUpload;
import org.springframework.stereotype.Service;

@Service
public interface SaveProductService {

    Result saveProduct(SaveProductUpload saveProductUpload);

}
