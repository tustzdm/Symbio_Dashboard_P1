package com.symbio.dashboard.business;

import com.symbio.dashboard.enums.EntityDisplay;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.model.TestCase;
import com.symbio.dashboard.util.BusinessUtil;
import com.symbio.dashboard.util.EntityUtils;

import java.util.Date;

public class TestCaseFactory {

    public static TestCase createNewTestCaseByExcel(Integer caseType, TestCase tc) {
        if (BusinessUtil.isIdEmpty(tc.getProductId())) {
            tc.setProductId(0);
        }
        tc.setCaseType(caseType);
        tc.setCaseStatus(EnumDef.CASE_STATUS.NORMAL.getValue());

        tc.setDisplay(EntityDisplay.SHOW.getValue());
        tc.setValidation(EnumDef.ENTITY_VALIDATION.VALID.getCode());
        tc.setCreateTime(new Date());
        return tc;
    }

    public static TestCase mergeTestCaseByExcel(TestCase dbTC, TestCase execlTC) {
        EntityUtils.mergeObjectBase(dbTC, execlTC);
        return dbTC;
    }
}
