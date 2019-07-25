package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRep extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT count(*) FROM product WHERE display = 1", nativeQuery = true)
    int getCount();

    @Query(value = "select * from product p order by p.update_time desc", nativeQuery = true)
    List<Product> findAllOrderByUpdateTimeDesc();

    @Query(value = "select * from product p order by p.id", nativeQuery = true)
    List<Product> findAllOrderById();

    @Query(value = "select p.id from product p",nativeQuery = true)
    List<Integer> getAllId();

    @Query(value = "select p.name from product p", nativeQuery = true)
    List<String> getAllName();

    Product getById(Integer id);

    Page<Product> findAll(Pageable pageable);

}
