package com.symbio.dashboard.repository;

import com.symbio.dashboard.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from product p order by p.update_time desc", nativeQuery = true)
    List<Product> findByOrderByUpdate_timeAtDesc();

    @Query(value = "select p.create_time from product p where p.id=?1",nativeQuery = true)
    Date getCreate_timeById(Integer id);

    @Query(value = "select p.id from product p",nativeQuery = true)
    List<Integer> getAllId();

    @Query(value = "select p.name from product p where p.id<>?1",nativeQuery = true)
    List<String> getAllName(Integer id);

    Product getById(Integer id);


}
