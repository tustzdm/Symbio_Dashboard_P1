package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.IssueCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueCategoryRep extends JpaRepository<IssueCategory, Integer> {

    @Query(value = "SELECT * FROM issue_category WHERE product_id = 0", nativeQuery = true)
    List<IssueCategory> getAllDefault();
}
