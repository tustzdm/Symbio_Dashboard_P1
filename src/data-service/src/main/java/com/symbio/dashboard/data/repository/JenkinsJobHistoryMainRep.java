package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.JenkinsJobHistoryMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JenkinsJobHistoryMainRep extends JpaRepository<JenkinsJobHistoryMain, Integer> {

    JenkinsJobHistoryMain getById(Integer id);

    // Refer to : EnumDef.JENKINS_JOB_STATUS
    // Not 'SUCCESS', 'FAILURE', 'ABORTED'
    @Query(value = "SELECT * FROM jenkins_job_history_main WHERE status NOT IN ('2','3','4') AND display = 1 AND parseCount < 99 ORDER BY id", nativeQuery = true)
    List<JenkinsJobHistoryMain> getUpdateStatusCronList();
}
