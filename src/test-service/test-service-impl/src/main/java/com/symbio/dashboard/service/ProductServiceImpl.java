package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.charts.BarChart;
import com.symbio.dashboard.data.charts.PieChart;
import com.symbio.dashboard.data.dao.ProductDao;
import com.symbio.dashboard.data.dao.UserDao;
import com.symbio.dashboard.data.repository.ProductRep;
import com.symbio.dashboard.enums.EntityDisplay;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private UserDao userDao;
    @Autowired
    private PieChart pieChart;
    @Autowired
    private BarChart barChart;

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
        logger.trace("ProductServiceImpl.updateProduct() Enter");

        Result result = null;
        String strMsg = null;

        try {
            Product product = productInfo;
            Integer userId = product.getUpdateUser();
            User userInfo = userDao.getUserById(userId);

            // If id is null, add new Product
            if (BusinessUtil.isIdEmpty(productInfo.getId())) {
                strMsg = "Added";
                result = verifyProductInfo(productInfo);
                if (result.hasError()) {
                    return result;
                }
                product.setStatus(0); // 0: Normal
                product.setDisplay(EntityDisplay.SHOW.getValue());

                product.setCreateTime(new Date());
                if(userInfo != null) {
                    product.setCreateUser(userInfo.getId());
                    product.setCreateUserName(userInfo.getFullName());
                }
            } else {
                // Get existed Product object
                // ToDo: fetch entity, then set the value from UI
                // product = productRep.getById(productInfo.getId());
                strMsg = "Updated";

                if(product.getDisplay() == null) {
                    product.setDisplay(EntityDisplay.SHOW.getValue());
                }
                if(product.getStatus() == null) {
                    product.setStatus(0);
                }
            }
            strMsg = String.format("Product %s", strMsg);

            product.setUpdateTime(new Date());
            if(userInfo != null) {
                product.setUpdateUser(userInfo.getId());
                product.setUpdateUserName(userInfo.getFullName());
            }

            try {
                // Save or update
                productRep.saveAndFlush(product);
                result = new Result(strMsg);
            } catch (Exception e) {
                e.printStackTrace();
                if(e.getMessage().contains("product_name")) {
                    result = new Result("000123", "Product Name is duplicated. Name = " + productInfo.getName());
                } else {
                    result = new Result("000102", "Exception happened while operation on " + strMsg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("000002", strMsg + " Exception! " + e.getMessage());
        }

        logger.trace("ProductServiceImpl.updateProduct() Exit");
        return result;
    }

    private Result verifyProductInfo(Product productInfo) {
        Result retResult = null;

        if (StringUtil.isEmpty(productInfo.getName())) {
            retResult = new Result("100010", "Value of name cannot be empty");
        }

        retResult = new Result("Info verified");

        return retResult;
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
    public Result getNavigationList(Integer userId, String locale, Integer total){
        logger.trace("ProductServiceImpl.getNavitionList Enter. total = %d", total);

        Result retResult = productDao.getNavigationList(locale, total);
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
    public Result getProductChart(Integer userId, String locale) {

        Map<String, Object> map = new HashMap<>();
        List charts = new ArrayList();
        try {

            Map data = pieChart.setChartData(); //service.getReportData();
            Map data2 = barChart.setChartData(); //service.getReportData();
            Map pieMap = pieChart.getPieScrollLegendChart(userId, locale, data);
            Map barMap = barChart.getBarCategoryStackChart(userId, locale, data2);
            charts.add(pieMap);
            charts.add(barMap);
            map.put("charts", charts);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result("000120", "Chart");
        }
        return new Result(map);
    }

}