package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryRep extends JpaRepository<Dictionary, Integer> {

    @Query(value = "SELECT * FROM dictionary WHERE `type` = ? AND validation = 1 ORDER BY id", nativeQuery = true)
    List<Dictionary> getDictDataByType(String type);

    @Override
    Dictionary getOne(Integer id);
}
