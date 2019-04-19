package com.symbio.dashboard.report.repository;

import com.symbio.dashboard.report.modle.ReportChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ReportChartRepository extends JpaRepository <ReportChart,Integer>{

    @Query(value = "select r.page from report_chart r where r.key=?1",nativeQuery = true)
    String getPageByKey(String key);

    ReportChart getByKey(String key);
}
