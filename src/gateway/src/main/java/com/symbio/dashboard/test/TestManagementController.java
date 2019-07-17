package com.symbio.dashboard.test;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dto.upload.*;
import com.symbio.dashboard.service.*;
import com.symbio.dashboard.test.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/testmgmr")
@RestController
@Slf4j
public class TestManagementController {
    @Autowired
    private GetProductListAuthService getProductListAuthService;
    @Autowired
    private GetProductListService getProductListService;
    @Autowired
    private GetProductInfoAuthService getProductInfoAuthService;
    @Autowired
    private GetProductInfoService getProductInfoService;
    @Autowired
    private SaveProductAuthService saveProductAuthService;
    @Autowired
    private SaveProductService saveProductService;



    @Autowired
    private GetReleaseListAuthService getReleaseListAuthService;
    @Autowired
    private GetReleaseListService getReleaseListService;
    @Autowired
    private GetReleaseInfoAuthService getReleaseInfoAuthService;
    @Autowired
    private GetReleaseInfoService getReleaseInfoService;
    @Autowired
    private SaveReleaseAuthService saveReleaseAuthService;
    @Autowired
    private SaveReleaseService saveReleaseService;


    @Autowired
    private GetTestSetListAuthService getTestSetListAuthService;
    @Autowired
    private GetTestSetListService getTestSetListService;
    @Autowired
    private SaveTestSetAuthService saveTestSetAuthService;
    @Autowired
    private SaveTestSetService saveTestSetService;


    //6.1

    /**
     * 此方法用于获得product的分页查询结果
     *
     * 测试接口：
     *  localhost:8080/testmgmr/getProductList?token=111&locale=en_US&pageIndex=0&pageSize=2
     *
     * @param token 用户token
     * @param locale 语种
     * @param pageIndex 页面索引
     * @param pageSize 页面大小
     *
     * @return 返回product的分页查询结果给前台
     */
    @RequestMapping("/getProductList")
    public Result getProductList(@RequestBody GetProductListUpload getProductListUpload) {
        Result result;
        result = getProductListAuthService.getProductList(getProductListUpload.getToken());
        if (!"0".equals(result.getEc())) {
            return result;
        }

        result = getProductListService.getProductList(getProductListUpload);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        return result;
    }

    //6.2

