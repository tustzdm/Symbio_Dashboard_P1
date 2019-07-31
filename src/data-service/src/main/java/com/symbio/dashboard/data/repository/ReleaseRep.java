package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseRep extends JpaRepository<Release,Integer> {
    @Query(value = "select * from `release` r where r.product_id=?1 order by r.update_time desc", nativeQuery = true)
    List<Release> findByProduct_idAndOrderByUpdate_timeAtDesc(Integer product_id);

    @Query(value = "select r.id from `release` r", nativeQuery = true)
    List<Integer> getAllId();

    @Query(value = "select r.name from `release` r where r.id<>?1", nativeQuery = true)
    List<String> getAllName(Integer id);

    @Query(value = "select r.name from `release` r where r.product_id=?1", nativeQuery = true)
    List<String> getAllNamesByProductId(Integer productId);

    Release getById(Integer id);

    Page<Release> findByProductId(Integer productId, Pageable pageable);

    List<Release> findByProductId(Integer productId);

    @Query(value = "SELECT count(*) FROM `release` WHERE product_id = ?1 AND display = 1", nativeQuery = true)
    int getProductCount(Integer productId);

    @Query(value = "SELECT * FROM `release` WHERE display = 1 ORDER BY id", nativeQuery = true)
    List<Release> findAllRelease();

    @Query(value = "SELECT * FROM `release` WHERE product_id = ?1 AND display = 1 ORDER BY update_time DESC", nativeQuery = true)
    List<Release> getNavigationList(Integer productId);

    @Query(value = "SELECT * FROM `release` WHERE product_id = ?1 AND display = 1 ORDER BY update_time DESC LIMIT 0,?2", nativeQuery = true)
    List<Release> getNavigationPageList(Integer productId, Integer total);
}
