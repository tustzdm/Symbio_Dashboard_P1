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

    @Query(value = "SELECT * FROM test_case WHERE case_id=?1 AND case_type=?2", nativeQuery = true)
    TestCase getTestCaseByCaseId(String caseId, Integer caseType);

    TestCase getByCaseIdAndCaseType(String caseId, Integer caseType);
}
