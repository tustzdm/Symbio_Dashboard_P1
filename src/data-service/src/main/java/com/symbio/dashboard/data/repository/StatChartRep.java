package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.StatChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName - StatChartRep
 * @Author - Shawn
 * @Description
 * @Date - 2019/11/25
 * @Version 1.0
 */

@Repository
public interface StatChartRep extends JpaRepository<StatChart, Integer> {

    @Override
    StatChart getOne(Integer id);

}
