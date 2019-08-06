package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.TestRun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRunRep extends JpaRepository<TestRunRep, Integer> {

    TestRun getById(Integer id);

}
