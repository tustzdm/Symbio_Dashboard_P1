package com.symbio.dashboard.business;

import com.symbio.dashboard.constant.CommonDef;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestCaseUtil {

    public static final String PRIORITY = "Test Priority";
    public static final String TRPRIORITY = "Priority";
    public static final String CASEID = "ID";
    public static final String SUBFEATUREAREA = "Domain / Subdomain";
    public static final String FEATUREAREA = "Section Hierarchy";
    public static final String SUMMARY = "Title";
    public static final String DETAILEDSTEPS = "Steps";
    public static final String OWNER = "Automation owner";
    public static final String CLASSNAME = "Class Name";
    public static final String LOCALES = "Locale";
    public static final String ENVIRONMENT = "Environment";
    public static final String ACTRUALTESTERNAME = "Actual Tester";

    private static final long serialVersionUID = -21775206532700122L;

    private static int m_nMaxTestCaseId = -1;

    public static synchronized Integer GetTestCaseId() {
        int nTestCaseId = 0;

        try {
            if (m_nMaxTestCaseId == -1) {
                nTestCaseId = CommonDef.TESTCASEID_SEQUENCE_INIT; // GetSequence("TestCaseId");
            } else {
                nTestCaseId = m_nMaxTestCaseId + 1;
            }
            // TODO: update sequence(nTestCaseId)

        } catch (Exception e) {
            log.error("Get TestCaseId' sequence Error.", e);
            nTestCaseId = -1;
        } finally {
            m_nMaxTestCaseId = nTestCaseId;
        }

        return nTestCaseId;
    }

    @SuppressWarnings("unused")
    public static synchronized Integer[] GetTestCaseId(int nCount) {
        int nTestCaseId = 0;
        Integer[] arrTestCaseId = null;

        try {
            arrTestCaseId = new Integer[nCount];
            if (m_nMaxTestCaseId == -1) {
                // TODO: GetSequence("TestCaseId")
                nTestCaseId = CommonDef.TESTCASEID_SEQUENCE_INIT;
            } else {
                nTestCaseId = m_nMaxTestCaseId;
            }

            for (Integer id : arrTestCaseId) {
                id = nTestCaseId++;
            }
            nTestCaseId--;

            // TODO: update sequence(nTestCaseId)

        } catch (Exception e) {
            log.error("Get TestCaseId' sequence Error.", e);
            nTestCaseId = -1;
        } finally {
            m_nMaxTestCaseId = nTestCaseId;
        }

        return arrTestCaseId;
    }

    public static String getFormatLen(Integer testcaseId, int len) {
        if (testcaseId == null || testcaseId < 0) return "";

        String strTestCaseId = null;
        if (len < 2) return testcaseId.toString();
        else {
            String strFormat = "%0" + len + "d"; // CommonDef.TESTCASEID_FORMAT_LEN
            strTestCaseId = String.format(strFormat, testcaseId);
        }
        return strTestCaseId;
    }


}
