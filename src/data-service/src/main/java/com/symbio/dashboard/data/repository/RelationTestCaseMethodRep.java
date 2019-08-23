package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.RelationTestCaseMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationTestCaseMethodRep extends JpaRepository<RelationTestCaseMethod, Integer> {

    RelationTestCaseMethod getById(Integer id);

    RelationTestCaseMethod getByPackageInfoAndClassNameAndMethodName(String packageInfo, String className, String method);
}
