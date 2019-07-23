package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.SysListSetting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysListSettingRep {

    @Query(value = "select * from sys_list_setting where name=?1", nativeQuery = true)
    List<SysListSetting> getSysListSetting(String name);


}
