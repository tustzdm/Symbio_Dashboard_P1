package com.symbio.dashboard.report.service;

import com.symbio.dashboard.report.dto.saveQualityViewLeyout.SaveQualityViewLeyout;

import java.util.List;

public interface SaveQualityViewLeyoutService {
    /**
     * 根据前端上送信息，将页面布局存放到数据库中，然后给前台反馈
     *
     * @return
     */
    SaveQualityViewLeyout getSaveFeedback(String token, String locale, List listChartCommon,
                                          List listChartOther,List listRowChart,List listList);


}
