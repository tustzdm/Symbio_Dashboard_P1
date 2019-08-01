package com.symbio.dashboard.report.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartCommon;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListChartOther;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListList;
import com.symbio.dashboard.report.dro.saveUploadInformation.ListRowChart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QualityViewLayoutService {
    /**
     * 查询所有的reportChart列表
     * @return
     */
    Result getQualityViewLayout(String locale);

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
