package com.symbio.dashboard.report.repository;

import com.symbio.dashboard.report.modle.LanguageUI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageUIRepository extends JpaRepository<LanguageUI,Integer> {

    @Query(value = "select l.key from language_ui l where l.page=?1 and l.validation=?2",nativeQuery = true)
    List<String> getKeyByPageAndValidation(String page, Integer validation);

    List<LanguageUI> getByPageAndValidation(String page, Integer validation);

}
