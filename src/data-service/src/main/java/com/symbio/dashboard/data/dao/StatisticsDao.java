package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.data.repository.StatChartRep;
import com.symbio.dashboard.data.repository.StatListRep;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.SystemListSetting;
import com.symbio.dashboard.model.StatList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@Slf4j
@Repository
public class StatisticsDao {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private StatListRep statListRep;
    @Autowired
    private StatChartRep statChartRep;

    public StatList getProgressStatData(Integer type, Integer fkId) {
        return statListRep.getByTypeIdField(type, fkId, EnumDef.STAT_LIST_FIELD.PROGRESS.getValue());
    }

    public void saveStatisticsData(StatList data) {
        statListRep.saveAndFlush(data);
    }

    public List<StatList> getTestSetData(Integer releaseId) {
        return getStatListData(SystemListSetting.TestSet, null, releaseId, null);
    }

    public List<StatList> getReleaseData(Integer productId) {
        return getStatListData(SystemListSetting.Release, productId, null, null);
    }

    public List<StatList> getProductData() {
        return getStatListData(SystemListSetting.Product, null, null, null);
    }

    private List<StatList> getStatListData(SystemListSetting systemListSetting, Integer productId, Integer releaseId, Integer testSetId) {
        List<StatList> retData = new ArrayList<>();

        switch (systemListSetting) {
            case Product:
                if (productId == null) {
                    retData = statListRep.getAllProductData(systemListSetting.toString(), systemListSetting.ordinal());
                } else {
                    retData = statListRep.getProductDataByProductId(systemListSetting.toString(), systemListSetting.ordinal(), productId);
                }
                break;
            case Release:
                if (productId != null && productId > 0) {
                    retData = statListRep.getReleaseDataByProductId(systemListSetting.toString(), systemListSetting.ordinal(), productId);
                } else {
                    retData = statListRep.getReleaseDataByReleaseId(systemListSetting.toString(), systemListSetting.ordinal(), releaseId);
                }
                break;
            case TestSet:
                if (releaseId != null && releaseId > 0) {
                    retData = statListRep.getTestSetDataByReleaseId(systemListSetting.toString(), systemListSetting.ordinal(), releaseId);
                } else {
                    retData = statListRep.getTestSetDataByTestSetId(systemListSetting.toString(), systemListSetting.ordinal(), testSetId);
                }
                break;
        }

        return retData;
    }

}
