package com.symbio.dashboard.report.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public interface StatisticsService {

    Result processTestSet();

    Result processRelease();

    Result processProduct();
}
