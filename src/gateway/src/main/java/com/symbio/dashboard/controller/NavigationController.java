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
 * @ClassName - NavigationController
 * @Author - admin
 * @Description - Navigation controller
 * @Date - 2019/7/26
 * @Version 1.0
 */

@RequestMapping("/navigation")
@RestController
@Slf4j
public class NavigationController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(NavigationController.class);

    @Autowired
    private ProductAuthService productAuthService;
    @Autowired
    private ProductService productService;

    /**
     * 得到 Product List 导航条数据
     * @param token
     * @param locale    en_US / zh_CN
     * @param total     null: (default setting)
     *                  0: all
     *                  其它: 最新N条记录
     * @return
     */
    @RequestMapping("/getProductNavigator")
    public Result getProductNavigator(@RequestParam(value = "token") String token,
                                      @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                      @RequestParam(value = "total", required = false) Integer total) {
        logger.trace("getProductNavigator() Enter. token = "+ token);
        logger.trace(String.format("locale = %s, total = %d", locale, total));

        Integer userId = 0;
        Result retResult = productService.getNavitionList(userId, locale, total);
        if(retResult.hasError()) {
            logger.debug(String.format("Get Error Info from productService. ec=%s, em=%s", retResult.getEc(), retResult.getEm()));
        }

        logger.trace("getProductNavigator() Exit");
        return retResult;
    }

}
