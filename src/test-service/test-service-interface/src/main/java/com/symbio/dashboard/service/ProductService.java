package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    Result getProductList(Integer userId, String locale);

    Result getProductList(Integer userId);

    Result getProductPageList(Integer userId, String locale, int pageIndex, int pageSize);

    Result getProductPageList(Integer userId, int pageIndex, int pageSize);

    Result getProductInfo(Integer userId, Integer id);

    Result updateProduct(Product product);

    Result removeProduct(Integer id);

    // Temp
    Result getProductPageList2(Integer userId, String locale, Integer pageIndex, Integer pageSize);

    Result getNavitionList(Integer userId, String locale, Integer total);

    Result getProductUiInfo(Integer userId, String locale, Integer uiInfo, Integer id);

    Result getProductPieChart(Integer userId, String locale, Integer... productId);

}
