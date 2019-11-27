package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.StatList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName - StatListRep
 * @Description
 * @Date - 2019/11/25
 * @Version 1.0
 */

@Repository
public interface StatListRep extends JpaRepository<StatList, Integer> {

    @Override
    StatList getOne(Integer id);

    @Query(value = "SELECT sl.* FROM stat_list sl" +
            " INNER JOIN product prod ON sl.fk_id = prod.id" +
            " INNER JOIN (SELECT name, field FROM sys_list_setting WHERE display = 1 AND is_entity = 0) sls ON sls.field = sl.field" +
            " WHERE sls.`name` = ?1 AND sl.type_code = ?2 AND sl.validation = 1 AND prod.display = 1" +
            " ORDER BY sl.fk_id, sl.field", nativeQuery = true)
    List<StatList> getAllProductData(String pageName, Integer typeCode);

    @Query(value = "SELECT sl.* FROM stat_list sl" +
            " INNER JOIN product prod ON sl.fk_id = prod.id " +
            " INNER JOIN (SELECT name, field FROM sys_list_setting WHERE display = 1 AND is_entity = 0) sls ON sls.field = sl.field" +
            " WHERE sls.`name` = ?1 AND sl.type_code = ?2 AND sl.fk_id = ?3 AND sl.validation = 1" +
            " ORDER BY sl.fk_id, sl.field", nativeQuery = true)
    List<StatList> getProductDataByProductId(String pageName, Integer typeCode, Integer productId);


    @Query(value = "SELECT sl.* FROM stat_list sl" +
            " INNER JOIN `release` re ON sl.fk_id = re.id" +
            " INNER JOIN product prod ON prod.id = re.product_id" +
            " INNER JOIN (SELECT name, field FROM sys_list_setting WHERE display = 1 AND is_entity = 0) sls ON sls.field = sl.field" +
            " WHERE sls.`name` = ?1 AND sl.type_code = ?2 AND prod.id = ?3" +
            " ORDER BY sl.fk_id, sl.field", nativeQuery = true)
    List<StatList> getReleaseDataByProductId(String pageName, Integer typeCode, Integer productId);

    @Query(value = "SELECT sl.* FROM stat_list sl" +
            " INNER JOIN `release` re ON sl.fk_id = re.id" +
            " INNER JOIN product prod ON prod.id = re.product_id" +
            " INNER JOIN (SELECT name, field FROM sys_list_setting WHERE display = 1 AND is_entity = 0) sls ON sls.field = sl.field" +
            " WHERE sls.`name` = ?1 AND sl.type_code = ?2 AND sl.fk_id = ?3 AND sl.validation = 1" +
            " ORDER BY sl.fk_id, sl.field", nativeQuery = true)
    List<StatList> getReleaseDataByReleaseId(String pageName, Integer typeCode, Integer releaseId);

    // Default: typeCode = 2(TestSet) name = 'testset'
    @Query(value = "SELECT sl.* FROM stat_list sl" +
            " INNER JOIN test_set ts ON sl.fk_id = ts.id" +
            " INNER JOIN `release` re ON re.id = ts.release_id" +
            " INNER JOIN (SELECT name, field FROM sys_list_setting WHERE display = 1 AND is_entity = 0) sls ON sls.field = sl.field" +
            " WHERE sls.`name` = ?1 AND sl.type_code = ?2 AND ts.release_id = ?3" +
            " ORDER BY sl.fk_id, sl.field", nativeQuery = true)
    List<StatList> getTestSetDataByReleaseId(String pageName, Integer typeCode, Integer releaseId);

    @Query(value = "SELECT sl.* FROM stat_list sl" +
            " INNER JOIN test_set ts ON sl.fk_id = ts.id" +
            " INNER JOIN `release` re ON re.id = ts.release_id" +
            " INNER JOIN (SELECT name, field FROM sys_list_setting WHERE display = 1 AND is_entity = 0) sls ON sls.field = sl.field" +
            " WHERE sls.`name` = ?1 AND sl.type_code = ?2 AND sl.fk_id = ?3 AND sl.validation = 1" +
            " ORDER BY sl.fk_id, sl.field", nativeQuery = true)
    List<StatList> getTestSetDataByTestSetId(String pageName, Integer typeCode, Integer releaseId);

}
