package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.JenkinsLabelInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JenkinsLabelInfoRep extends JpaRepository<JenkinsLabelInfo, Integer> {

    JenkinsLabelInfo getById(Integer id);
}
