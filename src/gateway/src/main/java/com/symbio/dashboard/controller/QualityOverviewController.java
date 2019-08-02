package com.symbio.dashboard.controller;


import com.symbio.dashboard.Result;
import com.symbio.dashboard.report.dro.getQualityOverview.Search;
import com.symbio.dashboard.report.dro.getQualityOverview.SearchCommon;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartCommon;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartOther;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListList;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListRowChart;
import com.symbio.dashboard.report.service.QualityViewLayoutService;
import com.symbio.dashboard.report.service.QualityViewService;
import com.symbio.dashboard.report.service.getQualityView.GetOverviewAuth;
import com.symbio.dashboard.report.service.getQualityView.GetOverviewAuthImpl;
import com.symbio.dashboard.report.service.getQualityViewLayout.GetOverviewLayoutAuth;
import com.symbio.dashboard.report.service.getQualityViewLayout.GetOverviewLayoutAuthImpl;
import com.symbio.dashboard.report.service.saveQualityViewLayout.SaveLayoutAuth;
import com.symbio.dashboard.report.service.saveQualityViewLayout.SaveLayoutAuthImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 本类用于对QualityOverview的一个控制，用于控制返回的内容信息
 *
 * @author daizongheng
 */


@Slf4j //用于添加日志信息
@RequiredArgsConstructor
@RestController //相当于@ResponseBody+@Controller
@RequestMapping("/menu")
@SuppressWarnings("unchecked")
public class QualityOverviewController {


    /**
     * 成员对象用于封装返回所有的json串内容，测试最终的getQualityOverview接口
     */
    @Autowired
    private QualityViewService qualityViewService;


    /**
     * 成员对象用于封装返回给前端的最终页面布局json串，测试最终getQualityViewLayout接口
     */
    @Autowired
    private QualityViewLayoutService qualityViewLayout;

    /**
     * 此方法用于调用返回总的QualityOverview类型数据，测试最终的json串
     *
     * 测试接口：
     *  localhost:8080/menu/getQualityOverview
     *
     * @return 返回给前端最终的QualityOverview类型对象的json串
     */

    @RequestMapping("/getQualityOverview")
    public Result getQualityOverview(@RequestParam(value = "token",required = false) String token,
                                              @RequestParam(value = "locale",required = false, defaultValue = "en_US") String locale,
                                              @RequestParam(value = "search",required = false) Search search){

        //根据token获得role权限
        GetOverviewAuth auth = new GetOverviewAuthImpl();
        Result result = auth.getOverview(token);
        if (result.hasError()) {
            return result;
        }
        Map<String,Integer> role = (Map<String, Integer>) result.getCd();

        Search search1 = new Search(0,new SearchCommon("","",""),null);

        return qualityViewService.getQualityOverview(role, locale, search1);
    }


    /**
     * 此方法用于调用返回总的QualityViewLayout类型数据，测试最终的json串
     *
     * 测试接口：
     *  localhost:8080/menu/getQualityViewLayout?token=aaa&locale=zh_cn
     *
     *  为了测试语种信息，url中暂时带一个String类型的local（语种信息）
     *
     *
     * @return 返回给前端最终的QualityViewLayout类型对象的json串
     */
    @RequestMapping("/getQualityViewLayout")
    public Result getQualityViewLayout(@RequestParam String token,
                                       @RequestParam(value = "locale",required = false, defaultValue = "en_US") String locale){

        GetOverviewLayoutAuth auth = new GetOverviewLayoutAuthImpl();
        Result result = auth.getLayoutAuth(token);
        if (result.hasError()) {
            return result;
        }

        return qualityViewLayout.getQualityViewLayout(locale);
    }


    /**
     * 本方法用于前端布局页面确定后调用接口，将页面布局信息存放到数据库中
     *
     * 测试接口：
     *  localhost:8080/menu/saveQualityViewLayout?token=aaa&locale=zh_cn
     *
     * @param token 用户token值 前台必须传
     * @param locale 语种 前台必须传
     * @param listChartCommons listChartCommon块布局
     * @param listChartOthers listChartOther块布局
     * @param listRowCharts listRowChart块布局
     * @param listLists listList块布局
     *
     * @return cd中存的是 返回给前端存储布局信息后的反馈信息
     */
    @RequestMapping("/saveQualityViewLayout")
    public Result saveQualityViewLayout(@RequestParam(value = "token") String token,
                                        @RequestParam(value = "locale", required = false, defaultValue = "en_US") String locale,
                                        @RequestParam(value = "listChartCommon",required = false) List<ListChartCommon> listChartCommons,
                                        @RequestParam(value = "listChartOther",required = false) List<ListChartOther> listChartOthers,
                                        @RequestParam(value = "listRowChart",required = false) List<ListRowChart> listRowCharts,
                                        @RequestParam(value = "listlist", required = false) List<ListList> listLists) {

        //权限认证
        SaveLayoutAuth auth = new SaveLayoutAuthImpl();
        Result result = auth.getRoleSaveLayout(token);
        if (result.hasError()) {
            return result;
        }


        //test
        Integer a[] = {1,2};
        Integer b[] = {1,3};
        List list = new LinkedList();
        ListChartCommon listChartCommon = new ListChartCommon();
        listChartCommon.setKey("StackedLine");
        listChartCommon.setPos(a);
        ListChartCommon listChartCommon1 = new ListChartCommon();
        listChartCommon1.setKey("BarLabRotation");
//        listChartCommon1.setKey("aaa");
        listChartCommon1.setPos(b);
        list.add(listChartCommon);
        list.add(listChartCommon1);

        List list2 = new LinkedList();
        List list3 = new LinkedList();
        List list4 = new LinkedList();



        return qualityViewLayout.getSaveFeedback(locale,list,list2,list3,list4);
    }


}