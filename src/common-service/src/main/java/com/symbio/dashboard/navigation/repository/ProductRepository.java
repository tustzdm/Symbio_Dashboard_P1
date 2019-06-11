package com.symbio.dashboard.navigation.repository;

import com.symbio.dashboard.navigation.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from product p order by p.update_time desc", nativeQuery = true)
    List<Product> findByOrderByUpdate_timeAtDesc();
}
