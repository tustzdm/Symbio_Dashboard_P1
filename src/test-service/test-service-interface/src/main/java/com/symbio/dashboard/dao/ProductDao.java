package com.symbio.dashboard.dao;

import com.symbio.dashboard.dto.upload.GetProductListUpload;
import com.symbio.dashboard.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductDao {

    List<Product> getProductList(GetProductListUpload getProductListUpload);

}
