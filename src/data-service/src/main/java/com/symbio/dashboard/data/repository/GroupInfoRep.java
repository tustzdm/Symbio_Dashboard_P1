package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupInfoRep extends JpaRepository<Group, Integer> {

    @Query(value = "SELECT * FROM group_info WHERE groupType = ?1 AND validation = 1", nativeQuery = true)
    List<Group> getListByType(Integer groupType);

    @Override
    Group getOne(Integer id);

    @Query(value = "SELECT * FROM group_info WHERE groupType = 2 AND productId = ?1 AND validation = 1 ORDER BY id LIMIT 0,1", nativeQuery = true)
    Group getOneByProductId(Integer productId);

}
