package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.JenkinsSvrInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JenkinsServerInfoRep extends JpaRepository<JenkinsSvrInfo, Integer> {

    JenkinsSvrInfo getById(Integer id);

}
