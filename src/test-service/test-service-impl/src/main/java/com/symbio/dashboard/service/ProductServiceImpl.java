package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.dao.ProductDao;
import com.symbio.dashboard.data.repository.ProductRep;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductRep productRep;

    @Override
    public Result getProductList(Integer userId, String locale) {
        Map<String, Object> map;
        try {
            map = productDao.getFormatProductList();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("100011", "Product list error");
        }
        return new Result(map);
    }

    @Override
    public Result getProductList(Integer userId) {
        return getProductList(userId, Locales.EN_US.toString());
    }

    @Override
    public Result getProductPageList(Integer userId, String locale, int pageIndex, int pageSize) {

        return null;
    }

    @Override
    public Result getProductPageList(Integer userId, int pageIndex, int pageSize) {
        return getProductPageList(userId, Locales.EN_US.toString(), pageIndex, pageSize);
    }

    @Override
    public Result getProductInfo(Integer userId, Integer id) {
        Product product;
        try {
            product = productRep.getById(id);
            if (product == null || "".equals(product)) {
                return new Result("000120", "Product Info");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("200102", "Product Info");
        }

        return new Result(product);
    }

    @Override
    public Result updateProduct(Product productInfo) {
        Result result;
        Product product;
        Integer id = productInfo.getId();

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String time = simpleDateFormat.format(date);
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            // If id is null, add new Product
            if (id == null) {
                result = verifyProductInfo(productInfo);
                if (result.hasError()) {
                    return result;
                }
                product = new Product(0, 1);

                product.setCreateTime(date);
                product.setCreateUser(productInfo.getCreateUser());
                product.setCreateUserName(productInfo.getCreateUserName());

            } else {
                // Get existed Product object
                product = productRep.getById(id);
                if (!productInfo.getName().equalsIgnoreCase(product.getName())) {
                    result = verifyProductInfo(productInfo);
                    if (result.hasError()) {
                        return result;
                    }
                }
            }

            product.setName(productInfo.getName());
            product.setOwner(productInfo.getOwner());
            product.setManager(productInfo.getManager());
            product.setQaLead(productInfo.getQaLead());
            product.setDevLead(productInfo.getDevLead());
            product.setLogoId(productInfo.getLogoId());
            product.setLogoUrl(productInfo.getLogoUrl());
            product.setDescription(productInfo.getDescription());
            product.setLocale(productInfo.getLocale());
            product.setUpdateTime(date);
            product.setUpdateUser(productInfo.getUpdateUser());
            product.setUpdateUserName(productInfo.getUpdateUserName());

            int flag = 0;
            try {
                // Save or update
                productRep.saveAndFlush(product);
                if (id == null) {
                    flag++;
                }
                flag++;
            } catch (Exception e) {
                e.printStackTrace();
                return new Result("123456", "Product info cannot be saved");
            }

            if (flag == 1) {
                result = new Result("Product Updated");
            } else if (flag == 2) {
                result = new Result("Product Added");
            } else {
                return new Result("123456", "Error at flag" + flag);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result("100010", "Edit/Add product error");
        }
        return result;
    }

    private Result verifyProductInfo(Product productInfo) {

        if (productInfo.getName() == null) {
            return new Result("100010", "Value of name cannot be empty");
        }

        List<String> allNames = productRep.getAllName();
        if (allNames != null && !allNames.isEmpty()) {
            for (String name : allNames) {
                if (productInfo.getName().equalsIgnoreCase(name)) {
                    return new Result("100011", "Product name exists, please use another name");
                }
            }
        }

        return new Result("Info verified");
    }

    @Override
    public Result removeProduct(Integer id) {
        try {
            Product product = productRep.getById(id);
            product.setDisplay(0);
            productRep.saveAndFlush(product);
        } catch (Exception e) {
            e.printStackTrace();
            if (CommonUtil.match(e.getMessage(), "(No|entity|id|exists)")) {
                return new Result("100018", "Id does not exist");
            }
        }
        return new Result("Product Removed");
    }

    /**
     *
     * @return
     */
    public Result testProductEntity() {
        List<Product> listResult = productDao.getProductListBySql();
        return new Result(listResult);
    }

    @Override
    public Result getProductPageList2(Integer userId, String locale, Integer pageIndex, Integer pageSize) {
        Result retResult = productDao.getProductList2(locale, pageIndex, pageSize);
        if(retResult.hasError()) {
            logger.info(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
        }
        return retResult;
    }

    @Override
    public Result getNavitionList(Integer userId, String locale, Integer total){
        logger.trace("ProductServiceImpl.getNavitionList Enter. total = %d", total);

        Result retResult = productDao.getNavitionList(locale, total);
        if(retResult.hasError()) {
            logger.info(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
        }
        return retResult;
    }

    @Override
    public Result getProductUiInfo(Integer userId, String locale, Integer uiInfo, Integer id) {
        Result retResult = productDao.getProductUiInfo(userId, locale, uiInfo, id);
        if (retResult.hasError()) {
            logger.info(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
        }
        return retResult;
    }

    @Override
    public Result getProductPieChart(Integer userId, String locale, Integer... productId) {

        Result result;

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

}
