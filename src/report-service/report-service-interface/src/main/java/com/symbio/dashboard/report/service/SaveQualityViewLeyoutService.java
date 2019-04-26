package com.symbio.dashboard.report.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartCommon;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartOther;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListList;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListRowChart;

import java.util.List;

public interface SaveQualityViewLeyoutService {
    /**
     * 根据前端上送信息，将页面布局存放到数据库中，然后给前台反馈
     *
     * @return
     */
    Result getSaveFeedback(String locale,
                           List<ListChartCommon> listChartCommon,
                           List<ListChartOther> listChartOther,
                           List<ListRowChart> listRowChart,
                           List<ListList> listList);


}
