package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.UiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UiInfoRep extends JpaRepository<UiInfo, Integer> {

    @Query(value = "select * from ui_info u where u.page=?1 and u.display = 1 order by u.id", nativeQuery = true)
    List<UiInfo> getUiInfoListByPageName(String page);

    @Override
    UiInfo getOne(Integer id);

}
