package com.symbio.dashboard.report.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public interface QualityViewLayoutService {
    /**
     * 查询所有的reportChart列表
     * @return
     */
    Result getQualityViewLayout(String locale);
}
