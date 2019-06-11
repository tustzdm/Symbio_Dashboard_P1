package com.symbio.dashboard.navigation.repository;

import com.symbio.dashboard.navigation.model.TestSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestSetRepository extends JpaRepository<TestSet,Integer> {

    @Query(value = "select * from test_set ts where ts.release_id=?1 order by ts.update_time desc",nativeQuery = true)
    List<TestSet> findByRelease_idAndOrderByUpdate_timeAtDesc(Integer release_id);
}
