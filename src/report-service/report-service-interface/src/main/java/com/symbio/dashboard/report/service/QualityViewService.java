package com.symbio.dashboard.report.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.report.dro.getQualityOverview.Search;
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
    Result getQualityOverview(Map<String,Integer> role, String locale, Search search);

}
