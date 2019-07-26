package com.symbio.dashboard.navigation;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.navigation.dto.upload.ProductUpload;
import com.symbio.dashboard.navigation.dto.upload.ReleaseUpload;
import com.symbio.dashboard.navigation.dto.upload.TestSetUpload;
import com.symbio.dashboard.navigation.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/navigationController")
@RestController
@Slf4j
public class NavigationController2 {

    @Autowired
    private GetProductAuthService getProductAuth;

    @Autowired
    private GetProductService getProductService;

    @Autowired
    private GetReleaseAuthService getReleaseAuth;

    @Autowired
    private GetReleaseService getReleaseService;

    @Autowired
    private GetTestSetAuthService getTestSetAuth;

    @Autowired
    private GetTestSetService getTestSetService;

    /**
     * 此方法为导航条模块中的product获得相应的信息
     *
     * 测试接口：
     *  localhost:8080/navigation/getProductList?token=aaa&total=3
     *
     * @param token 用户
     * @param locale 语种
     * @param total 需要返回的长度大小
     *
     * @return 返回一个result 内容为导航条中product中所需要的返回信息
     */
    @RequestMapping("/getProductList")
    public Result getProductList(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "locale",required = false,defaultValue = "en_US") String locale,
                                 @RequestParam(value = "total",required = false) Integer total) {
        Result result = getProductAuth.getProductAuth(token);

        if (!"0".equals(result.getEc())) {
            return result;
        }

        ProductUpload productUpload = new ProductUpload();
        productUpload.setToken(token);
        productUpload.setLocale(locale);
        productUpload.setTotal(total);

        result = getProductService.getProductList(productUpload);


        return result;
    }


    /**
     * 此方法为控制导航条中的release的信息
     *
     * 测试接口：
     *  localhost:8080/navigation/getReleaseList?token=aaa&productId=1&total=1
     *
     * @param token 用户
     * @param productId product的id
     * @param locale 语种
     * @param total 需要查询的记录的长度
     * @return 返回一个result 内容为导航条需要的的release的返回信息
     */
    @RequestMapping("/getReleaseList")
    public Result getReleaseList(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "productId") Integer productId,
                                 @RequestParam(value = "locale",required = false,defaultValue = "en_US") String locale,
                                 @RequestParam(value = "total",required = false) Integer total) {
        Result result = getReleaseAuth.getReleaseAuth(token);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        ReleaseUpload releaseUpload = new ReleaseUpload();
        releaseUpload.setLocale(locale);
        releaseUpload.setProductId(productId);
        releaseUpload.setToken(token);
        releaseUpload.setTotal(total);

        result = getReleaseService.getReleaseList(releaseUpload);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        return result;
    }


    /**
     * 此方法为控制返回导航条中的test_set信息集合，用于返回其所需要的信息
     *
     * 测试接口：
     *  localhost:8080/navigation/getTestSetList?token=aaa&productId=1&releaseId=2&total=3
     *
     * @param token 用户
     * @param productId product_id
     * @param releaseId release_id
     * @param locale 语言
     * @param total 所需要的信息长度大小
     *
     * @return 返回testset的信息集合
     */
    @RequestMapping("/getTestSetList")
    public Result getTestSetList(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "productId") Integer productId,
                                 @RequestParam(value = "releaseId") Integer releaseId,
                                 @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                 @RequestParam(value = "total", required = false) Integer total) {
        Result result = getTestSetAuth.getTestSetAuth(token);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        TestSetUpload testSetUpload = new TestSetUpload();
        testSetUpload.setToken(token);
        testSetUpload.setLocale(locale);
        testSetUpload.setProductId(productId);
        testSetUpload.setReleaseId(releaseId);
        testSetUpload.setTotal(total);

        result = getTestSetService.getTestSetList(testSetUpload);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        return result;
    }


    @RequestMapping("/getNavigationInfo")
    public Result getNavigationInfo(@RequestParam(value = "token") String token,
                                    @RequestParam(value = "locale",required = false,defaultValue = "en_US") String locale) {
        Result result = new Result();

        return result;
    }
}
