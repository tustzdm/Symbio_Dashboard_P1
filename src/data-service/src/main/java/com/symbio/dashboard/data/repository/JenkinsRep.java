package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.TestExecPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JenkinsRep extends JpaRepository<TestExecPlatform, Integer> {
    @Query(value = "SELECT * FROM test_exec_platform WHERE productId = 0 AND testSetType = ?1 display = 1 ORDER BY idx", nativeQuery = true)
    List<TestExecPlatform> getCommonTEPSetting(Integer testSetType);

    @Query(value = "SELECT * FROM test_exec_platform WHERE productId = ?1 AND testSetType = ?2 display = 1 ORDER BY idx", nativeQuery = true)
    List<TestExecPlatform> getTEPSettingByProductId(Integer productId, Integer testSetType);
}
