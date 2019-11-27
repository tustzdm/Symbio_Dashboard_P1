package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.constant.ProjectConst;
import com.symbio.dashboard.data.repository.SettingExcelImportRep;
import com.symbio.dashboard.data.repository.TestSetRep;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.model.SettingExcelImport;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName - ExcelImportDao
 * @Description -
 * @Date - 2019/8/12 16:11
 * @Version 1.0
 */

@SuppressWarnings("unchecked")
@Repository
@Slf4j
public class ExcelImportDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private TestSetRep testSetRep;
    @Autowired
    private SettingExcelImportRep settingExcelImportRep;


    public Result<List<Map<String, String>>> getExcelHeadSetting(String locale, Integer testSetId) {
        Result<List<Map<String, String>>> retResult = new Result();

        try {
            List<Map<String, String>> list = new ArrayList<>();
            Map<String, String> map;

            /*
            String productConfig = commonDao.getConfigValueByKey(ProjectConst.TESTCASE_SPERATED_PRODUCT);
            Integer productId;
            if (EnumDef.TESTCASE_SEPARATED_PRODUCT.NO.toString().equalsIgnoreCase(productConfig)) {
                productId = 0;
            } else {
                productId = testSetRep.getProductIdByTestSetId(testSetId);
            }
            Integer testSetType = testSetRep.getTypeById(testSetId);

            List<SettingExcelImport> headSettingList = settingExcelImportRep.getByCaseType(testSetType, productId);
            if (CommonUtil.isEmpty(headSettingList)) {
                headSettingList = settingExcelImportRep.getByCaseType(testSetType, 0);

                if (CommonUtil.isEmpty(headSettingList)) {
                    return commonDao.getResultArgs(locale, "300202", testSetType);
                }
            }*/

            String productConfig = commonDao.getConfigValueByKey(ProjectConst.TESTCASE_SPERATED_PRODUCT);
            Integer testSetType = testSetRep.getTypeById(testSetId);
            Integer productId = testSetRep.getProductIdByTestSetId(testSetId);

            List<SettingExcelImport> headSettingList = settingExcelImportRep.getByCaseType(testSetType, productId);
            if (CommonUtil.isEmpty(headSettingList)) {
                // Try to get common setting
                if (EnumDef.TESTCASE_SEPARATED_PRODUCT.NO.toString().equalsIgnoreCase(productConfig)) {
                    headSettingList = settingExcelImportRep.getByCaseType(testSetType, 0);
                }

                if (CommonUtil.isEmpty(headSettingList)) {
                    return commonDao.getResultArgs(locale, "300202", testSetType);
                }
            }
            for (SettingExcelImport s : headSettingList) {
                map = new HashMap<>();
                String name = s.getName();
                String field = s.getField();
                map.put("name", name);
                map.put("field", field);
                list.add(map);
            }
            retResult.setCd(list);
        } catch (Exception e) {
            e.printStackTrace();
            return commonDao.getExceptionArgsResult("getting Excel head setting");
        }
        return retResult;
    }

}
