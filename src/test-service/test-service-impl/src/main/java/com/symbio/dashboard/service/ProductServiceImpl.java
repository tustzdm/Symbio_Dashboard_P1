package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.business.CommonListDTOFactory;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.data.charts.BarChart;
import com.symbio.dashboard.data.charts.ChartGenerate;
import com.symbio.dashboard.data.charts.ChartProvider;
import com.symbio.dashboard.data.charts.PieChart;
import com.symbio.dashboard.data.charts.impl.ChartProviderFactory;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.dao.IssueDao;
import com.symbio.dashboard.data.dao.ProductDao;
import com.symbio.dashboard.data.dao.UserDao;
import com.symbio.dashboard.data.repository.ProductRep;
import com.symbio.dashboard.dto.CommonListDTO;
import com.symbio.dashboard.enums.*;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.model.SysListSetting;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName - ProductServiceImpl
 * @Description - Implementation of Product Service: show product list, add/edit/remove product
 * @Date - 2019/7/17 16:52
 * @Version 1.0
 */
@Slf4j
@Service
@SuppressWarnings("unchecked")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CommonDao commonDao;
    @Autowired
    private IssueDao issueDao;
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
    public Result getProductPageList(Integer userId, int pageIndex, int pageSize) {
        return getProductPageList(userId, Locales.EN_US.toString(), pageIndex, pageSize);
    }

    @Override
    public Result getProductInfo(Integer userId, Integer id) {
        Product product;
        try {
            product = productRep.getById(id);
            if (product == null || "".equals(product)) {
                return commonDao.getResult("000120", "Product Info");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return commonDao.getResult("200102", "Product Info");
        }

        return new Result(product);
    }

    @Override
    public Result updateProduct(Product productInfo) {
        String funcName = "ProductServiceImpl.updateProduct()";

        log.trace(funcName + " Enter");

        Result result = null;
        String strMsg = null;
        Boolean bAddNewProduct = false;

        try {
            Product product = productInfo;
            Integer userId = product.getUpdateUser();
            User userInfo = userDao.getUserById(userId);

            // If id is null, add new Product
            if (BusinessUtil.isIdEmpty(productInfo.getId())) {
                bAddNewProduct = true;
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
                product = productRep.saveAndFlush(product);

                // If new product, add issue category and reason at the same time
                if (bAddNewProduct) {
                    Result retCloneIssueInfo = issueDao.AddNewProductIssueCategory(product.getId());
                    if (!retCloneIssueInfo.isSuccess()) {
                        log.error(ErrorConst.getErrorLogMsg(funcName + " - AddNewProductIssueCategory", retCloneIssueInfo));
                    }
                }

                Map<String, Integer> map = new HashMap<>();
                map.put("id", product.getId());
                result = new Result(map);
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

        log.trace("ProductServiceImpl.updateProduct() Exit");
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
    public Result getProductPageList(Integer userId, String locale, Integer pageIndex, Integer pageSize) {

//        Result retResult = productDao.getProductList(userId, null, locale, pageIndex, pageSize);
//        if(retResult.hasError()) {
//            log.info(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
//        }
//        return retResult;

        String funcName = "ProductServiceImpl.getProductPageList()";

        log.trace(funcName + " Enter.");
        log.trace(String.format("Args: locale = %s, pageIndex = %d, pageSize = %d", locale, pageIndex, pageSize));

        CommonListDTO retProdDTO = CommonListDTOFactory.createNewCommonListDTO(locale, pageIndex, pageSize);
        Result retResult = new Result(retProdDTO);

        // Get Menu role
        Integer role = commonDao.getUserMenuRole(userId);
        retProdDTO.setRole(role);

        // Get fields info
        List<SysListSetting> listSetting = commonDao.getSystemSettingEntityInfo(SystemListSetting.Product);
        if (CommonUtil.isEmpty(listSetting)) {
            return retResult;
        }

        // Get Columns Info
        Result<List<SysListSetting>> resultListColInfo = commonDao.getSystemSettingColumnsInfo(SystemListSetting.Product);
        if (resultListColInfo.hasError()) {
            return resultListColInfo;
        } else {
            List<SysListSetting> listColumns = resultListColInfo.getCd();
            retProdDTO.setColumns(BusinessUtil.getListColumnInfo(role, locale, listColumns));
        }

        // Get field detail info
        List<String> listFields = CommonDao.getQueryFields(listSetting);
        if (CommonUtil.isEmpty(listFields)) {
            log.debug("There is no field to query");
            return retResult;
        }
        List<String> listUserFields = BusinessUtil.getQueryUserRefFields(listSetting);

        String strFields = String.join(",", listFields);
        Result retProductResult = productDao.getProductMapInfoByField(userId, strFields, pageIndex, pageSize, listUserFields);
        if (retProductResult.hasError()) {
            retResult = retProductResult;
        } else {
            CommonListDTO retProduct = (CommonListDTO) retProductResult.getCd();
            retProdDTO.setTotalRecord(retProduct.getTotalRecord());
            retProdDTO.setFields(retProduct.getFields());
            retProdDTO.setDataType(retProduct.getDataType());
            retProdDTO.setData(BusinessUtil.AppendOperation(EnumDef.OPERATION_TYPE.PRODUCT, role, retProduct.getData()));
            retProdDTO.setRole(role);
            retResult = new Result(retProdDTO);
        }

        // Get chart data
//        Result resultChartData = productDao.getChartData();
//        if (resultChartData.hasError()) {
//            return resultChartData;
//        } else {
//            List<Map<String, Object>> listChartData = (List<Map<String, Object>>) resultChartData.getCd();
//            retProdDTO.setChartData(listChartData);
//        }

        log.trace(funcName + " Exit");
        return retResult;
    }

    @Override
    public Result getNavigationList(Integer userId, String locale, Integer total){
        log.trace("ProductServiceImpl.getNavitionList Enter. total = %d", total);

        Result retResult = productDao.getNavigationList(userId, locale, total);
        if(retResult.hasError()) {
            log.info(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
        }
        return retResult;
    }

    @Override
    public Result getProductUiInfo(Integer userId, String locale, Integer uiInfo, Integer id) {
        Result retResult = productDao.getProductUiInfo(userId, locale, uiInfo, id);
        if (retResult.hasError()) {
            log.info(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
        }
        return retResult;
    }

    @Override
    public Result getProductChart(Integer userId, String locale, Integer productId, Integer releaseId, Integer testSetId) {
        String funcName = "ProductServiceImpl.getProductChart()";
        log.trace(funcName + " Enter.");
        log.debug("userId = {}, locale = {}, productId = {}", userId, locale, productId);

        List<Map<String, Object>> listChartData = new ArrayList();
        try {
            // Pie Chart
            Map<String, Object> mapPie = new HashMap<>();
            Result<Map<String, Object>> resultChartData = productDao.getPieDataFormatter(userId, locale, productId, releaseId, testSetId);
            if (resultChartData.hasError()) {
                return resultChartData;
            } else {
                mapPie = resultChartData.getCd();
            }

            // Abstract Factory
            ChartProvider chartProvider = new ChartProviderFactory();
            ChartGenerate chartGenerate = chartProvider.produce(ChartsType.PIE_REFER);
            Map<String, Object> mapData = chartGenerate.getChartMapData(mapPie);
            listChartData.add(mapData);

            // Bar Category Stack
            Map<String, Object> mapBar = new HashMap<>();
            resultChartData = productDao.getBarCategoryStack(userId, locale, productId, releaseId, testSetId);
            if (resultChartData.hasError()) {
                return resultChartData;
            } else {
                mapBar = resultChartData.getCd();
            }
            chartGenerate = chartProvider.produce(ChartsType.BAR_CATEGORY);
            mapData = chartGenerate.getChartMapData(mapBar);
            listChartData.add(mapData);

        } catch (Exception e) {
            e.printStackTrace();
            return commonDao.getResultArgs(locale, "000120", "Chart");
        }

        log.trace(funcName + " Exit.");
        return new Result(listChartData);
    }

}