package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.Dictionay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionayRep extends JpaRepository<Dictionay, Integer> {

    @Query(value = "SELECT * FROM dictionary WHERE `type` = ? AND validation = 1 ORDER BY id", nativeQuery = true)
    List<Dictionay> getDictDataByType(String type);

    @Override
    Dictionay getOne(Integer id);
}
