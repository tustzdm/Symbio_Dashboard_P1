package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.SysListSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysListSettingRep extends JpaRepository<SysListSetting, Integer> {

    @Query(value = "select * from sys_list_setting where name=?1", nativeQuery = true)
    List<SysListSetting> getSysListSetting(String name);

    @Query(value = "SELECT sls.* FROM sys_list_setting sls " +
            "JOIN ui_info ui ON sls.field = ui.db_field " +
            "WHERE sls.`name` = ?1 AND sls.display = 1 AND sls.is_entity = 1 " +
            "AND ui.`page` = ?1 ORDER by sls.idx", nativeQuery = true)
    List<SysListSetting> getEntityInfo(String pageName);

    @Query(value = "select s.* from sys_list_setting s " +
            "join ui_info u " +
            "on s.field = u.db_field " +
            "where s.name = 'product' " +
            "and u.display = 1", nativeQuery = true)
    List<SysListSetting> getDbFieldsInProduct();

    @Query(value = "SELECT * FROM sys_list_setting WHERE `name` = ? AND sls.display = 1 AND sls.is_entity = 0 ORDER by sls.idx", nativeQuery = true)
    List<SysListSetting> getStatisticsInfo(String pageName);
}
