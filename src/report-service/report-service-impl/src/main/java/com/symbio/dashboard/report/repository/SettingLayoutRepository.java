package com.symbio.dashboard.report.repository;

import com.symbio.dashboard.report.modle.SettingLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingLayoutRepository extends JpaRepository<SettingLayout,Integer> {

    @Query(value = "select s.id from setting_layout s where s.page=?1 and s.type=?2 and s.locale=?3",nativeQuery = true)
    Integer getIdByPageAndTypeAndLocale(String page,Integer type,String locale);

    @Query(value = "select s.layout from setting_layout s where s.locale=?1",nativeQuery = true)
    String getLayoutByLocale(String locale);
}
