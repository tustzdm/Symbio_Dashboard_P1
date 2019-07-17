package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.Product;
import com.symbio.dashboard.service.ProductService;
import com.symbio.dashboard.test.service.ProductAuthService;
import lombok.extern.slf4j.Slf4j;
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
public class ProductController {

    @Autowired
    private ProductAuthService productAuthService;
    @Autowired
    private ProductService productService;

    @RequestMapping("/getProductList")
    public Result getProductList(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "id") Integer id) {
        Result result;
        try {
            result = productAuthService.getProductListAuth(token);
            if (result.hasError()) {
                return new Result("100010", "Product Auth Error");
            }

            result = productService.getProductList(id);
            if (result.hasError()) {
                return new Result("100011", "Product List Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("100012", "Get Product List Interface Exception");
        }

        return result;
    }

    @RequestMapping("/editProduct")
    public Result editProduct(@RequestParam(value = "token") String token,
                              @RequestBody Product product) {
        Result result;
        try {
            result = productAuthService.editProductAuth(token);
            if (result.hasError()) {
                return new Result("100010", "Edit Product Auth Error");
            }

            result = productService.editProduct(product);
            if (result.hasError()) {
                return new Result("100011", "Edit Product Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("100012", "Edit Product Interface Exception");
        }

        return result;
    }

    @RequestMapping("/removeProduct")
    public Result removeProduct(@RequestParam(value = "token") String token,
                                @RequestParam(value = "id") Integer id) {
        Result result;
        try {
            result = productAuthService.removeProductAuth(token);
            if (result.hasError()) {
                return new Result("100010", "Remove Product Auth Error");
            }

            result = productService.removeProduct(id);
            if (result.hasError()) {
                return new Result("100011", "Remove Product Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("100012", "Remove Product Interface Exception");
        }

        return result;
    }

}
