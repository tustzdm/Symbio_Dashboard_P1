package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.ProjectConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectConfigRep extends JpaRepository<ProjectConfig, Integer> {

    ProjectConfig getById(Integer id);

    @Query(value = "SELECT * FROM project_config ORDER BY id", nativeQuery = true)
    List<ProjectConfig> getAll();

    @Query(value = "SELECT configValue from project_config where configName = 'TEP_SeparatedByProduct'", nativeQuery = true)
    String getSeparatedByProduct();
}
