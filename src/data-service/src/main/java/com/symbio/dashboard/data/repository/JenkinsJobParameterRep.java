package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.JenkinsJobParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JenkinsJobParameterRep extends JpaRepository<JenkinsJobParameter, Integer> {

    JenkinsJobParameter getById(Integer id);

    @Query(value = "SELECT * FROM jenkins_job_parameter WHERE `jsiId` = ? AND validation = 1 ORDER BY id", nativeQuery = true)
    List<JenkinsJobParameter> fetchListByJSIId(Integer jsiId);

    JenkinsJobParameter findByJsiIdAndParamName(Integer jsiId, String paramName);

}
