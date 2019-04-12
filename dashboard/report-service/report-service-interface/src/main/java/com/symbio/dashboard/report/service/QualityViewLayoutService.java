package com.symbio.dashboard.report.service;

import com.symbio.dashboard.report.dto.QualityViewLeyout.QualityViewLayout;
import org.springframework.stereotype.Service;

@Service
public interface QualityViewLayoutService {
    /**
     * 查询所有的reportChart列表
     * @return
     */
    QualityViewLayout findAll(String locale);
}
