package com.symbio.dashboard.navigation.service.impl;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.navigation.dto.download.ProductMessage;
import com.symbio.dashboard.navigation.dto.upload.ProductUpload;
import com.symbio.dashboard.navigation.service.GetProductService;

public class GetProductServiceImpl implements GetProductService {
    @Override
    public Result getProductList(ProductUpload productUpload) {
        return null;
    }

    private Result getProductListResult(ProductUpload productUpload) {
        Result result = new Result();
        String token = productUpload.getToken();
        String locale = productUpload.getLocale();
        Integer total = productUpload.getTotal();

        //...
        //做相关的token操作





        return result;
    }

    private Result createProduct(Integer total) {
        Result result = new Result();
        ProductMessage productMessage = new ProductMessage();

        return result;
    }


}
