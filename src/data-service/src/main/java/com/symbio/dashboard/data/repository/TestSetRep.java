package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.TestSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestSetRep extends JpaRepository<TestSet,Integer> {

    @Query(value = "select * from test_set ts where ts.release_id=?1 order by ts.update_time desc",nativeQuery = true)
    List<TestSet> findByRelease_idAndOrderByUpdate_timeAtDesc(Integer release_id);

    @Query(value = "select ts.name from test_set ts where ts.id<>?1",nativeQuery = true)
    List<String> getAllName(Integer id);

    TestSet getById(Integer id);

    Page<TestSet> findByReleaseId(Integer releaseId, Pageable pageable);

    List<TestSet> findByReleaseId(Integer releaseId);

    @Query(value = "select t.name from test_set t where t.release_id=?1", nativeQuery = true)
    List<String> getAllNamesByReleaseId(Integer releaseId);

    @Query(value = "SELECT count(*) FROM `test_set` WHERE release_id = ?1 AND display = 1", nativeQuery = true)
    int getReleaseCount(Integer releaseId);

    @Query(value = "SELECT product.id FROM product " +
            "INNER join `release` ON `release`.product_id = product.id " +
            "INNER join test_set ON test_set.release_id = `release`.id" +
            "WHERE test_set.id = ?1", nativeQuery = true)
    Integer getProductIdByTestSetId(Integer testSetId);

    @Query(value = "SELECT `type` FROM `test_set` WHERE id = ?1", nativeQuery = true)
    Integer getTypeById(Integer id);

}
