package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.IssueReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueReasonRep extends JpaRepository<IssueReason, Integer> {

    @Query(value = "SELECT * FROM issue_reason WHERE issue_category_id = ?1", nativeQuery = true)
    List<IssueReason> getByCategoryId(Integer categoryId);


    @Query(value = "SELECT ir.* FROM issue_reason ir " +
            " INNER join issue_category ic on ir.issue_category_id = ic.id" +
            " WHERE ic.product_id = ?1 and ic.validation = 1 AND ir.validation = 1" +
            " ORDER BY ic.case_type, ic.idx", nativeQuery = true)
    List<IssueReason> getByProductId(Integer categoryId);

}
