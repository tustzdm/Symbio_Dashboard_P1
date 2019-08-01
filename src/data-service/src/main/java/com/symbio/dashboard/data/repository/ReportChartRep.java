package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.ReportChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportChartRep extends JpaRepository<ReportChart, Integer> {

    @Query(value = "SELECT page FROM report_chart WHERE key = ?1", nativeQuery = true)
    String getPageByKey(String key);


    List<ReportChart> getByPageAndValidation(String page, Integer validation);

    @Query(value = "SELECT key FROM report_chart WHERE page = ?1 AND validation = ?2", nativeQuery = true)
    List<String> getKeyByPageAndValidation(String Page, Integer validation);
}
