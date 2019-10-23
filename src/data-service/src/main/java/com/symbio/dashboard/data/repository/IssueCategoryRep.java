package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.IssueCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueCategoryRep extends JpaRepository<IssueCategory, Integer> {

    @Query(value = "SELECT * FROM issue_category WHERE product_id = 0 AND validation = 1 ORDER BY idx", nativeQuery = true)
    List<IssueCategory> getAllDefault();

    List<IssueCategory> findByProductIdAndValidation(Integer productId, Integer validation);

    @Query(value = "SELECT * FROM issue_category WHERE product_id = ?1 AND validation = 1 ORDER BY idx", nativeQuery = true)
    List<IssueCategory> getByProductId(Integer productId);

}