    /**
     *此方法用于获得产品的信息
     *
     * 测试接口：
     *   localhost:8080/testmgmr/getProductInfo?token=aa&uiInfo=0&productId=1
     *
     * @param token 用户token
     * @param locale 语种
     * @param uiInfo ui信息是否需要，0 不需要，1需要
     * @param productId product id
     *
     * @return 返回一个查询后的结果
     */
    @RequestMapping("/getProductInfo")
    public Result getProductInfo(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "locale",defaultValue = "en_US",required = false) String locale,
                                 @RequestParam(value = "uiInfo",required = false) Integer uiInfo,
                                 @RequestParam(value = "productId",required = false) Integer productId) {
        Result result ;
        result = getProductInfoAuthService.getProductInfo(token);
        if (!"0".equals(result.getEc())) {
            return result;
        }
        GetProductInfoUpload getProductInfoUpload = new GetProductInfoUpload();
        getProductInfoUpload.setToken(token);
        getProductInfoUpload.setLocale(locale);
        getProductInfoUpload.setUiInfo(uiInfo);
        getProductInfoUpload.setProductId(productId);

        return result;
    }

    //6.3

    /**
     * 此方法用于testManagement模块中的add或者edit product模块
     *
     * 测试接口：
     *  localhost:8080/testmgmr/saveProduct?token=111&owner=1&manager=1&qaleader=1&devleader=1&description=aaaaa&name=ggg&productId=
     *
     * @param token 用户
     * @param locale 语种
     * @param productId product id
     * @param name 项目名称
     * @param owner 项目归属者
     * @param manager 项目管理
     * @param qaleader qa
     * @param devleader 开发者
     * @param description 描述
     *
     * @return 存储或者修改成功返回success 否则返回相应的报错信息
     */
    @RequestMapping("/saveProduct")
    public Result saveProduct(@RequestParam(value = "token") String token,
                              @RequestParam(value = "locale",defaultValue = "en_US",required = false) String locale,
                              @RequestParam(value = "productId") Integer productId,
                              @RequestParam(value = "name") String name,
                              @RequestParam(value = "owner") Integer owner,
                              @RequestParam(value = "manager") Integer manager,
                              @RequestParam(value = "qaleader") Integer qaleader,
                              @RequestParam(value = "devleader") Integer devleader,
                              @RequestParam(value = "description") String description) {
        Result result = saveProductAuthService.saveProduct(token);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        SaveProductUpload saveProductUpload = new SaveProductUpload();
        //写死
//        token = "111";
//        locale = "en_US";
//        productId = null;
//        name = "bbb";
//        owner = 1;
//        manager = 1;
//        qaleader = 1;
//        devleader = 1;
//        description = "描述";

        saveProductUpload.setToken(token);
        saveProductUpload.setLocale(locale);
        saveProductUpload.setProductId(productId);
        saveProductUpload.setName(name);
        saveProductUpload.setOwner(owner);
        saveProductUpload.setManager(manager);
        saveProductUpload.setQaleader(qaleader);
        saveProductUpload.setDevleader(devleader);
        saveProductUpload.setDescription(description);

        result = saveProductService.saveProduct(saveProductUpload);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        return result;
    }

    //6.4

    /**
     * 此方法用于返回release的信息，分页返回
     *
     * 测试接口：
     *  localhost:8080/testmgmr/getReleaseList?token=111&productId=1&pageIndex=0&pageSize=2
     *
     * @param token 用户token
     * @param locale 语种
     * @param productId product 的id
     * @param pageIndex 页面索引
     * @param pageSize 页面大小
     * @return
     */
    @RequestMapping("/getReleaseList")
    public Result getReleaseList(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "locale",required = false,defaultValue = "en_US") String locale,
                                 @RequestParam(value = "productId") Integer productId,
                                 @RequestParam(value = "pageIndex",required = false) Integer pageIndex,
                                 @RequestParam(value = "pageSize",required = false) Integer pageSize) {
        Result result ;

        result = getReleaseListAuthService.getReleaseList(token);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        GetReleaseListUpload getReleaseListUpload = new GetReleaseListUpload();
        getReleaseListUpload.setLocale(locale);
        getReleaseListUpload.setToken(token);
        getReleaseListUpload.setProductId(productId);
        getReleaseListUpload.setPageIndex(pageIndex);
        getReleaseListUpload.setPageSize(pageSize);

        result = getReleaseListService.getReleaseList(getReleaseListUpload);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        return result;
    }

    //6.5

    /**
     * 此方法用于返回release的信息
     *
     * 测试接口：
     *  localhost:8080/testmgmr/getReleaseInfo?token=aaa&uiInfo=1&releaseId=29
     *
     * @param token 用户token
     * @param locale 语种
     * @param uiInfo 是否需要ui信息
     * @param productId product id
     * @param releaseId release id
     * @return 返回最终的release的信息
     */
    @RequestMapping("/getReleaseInfo")
    public Result getReleaseInfo(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "locale",defaultValue = "en_US",required = false) String locale,
                                 @RequestParam(value = "uiInfo",required = false) Integer uiInfo,
                                 @RequestParam(value = "productId",required = false) Integer productId,
                                 @RequestParam(value = "releaseId",required = false) Integer releaseId) {
        Result result;
        result = getReleaseInfoAuthService.getReleaseInfoAuth(token);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        GetReleaseInfoUpload getReleaseInfoUpload = new GetReleaseInfoUpload();
        getReleaseInfoUpload.setLocale(locale);
        getReleaseInfoUpload.setToken(token);
        getReleaseInfoUpload.setProductId(productId);
        getReleaseInfoUpload.setReleaseId(releaseId);
        getReleaseInfoUpload.setUiInfo(uiInfo);

        result = getReleaseInfoService.getReleaseInfo(getReleaseInfoUpload);
        if (!"0".equals(result.getEc())) {
            return result;
        }


        return result;
    }

    //6.6

    /**
     * 此方法用于testManagement模块中的add或者edit release模块
     *
     * 测试接口：
     *  localhost:8080/testmgmr/saveRelease?token=111&locale=en_US&name=aa&productId=1&releaseId=
     *
     * @param token 用户
     * @param locale 语种
     * @param productId product id
     * @param releaseId release id
     * @param name release name
     * @param status 状态
     * @param start 开始时间
     * @param end 结束时间
     * @param description 描述
     *
     * @return 返回一个是否存储成功的信息给前台
     */
    @RequestMapping("/saveRelease")
    public Result saveRelease(@RequestParam(value = "token") String token,
                              @RequestParam(value = "locale") String locale,
                              @RequestParam(value = "productId") Integer productId,
                              @RequestParam(value = "releaseId") Integer releaseId,
                              @RequestParam(value = "name") String name,
                              @RequestParam(value = "status",defaultValue = "1",required = false) Integer status,
                              @RequestParam(value = "start",required = false) String start,
                              @RequestParam(value = "end",required = false) String end,
                              @RequestParam(value = "description",required = false) String description) {
        Result result = saveReleaseAuthService.saveReleaseAuth(token);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        SaveReleaseUpload saveReleaseUpload = new SaveReleaseUpload();

//        start = "2018-12-31";
//        end = "2019-03-25";

        saveReleaseUpload.setToken(token);
        saveReleaseUpload.setLocale(locale);
        saveReleaseUpload.setProductId(productId);
        saveReleaseUpload.setReleaseId(releaseId);
        saveReleaseUpload.setName(name);
        saveReleaseUpload.setStatus(status);
        saveReleaseUpload.setStart(start);
        saveReleaseUpload.setEnd(end);
        saveReleaseUpload.setDescription(description);

        result = saveReleaseService.saveRelease(saveReleaseUpload);

        if (!"0".equals(result.getEc())) {
            return result;
        }


        return result;
    }

    //6.7
    /**
     * 此方法用于分页查询返回testSet的内容
     *
     * 测试接口：
     *  localhost:8080/testmgmr/getTestSetList?token=111&releaseId=1&pageIndex=0&pageSize=2
     *
     * @param token 用户token
     * @param locale 语种
     * @param releaseId release id
     * @param pageIndex 页面索引
     * @param pageSize 页面大小
     * @return 返回分页好的testSet内容和页面的信息
     */
    @RequestMapping("/getTestSetList")
    public Result getTestSetList(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "locale",required = false,defaultValue = "en_US") String locale,
                                 @RequestParam(value = "releaseId") Integer releaseId,
                                 @RequestParam(value = "pageIndex",required = false) Integer pageIndex,
                                 @RequestParam(value = "pageSize",required = false) Integer pageSize) {
        Result result = new Result();

        result = getTestSetListAuthService.getTestSetList(token);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        GetTestSetListUpload getTestSetListUpload = new GetTestSetListUpload();
        getTestSetListUpload.setToken(token);
        getTestSetListUpload.setLocale(locale);
        getTestSetListUpload.setReleaseId(releaseId);
        getTestSetListUpload.setPageIndex(pageIndex);
        getTestSetListUpload.setPageSize(pageSize);

        result = getTestSetListService.getTestSetList(getTestSetListUpload);
        if (!"0".equals(result.getEc())) {
            return result;
        }


        return result;
    }

    //6.8
    @RequestMapping("/getTestSetInfo")
    public Result getTestSetInfo(@RequestParam(value = "token") String token) {
        Result result = new Result();

        return result;
    }

    //6.9

    /**
     * 此方法用于testManagement模块中的add或者edit testSet模块
     *
     * 测试接口：
     *  localhost:8080/testmgmr/saveTestSet?token=111&locale=en_US&type=0&status=1&name=aa&releaseId=1&testSetId=
     *
     * @param token
     * @param locale
     * @param releaseId
     * @param testSetId
     * @param name
     * @param type
     * @param status
     * @param startDate
     * @param endDate
     * @param testOwner
     * @param jiraProduct
     * @param bugAssignee
     * @param description
     * @param extend
     * @return
     */
    @RequestMapping("/saveTestSet")
    public Result saveTestSet(@RequestParam(value = "token") String token,
                              @RequestParam(value = "locale",defaultValue = "en_US") String locale,
                              @RequestParam(value = "releaseId") Integer releaseId,
                              @RequestParam(value = "testSetId") Integer testSetId,
                              @RequestParam(value = "name") String name,
                              @RequestParam(value = "type",defaultValue = "0") Integer type,
                              @RequestParam(value = "status",defaultValue = "0") Integer status,
                              @RequestParam(value = "startDate",required = false) String startDate,
                              @RequestParam(value = "endDate",required = false) String endDate,
                              @RequestParam(value = "testOwner",required = false) Integer testOwner,
                              @RequestParam(value = "jiraProduct",required = false) String jiraProduct,
                              @RequestParam(value = "bugAssignee",required = false) Integer bugAssignee,
                              @RequestParam(value = "description",required = false) String description ,
                              @RequestParam(value = "extend",required = false) String extend) {
        Result result;
        result = saveTestSetAuthService.saveTestSetAuth(token);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        SaveTestSetUpload saveTestSetUpload = new SaveTestSetUpload();
        saveTestSetUpload.setToken(token);
        saveTestSetUpload.setLocale(locale);
        saveTestSetUpload.setReleaseId(releaseId);
        saveTestSetUpload.setTestSetId(testSetId);
        saveTestSetUpload.setName(name);
        saveTestSetUpload.setType(type);
        saveTestSetUpload.setStatus(status);
        saveTestSetUpload.setStartDate(startDate);
        saveTestSetUpload.setEndDate(endDate);
        saveTestSetUpload.setTestOwner(testOwner);
        saveTestSetUpload.setJiraProduct(jiraProduct);
        saveTestSetUpload.setButAssignee(bugAssignee);
        saveTestSetUpload.setDescription(description);
        saveTestSetUpload.setExtend(extend);

        result = saveTestSetService.saveTestSet(saveTestSetUpload);
        if (!"0".equals(result.getEc())) {
            return result;
        }

        return result;
    }


    //6.13
    @RequestMapping("/getTestResultList")
    public Result getTestResultList(@RequestParam(value = "token") String token) {
        Result result = new Result();

        return result;
    }

    //6.14
    @RequestMapping("/getTRFilterList")
    public Result getTRFilterList(@RequestParam(value = "token") String token) {
        Result result = new Result();

        return result;
    }


    //6.15
    @RequestMapping("/getJiraJobInfo")
    public Result getJiraJobInfo(@RequestParam(value = "token") String token) {
        Result result = new Result();

        return result;
    }


    //6.16
    @RequestMapping("/getJiraJobSetting")
    public Result getJiraJobSetting(@RequestParam(value = "token") String token) {
        Result result = new Result();

        return result;
    }
}

