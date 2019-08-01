package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.service.ProductService;
import com.symbio.dashboard.test.service.ProductAuthService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName - ProductController
 * @Author - admin
 * @Description - Product UI controller
 * @Date - 2019/7/17 16:34
 * @Version 1.0
 */

@RequestMapping("/testmgmt")
@RestController
@Slf4j
public class ProductController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductAuthService productAuthService;
    @Autowired
    private ProductService productService;

    /**
     * 得到Product List 相关数据
     * @param token
     * @param locale
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/getProductList")
    public Result getProductList(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                 @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return getProductListBase(token, locale, pageIndex, pageSize);
    }

    @RequestMapping("/getProductPageList")
    public Result getProductPageList(@RequestParam(value = "token") String token,
                                     @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                     @RequestParam(value = "total", required = false) Integer pageIndex,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return getProductListBase(token, locale, pageIndex, pageSize);
    }

    @RequestMapping("/getProductInfo")
    public Result getProductInfo(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "locale",required = false,defaultValue = "en_US") String locale,
                                 @RequestParam(value = "id") Integer id) {
        Result result;
        Integer userId = 0;
        try {
            result = productAuthService.getProductListAuth(token);
            if (result.hasError()) {
                return result;
            }

            result = productService.getProductInfo(userId, id);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult("000102", "Product Info");
        }

        return result;
    }

    @RequestMapping("/updateProduct")
    public Result updateProduct(@RequestParam(value = "token") String token,
                              @RequestParam(value = "locale",required = false,defaultValue = "en_US") String locale,
                              @RequestBody Product product) {
        Result result;
        try {
            result = productAuthService.editProductAuth(token);
            if (result.hasError()) {
                return result;
            }

            // Set update user id
            Integer userId = 1;
            product.setUpdateUser(userId);

            result = productService.updateProduct(product);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("100012", "Edit Product Interface Exception");
        }

        return result;
    }

    @RequestMapping("/removeProduct")
    public Result removeProduct(@RequestParam(value = "token") String token,
                                @RequestParam(value = "locale",required = false,defaultValue = "en_US") String locale,
                                @RequestParam(value = "id") Integer id) {
        Result result;
        try {
            result = productAuthService.removeProductAuth(token);
            if (result.hasError()) {
                return result;
            }

            result = productService.removeProduct(id);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("100012", "Remove Product Interface Exception");
        }

        return result;
    }

    @RequestMapping("/getProductListDemo")
    public Result getProductListDemo(@RequestParam(value = "token") String token,
                                     @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                     @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return getProductListBase(token, locale, pageIndex, pageSize);
    }

    @RequestMapping("/getProductPieChart")
    public Result getProductPieChart(@RequestParam(value = "token") String token,
                                     @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale) {
        Integer userId = 0;
        return productService.getProductChart(userId, locale);
    }

    private Result getProductListBase(String token, String locale, Integer pageIndex, Integer pageSize) {
        logger.trace("getProductListBase() Enter. token = "+ token);

        Integer userId = 0;
        Result retResult = productService.getProductPageList2(userId, locale, pageIndex, pageSize);
        if(retResult.hasError()) {
            logger.debug(String.format("Get Error Info from productService. ec=%s, em=%s", retResult.getEc(), retResult.getEm()));
            if ("000121".equals(retResult.getEc())) {
                return getResult("000121", "Product");
            }
        }

        logger.trace("getProductListBase() Exit");
        return retResult;
    }

    @RequestMapping("/getProductUiInfo")
    public Result getProductUiInfo(@RequestParam(value = "token") String token,
                                   @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                   @RequestParam(value = "uiInfo", required = false, defaultValue = "1") Integer uiInfo,
                                   @RequestParam(value = "id", required = false, defaultValue = "0") Integer id) {
        Result result;
        Integer userId = 0;
        try {
            result = productAuthService.getProductListAuth(token);
            if (result.hasError()) {
                return result;
            }

            result = productService.getProductUiInfo(userId, locale, uiInfo, id);
            if (result.hasError()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult("000102", "Product UI Info");
        }

        return result;
    }

}
