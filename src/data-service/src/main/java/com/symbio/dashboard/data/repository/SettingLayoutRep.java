package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.SettingLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingLayoutRep extends JpaRepository<SettingLayout, Integer> {

    @Query(value = "SELECT id FROM setting_layout WHERE page = ?1 AND `type` = ?2 AND locale = ?3", nativeQuery = true)
    Integer getIdByPageAndTypeAndLocale(String page, Integer type, String locale);

    @Query(value = "SELECT layout FROM setting_layout WHERE locale = ?1", nativeQuery = true)
    String getLayoutByLocale(String locale);
}
