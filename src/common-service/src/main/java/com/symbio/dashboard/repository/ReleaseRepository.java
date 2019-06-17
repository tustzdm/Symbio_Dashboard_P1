package com.symbio.dashboard.repository;

import com.symbio.dashboard.model.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ReleaseRepository extends JpaRepository<Release,Integer> {
    @Query(value = "select * from `releases` r where r.product_id=?1 order by r.update_time desc",nativeQuery = true)
    List<Release> findByProduct_idAndOrderByUpdate_timeAtDesc(Integer product_id);


    @Query(value = "select r.create_time from `releases` r where r.id=?1",nativeQuery = true)
    Date getCreate_timeById(Integer id);

    @Query(value = "select r.id from `releases` r", nativeQuery = true)
    List<Integer> getAllId();

    @Query(value = "select r.name from `releases` r where r.id<>?1",nativeQuery = true)
    List<String> getAllName(Integer id);


    Release getById(Integer id);


}
