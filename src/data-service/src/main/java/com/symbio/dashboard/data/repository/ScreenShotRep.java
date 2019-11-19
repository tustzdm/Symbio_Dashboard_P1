package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.ScreenShot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ScreenShotRep extends JpaRepository<ScreenShot, Integer> {

    @Override
    void deleteById(Integer integer);

    @Transactional
    void deleteByTestResultId(Integer id);

    List<ScreenShot> getByTestResultId(Integer testResultId);

//    @Modifying
//    @Query(value = "UPDATE screen_shot SET status = ?2 WHERE id = ?1")
//    int updateScreenShotStatus(Integer id, Integer status);
}
