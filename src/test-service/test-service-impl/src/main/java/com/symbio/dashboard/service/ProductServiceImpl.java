package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.dao.ProductDao;
import com.symbio.dashboard.data.repository.ProductRep;
import com.symbio.dashboard.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName - ProductServiceImpl
 * @Author - Danny
 * @Description - Implementation of Product Service: show product list, add/edit/remove product
 * @Date - 2019/7/17 16:52
 * @Version 1.0
 */

@Service
@SuppressWarnings("unchecked")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductRep productRep;

    @Override
    public Result getProductList(Integer id) {
        return null;
    }

    @Override
    public Result editProduct(Product product) {
        return null;
    }

    @Override
    public Result removeProduct(Integer id) {
        try {
            productRep.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("100010", "Remove Error");
        }
        return new Result("Product Removed");
    }
}
