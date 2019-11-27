package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName - TestResultRep
 * @Description
 * @Date - 2019/8/22 14:57
 * @Version 1.0
 */

@Repository
public interface TestResultRep extends JpaRepository<TestResult, Integer> {

    TestResult getById(Integer id);

    TestResult getByTestRunId(Integer testRunId);
}
