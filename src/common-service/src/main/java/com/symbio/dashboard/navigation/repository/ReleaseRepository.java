package com.symbio.dashboard.navigation.repository;

import com.symbio.dashboard.navigation.model.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReleaseRepository extends JpaRepository<Release,Integer> {
    @Query(value = "select * from `release` r where r.product_id=?1 order by r.update_time desc",nativeQuery = true)
    List<Release> findByProduct_idAndOrderByUpdate_timeAtDesc(Integer product_id);
}
