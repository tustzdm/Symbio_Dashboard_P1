package com.symbio.dashboard.report.repository;

import com.symbio.dashboard.report.modle.ReportChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReportChartRepository extends JpaRepository <ReportChart,Integer>{

    @Query(value = "select r.page from report_chart r where r.key=?1",nativeQuery = true)
    String getPageByKey(String key);


    List<ReportChart> getByPageAndValidation(String page,Integer validation);

    @Query(value = "select r.key from report_chart r where r.page=?1 and r.validation=?2",nativeQuery = true)
    List<String> getKeyByPageAndValidation(String Page, Integer validation);
}
