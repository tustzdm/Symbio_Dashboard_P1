package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.TestRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRunRep extends JpaRepository<TestRun, Integer> {

    TestRun getById(Integer id);

    @Query(value = "SELECT * FROM test_run WHERE testcase_id = ?1 AND display = 1", nativeQuery = true)
    List<TestRun> getByTestCaseId(Integer testCaseId);

    @Query(value = "SELECT * FROM test_run WHERE testcase_id = ?1 AND locale = ?2 AND display = 1", nativeQuery = true)
    TestRun getByTestCaseIdAndLocale(Integer testCaseId, String locale);

    List<TestRun> getByTestsetIdAndTestcaseId(Integer testSetId, Integer testCaseId);

    @Query(value = "SELECT * FROM test_run WHERE testset_id = ?1 AND testcase_id = ?2 AND locale = ?3 AND display = 1", nativeQuery = true)
    TestRun getByTestSetIdAndTestCaseIdAndLocale(Integer testSetId, Integer testCaseId, String locale);
}
