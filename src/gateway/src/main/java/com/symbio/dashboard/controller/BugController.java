package com.symbio.dashboard.controller;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.ListQueryVO;
import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.service.BugService;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName - BugController
 * @Description - User 控制器
 * @Date - 2019/11/29
 * @Version 1.0
 */

@RequestMapping("/bug")
@RestController
@Slf4j
public class BugController extends BaseController {

    @Autowired
    private BugService bugService;

    @GetMapping("/getList")
    public Result getList(@RequestBody ListQueryVO query) {
        String funcName = "BugController.getList()";
        log.info(funcName + " Enter");

        Result retResult = new Result();
        try {
            String token = query.getToken();
            Result resultUserId = super.getUserIdByToken(token);
            if (resultUserId.hasError()) {
                return resultUserId;
            }
            Integer userId = (Integer) resultUserId.getCd();

            // default value
            if (CommonUtil.isEmpty(query.getLocale())) {
                query.setLocale(Locales.EN_US.toString());
            }
            if (CommonUtil.isEmpty(query.getPageIndex())) {
                query.setPageIndex(0);
            }
            if (CommonUtil.isEmpty(query.getPageSize())) {
                query.setPageSize(20);
            }

            retResult = bugService.getList(userId, query);
            if (retResult.hasError()) {
                log.error(ErrorConst.getErrorLogMsg(funcName, retResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.trace(funcName + " Exit");
        return retResult;
    }

}
