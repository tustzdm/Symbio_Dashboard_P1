package com.symbio.dashboard.navigation.service.impl;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.navigation.dto.download.ProductData;
import com.symbio.dashboard.navigation.dto.download.ProductMessage;
import com.symbio.dashboard.navigation.dto.upload.ProductUpload;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.repository.ProductRepository;
import com.symbio.dashboard.navigation.service.GetProductService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GetProductServiceImpl implements GetProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Result getProductList(ProductUpload productUpload) {
        return getProductListResult(productUpload);
    }

    /**
     * 此方法为封装导航条中的product返回信息
     *
     * @param productUpload product的上送信息
     *
     * @return 返回product的全部信息
     */
    private Result getProductListResult(ProductUpload productUpload) {
        Result result = new Result();
        String token = productUpload.getToken();
        String locale = productUpload.getLocale();
        //记录数
        Integer total = productUpload.getTotal();


        Result productList = createProductList(total);
        if (!productList.getEc().equals("0")) {
            return productList;
        }
        //此对象可能是后面返回来的已有某些内容的对象
        ProductMessage productMessage = (ProductMessage) productList.getCd();
        productMessage.setLocale(locale);

        //...
        //做相关的token操作
        productMessage.setRole(token);

        result.setCdAndRightEcAndEm(productMessage);

        return result;
    }

    /**
     * 此方法用于创建product返回信息中的部分信息,封装了isShowMore和data
     *
     * @param total 查询数量
     *
     * @return 返回一个只有部分信息的product返回信息
     */
    private Result createProductList(Integer total) {
        Result result = new Result();
        ProductMessage productMessage = new ProductMessage();
        boolean isShowMore = false;

        List<ProductData> list = new LinkedList<>();
        if (total == null) {
            //默认设置

            productMessage.setIsShowMore(isShowMore);

            result.setCdAndRightEcAndEm(productMessage);
            return result;
        }

        List<Product> productList = productRepository.findByOrderByUpdate_timeAtDesc();

        int flag = productList.size();
        if (total == 0) {
            list = createProductData(productList, flag);
        } else if (total < flag) {
            isShowMore = true;
            list = createProductData(productList, total);
        } else if (total >= flag) {
            list = createProductData(productList, flag);
        }

        productMessage.setIsShowMore(isShowMore);
        productMessage.setData(list);

        result.setCdAndRightEcAndEm(productMessage);

        return result;
    }

    /**
     * 本方法用于封装一个Product中的data
     *
     * @param productList 实体类列表
     * @param size data的长度
     *
     * @return 返回一个封装好的data
     */
    private List<ProductData> createProductData(List<Product> productList,int size) {
        List<ProductData> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            ProductData productData = new ProductData();
            productData.setId(productList.get(i).getId());
            productData.setName(productList.get(i).getName());
            list.add(productData);
        }
        return list;
    }


}
