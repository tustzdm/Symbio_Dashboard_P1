package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.LocaleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalesInfoRep extends JpaRepository<LocaleInfo, Integer> {

    List<LocaleInfo> getById(Integer id);

    @Override
    List<LocaleInfo> findAll();

    @Query(value = "SELECT * FROM locales_info WHERE code IN (?1) AND validation = 1", nativeQuery = true)
    List<LocaleInfo> getListByQuery(String locale);

    @Query(value = "SELECT * FROM locales_info WHERE code IN (?1) AND code != 'en_US' AND validation = 1", nativeQuery = true)
    List<LocaleInfo> getReviewLocalesList(String locale);
}
