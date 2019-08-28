package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.ScreenShot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ScreenShotRep extends JpaRepository<ScreenShot, Integer> {

    @Override
    void deleteById(Integer integer);

    @Transactional
    void deleteByTestRunId(Integer id);
}
