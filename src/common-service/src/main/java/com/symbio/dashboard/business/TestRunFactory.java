package com.symbio.dashboard.business;

import com.symbio.dashboard.constant.CommonDef;
import com.symbio.dashboard.enums.EntityDisplay;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.TestCase;
import com.symbio.dashboard.model.TestRun;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.StringUtil;

import java.util.Date;
import java.util.Map;

public class TestRunFactory {

    public static TestRun createNewTestRun(TestCase tc, String labelInfoIdStr, String local) {
        TestRun testRun = new TestRun();
        if (!StringUtil.isEmpty(labelInfoIdStr)) {
//            testRun.setReleaseInfoId(Integer.parseInt(labelInfoIdStr));
        }
        testRun.setTestcaseId(tc.getId());
        testRun.setDisplay(1);
        testRun.setValidation(1);
        testRun.setLocale(local);
        testRun.setCreateTime(new Date());
        return testRun;
    }

    public static TestRun createNewTestRun(TestCase tc, Integer testSetId, String locale) {
        TestRun testRun = new TestRun();

        testRun.setTestsetId(testSetId);
        testRun.setTestcaseId(tc.getId());
        testRun.setDisplay(EntityDisplay.SHOW.getValue());
        testRun.setValidation(EnumDef.ENTITY_VALIDATION.VALID.getCode());
        testRun.setStatus(EnumDef.TEST_RUN_STATUS.NOT_RUN.getCode());
        testRun.setRunEngineerId(10);
        testRun.setRunQaId(6);
        testRun.setRunTepId(7);
        testRun.setScreenshot_flag(0);

        if (CommonUtil.isEmpty(locale)) {
            testRun.setLocale(Locales.EN_US.toString());
        } else {
            testRun.setLocale(locale);
        }
        testRun.setCreateTime(new Date());
        return testRun;
    }

    public static String getTestLocale(Map<String, String> config) {
        if (config == null) {
            return Locales.EN_US.toString();
        }
        String language = config.get(CommonDef.MOBILEAPPLANGUAGE);
        if (CommonUtil.isEmpty(language)) {
            language = "en";
        }
        if (language.contains("_")) {
            return language;
        }
        String locale = config.get(CommonDef.MOBILEAPPLOCALE);
        if (CommonUtil.isEmpty(locale)) {
            locale = "US";
        }
        return language + "_" + locale;
    }
}
