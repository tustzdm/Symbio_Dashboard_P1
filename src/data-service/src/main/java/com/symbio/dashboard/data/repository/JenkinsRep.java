package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.JenkinsJobParameter;
import com.symbio.dashboard.model.TestExecPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JenkinsRep extends JpaRepository<TestExecPlatform, Integer> {
    @Query(value = "SELECT * FROM test_exec_platform WHERE productId = 0 AND testSetType = ?1 AND display = 1 ORDER BY idx", nativeQuery = true)
    List<TestExecPlatform> getCommonTEPSetting(Integer testSetType);

    @Query(value = "SELECT * FROM test_exec_platform WHERE productId = ?1 AND testSetType = ?2 AND display = 1 ORDER BY idx", nativeQuery = true)
    List<TestExecPlatform> getTEPSettingByProductId(Integer productId, Integer testSetType);

    @Query(value = "SELECT jsi.id FROM jenkins_svr_info jsi " +
            "INNER JOIN test_exec_platform tep ON tep.jenkinsId = jsi.id " +
            "WHERE tep.id = ?1 AND jsi.display = 1 LIMIT 0,1", nativeQuery = true)
    Integer getJSIIdByTEPId(Integer tepId);

//    @Query(value = "SELECT * FROM jenkins_svr_info WHERE id = ? LIMIT 0,1", nativeQuery = true)
//    List<JenkinsSvrInfo> getJSIInfoById(Integer id);

    @Query(value = "SELECT * FROM jenkins_job_parameter WHERE jsiId = ?1 AND validation = 1 ORDER BY idx", nativeQuery = true)
    List<JenkinsJobParameter> getJobParameter(Integer jsiId);
}
