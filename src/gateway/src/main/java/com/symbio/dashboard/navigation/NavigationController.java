package com.symbio.dashboard.navigation;

import com.symbio.dashboard.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/navigation")
@RestController
@Slf4j
public class NavigationController {

    @RequestMapping("/getProductList")
    public Result getProductList(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "locale",required = false,defaultValue = "en_US") String locale,
                                 @RequestParam(value = "total",required = false) Integer total) {
        Result result = new Result();



        return result;
    }


    @RequestMapping("/getReleaseList")
    public Result getReleaseList(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "productId") Integer productId,
                                 @RequestParam(value = "locale",required = false,defaultValue = "en_US") String locale,
                                 @RequestParam(value = "total",required = false) Integer total) {
        Result result = new Result();

        return result;
    }

    @RequestMapping("/getTestSetList")
    public Result getTestSetList(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "productId") Integer productId,
                                 @RequestParam(value = "releaseId") Integer releaseId,
                                 @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                 @RequestParam(value = "total", required = false) Integer total) {
        Result result = new Result();

        return result;
    }
}
