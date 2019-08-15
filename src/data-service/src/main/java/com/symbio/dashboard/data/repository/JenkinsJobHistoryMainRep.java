package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.JenkinsJobHistoryMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JenkinsJobHistoryMainRep extends JpaRepository<JenkinsJobHistoryMain, Integer> {

    JenkinsJobHistoryMain getById(Integer id);
}
