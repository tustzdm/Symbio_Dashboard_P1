package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.SettingExcelImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingExcelImportRep extends JpaRepository<SettingExcelImport, Integer> {

    @Query(value = "SELECT * FROM setting_excel_import WHERE case_type = ?1 and productId = ?2", nativeQuery = true)
    List<SettingExcelImport> getByCaseType(Integer type, Integer productId);

}
