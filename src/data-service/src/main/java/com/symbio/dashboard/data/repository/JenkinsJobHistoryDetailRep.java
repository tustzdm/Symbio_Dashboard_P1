package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.JenkinsJobHistoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JenkinsJobHistoryDetailRep extends JpaRepository<JenkinsJobHistoryDetail, Integer> {

    JenkinsJobHistoryDetail getById(Integer id);
}
