package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.TestSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestSetRep extends JpaRepository<TestSet,Integer> {

    @Query(value = "select * from test_set ts where ts.release_id=?1 order by ts.update_time desc",nativeQuery = true)
    List<TestSet> findByRelease_idAndOrderByUpdate_timeAtDesc(Integer release_id);

    @Query(value = "select ts.name from test_set ts where ts.id<>?1",nativeQuery = true)
    List<String> getAllName(Integer id);

    TestSet getById(Integer id);

    Page<TestSet> findByReleaseId(Integer releaseId, Pageable pageable);

    List<TestSet> findByReleaseId(Integer releaseId);


}
