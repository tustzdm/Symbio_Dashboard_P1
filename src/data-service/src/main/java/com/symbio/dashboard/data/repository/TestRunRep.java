package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.TestRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRunRep extends JpaRepository<TestRun, Integer> {

    TestRun getById(Integer id);

}
