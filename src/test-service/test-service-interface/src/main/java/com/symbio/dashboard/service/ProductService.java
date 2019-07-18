package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    Result getProductList(String locale);

    Result getProductPageList(String locale, int pageIndex, int pageSize);

    Result getProductInfo(Integer id);

    Result editProduct(Product product);

    Result removeProduct(Integer id);

}
