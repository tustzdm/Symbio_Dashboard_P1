package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.ResultMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultMessageRep extends JpaRepository<ResultMessage, Integer> {

    ResultMessage getById(Integer id);

    @Query(value = "SELECT * FROM result_message ORDER BY id", nativeQuery = true)
    List<ResultMessage> getAll();
}
