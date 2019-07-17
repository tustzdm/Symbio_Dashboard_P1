package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryRep extends JpaRepository<Dictionary, Integer> {

    @Query(value = "select * from dictionary where type=?1 and validation = 1 order by id", nativeQuery = true)
    List<Dictionary> getPageNameList(String type);

    @Override
    Dictionary getOne(Integer id);
}
