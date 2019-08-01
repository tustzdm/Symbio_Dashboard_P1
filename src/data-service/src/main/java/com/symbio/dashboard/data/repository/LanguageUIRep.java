package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.LanguageUi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageUIRep extends JpaRepository<LanguageUi, Integer> {

    @Query(value = "SELECT key FROM language_ui WHERE page = ?1 AND validation = ?2", nativeQuery = true)
    List<String> getKeyByPageAndValidation(String page, Integer validation);

    List<LanguageUi> getByPageAndValidation(String page, Integer validation);

}
