package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCaseRep extends JpaRepository<TestCase, Integer> {

    @Query(value = "SELECT id FROM test_case ORDER BY id", nativeQuery = true)
    List<Integer> getAllIds();

    @Query(value = "SELECT * FROM test_case WHERE case_id = ?1 AND case_type = ?2", nativeQuery = true)
    TestCase getTestCaseByCaseId(String caseId, Integer caseType);

    TestCase getByCaseIdAndCaseType(String caseId, Integer caseType);

    @Query(value = "SELECT COUNT(DISTINCT tc.id) from test_case tc INNER JOIN test_run tr ON tr.testcase_id = tc.id AND tr.validation = 1" +
            " WHERE tr.testset_id = ?1 AND tc.case_status = 0 AND tc.display = 1 AND tc.validation = 1", nativeQuery = true)
    Integer getTestCaseCountByTestSetId(Integer testSetId);
}
