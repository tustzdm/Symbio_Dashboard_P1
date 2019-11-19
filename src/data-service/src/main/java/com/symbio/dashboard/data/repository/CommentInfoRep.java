package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.CommentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName - CommentInfoRep
 * @Author - Shawn
 * @Description
 * @Date - 2019/11/19
 * @Version 1.0
 */

@Repository
public interface CommentInfoRep extends JpaRepository<CommentInfo, Integer> {

    @Override
    CommentInfo getOne(Integer id);

    @Query(value = "SELECT * FROM comment_info WHERE comment_type = ?1 AND fk_id = ?2 AND validation = 1 ORDER BY update_time DESC", nativeQuery = true)
    List<CommentInfo> getListByFkId(Integer commentType, Integer fkId);
}
