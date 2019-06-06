package com.symbio.dashboard.navigation.repository;

import com.symbio.dashboard.navigation.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {

}
