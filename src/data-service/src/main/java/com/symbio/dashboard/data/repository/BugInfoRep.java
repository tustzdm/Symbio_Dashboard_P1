package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.BugInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName - BugInfoRep
 * @Author - Shawn
 * @Description
 * @Date - 2019/11/08
 * @Version 1.0
 */

@Repository
public interface BugInfoRep extends JpaRepository<BugInfo, Integer> {

    @Override
    BugInfo getOne(Integer id);

    @Query(value = "SELECT * FROM bug_info WHERE test_result_id = ?1 AND screen_shot_id = ?2 AND locale = ?3 AND validation = 1", nativeQuery = true)
    BugInfo getByTestResultScreenLocale(Integer testResultId, Integer screenshotId, String locale);

    @Query(value = "SELECT * FROM bug_info WHERE screen_shot_id = ?1 AND validation = 1", nativeQuery = true)
    BugInfo getByScreenShotId(Integer screenshotId);
}
