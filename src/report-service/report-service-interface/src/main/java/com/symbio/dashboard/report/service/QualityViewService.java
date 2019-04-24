package com.symbio.dashboard.report.service;

import com.symbio.dashboard.report.dro.getQualityOverview.Search;
import com.symbio.dashboard.report.dto.QualityOverview.QualityOverview;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface QualityViewService {

    /**
     * 根据上送信息将QualityOverview返回给前台
     * @param role
     * @param locale
     * @param search
     * @return
     */
    QualityOverview getQualityOverview(Map<String,Integer> role, String locale, Search search);


}
