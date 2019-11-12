package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.IssueCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueCategoryRep extends JpaRepository<IssueCategory, Integer> {

    @Query(value = "SELECT * FROM issue_category WHERE product_id = 0 AND validation = 1 ORDER BY idx", nativeQuery = true)
    List<IssueCategory> getAllDefault();

    List<IssueCategory> findByProductIdAndValidation(Integer productId, Integer validation);

    @Query(value = "SELECT * FROM issue_category WHERE product_id = ?1 AND validation = 1 ORDER BY idx", nativeQuery = true)
    List<IssueCategory> getByProductId(Integer productId);

    @Modifying
    @Query(value = "INSERT INTO issue_category(product_id, case_type, category, idx) " +
            " SELECT CAST(?1 AS CHAR) as product_id, case_type, category, idx FROM issue_category " +
            " WHERE product_id = 0 AND validation = 1 ORDER BY case_type, idx", nativeQuery = true)
    int copyCommonCategory(Integer productId);

    @Query(value = "SELECT ic.* FROM issue_category ic " +
            " INNER JOIN product ON ic.product_id = product.id" +
            " INNER JOIN `release` re ON re.product_id = product.id" +
            " INNER JOIN test_set ts ON ts.release_id = re.id AND ts.type = ic.case_type" +
            " WHERE ts.id = ?1 AND ic.validation = 1 ORDER BY ic.idx", nativeQuery = true)
    List<IssueCategory> getListByTestSetId(Integer testSetId);


}
