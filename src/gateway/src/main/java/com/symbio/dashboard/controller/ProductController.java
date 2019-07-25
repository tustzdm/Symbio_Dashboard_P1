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
 * @Description - TODO
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

    @RequestMapping("/getProductList")
    public Result getProductList(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "locale",required = false,defaultValue = "en_US") String locale) {

        Integer userId = 0;
        Result retResult = productService.getProductList(userId, locale);
        return retResult;
    }

    @RequestMapping("/getProductPageList")
    public Result getProductPageList(@RequestParam(value = "token") String token,
                                     @RequestParam(value = "locale",required = false,defaultValue = "en_US") String locale,
                                     @RequestParam(value = "pageIndex",required = false) Integer pageIndex,
                                     @RequestParam(value = "pageSize",required = false) Integer pageSize) {

        Integer userId = 0;
        Result retResult = productService.getProductPageList(userId, locale, pageIndex, pageSize);
        return retResult;
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
            return new Result("000102", "Product Info");
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

        Integer userId = 0;
        Result retResult = productService.getProductPageList2(userId, locale, pageIndex, pageSize);
        if(retResult.hasError()) {
            return getResult("000120" , "Product List");
        }
        return retResult;
    }

}
