package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.dao.ProductDao;
import com.symbio.dashboard.data.repository.ProductRep;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Result getProductList(Integer userId, String locale) {
//        List<Product> list;
        List<Map<String, Object>> list;
        try {
//            list = productDao.getProductList();
            list = productDao.getProductUsers(2);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("100011", "Product list error");
        }
        return new Result(list);
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
        return null;
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
            result = verifyProductInfo(productInfo);
            if (result.hasError()) {
                return result;
            }

            // If id is null, add new Product
            if (id == null) {
                product = new Product();

                product.setCreateTime(date);
                product.setCreateUser(productInfo.getCreateUser());
                product.setCreateUserName(productInfo.getCreateUserName());

            } else {
                // Get existed Product object
                product = productRep.getById(id);
            }

            product.setName(productInfo.getName());
            product.setOwner(productInfo.getOwner());
            product.setManager(productInfo.getManager());
            product.setQaLead(productInfo.getQaLead());
            product.setDevLead(productInfo.getDevLead());
            product.setLogoId(productInfo.getLogoId());
            product.setLogoUrl(productInfo.getLogoUrl());
            product.setStatus(productInfo.getStatus());
            product.setDisplay(productInfo.getDisplay());
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
            productRep.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            if (match(e.getMessage())) {
                return new Result("100018", "Id does not exist");
            }
        }
        return new Result("Product Removed");
    }

    private boolean match(String text) {
        Pattern pattern = Pattern.compile("(No|entity|id|exists)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Result testProductEntity() {
        List<Product> listResult = productDao.getProductListBySql();
        return new Result(listResult);
    }

}
