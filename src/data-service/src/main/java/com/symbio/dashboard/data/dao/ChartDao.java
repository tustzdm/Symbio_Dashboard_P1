package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.data.repository.ChartDataRep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@SuppressWarnings("unchecked")
@Slf4j
@Repository
public class ChartDao {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ChartDataRep chartDataRep;


}
