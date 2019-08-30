package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.IssueReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueReasonRep extends JpaRepository<IssueReason, Integer> {

    @Query(value = "SELECT * FROM issue_reason WHERE issue_category_id = ?1", nativeQuery = true)
    List<IssueReason> getByCategoryId(Integer categoryId);
}
