package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.ChartData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartDataRep extends JpaRepository<ChartData, Integer> {

    @Override
    ChartData getOne(Integer id);

    @Query(value = "SELECT * FROM chart_data WHERE chart_key = ?1 AND validation = 1 LIMIT 0,1", nativeQuery = true)
    ChartData getByKey(String key);
}
