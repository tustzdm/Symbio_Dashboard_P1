package com.symbio.dashboard.enums;

import lombok.Getter;

import java.lang.reflect.Method;
import java.util.EnumSet;

/**
 * Enumeration type definition
 *
 * @Version 1.1  2019/08/09
 */
public class EnumDef {

    public interface IDictEnum {
        Integer getCode();

        String getValue();

        String toString();
    }

    /**
     * BROWSER TYPE
     */
    public enum BROWSER_TYPE {
        UNKOWN_BROWSER((short) 0),
        IE6((short) 100),
        IE7((short) 110),
        IE8((short) 120),
        IE9((short) 130),
        IE10((short) 140),
        IE11((short) 150),
        IE12((short) 160),
        FIREFOX((short) 200),
        CHROME((short) 300),
        OPERA((short) 400),
        B360((short) 500),
        MAXTHON((short) 600),
        QQ((short) 610),
        GREEN((short) 620),
        SAFARI((short) 630),
        SOGOU((short) 640),
        OTHER((short) 999);
        private short value;

        BROWSER_TYPE(short value) {
            this.value = value;
        }

        public short getValue() {
            return this.value;
        }
    }

    public enum FILEINFO_PARAM_TYPE {
        C("C"),
        S("_");
        private CharSequence value;

        FILEINFO_PARAM_TYPE(CharSequence value) {
            this.value = value;
        }

        public CharSequence getValue() {
            return this.value;
        }
    }

    /**
     * OS TYPE
     */
    public enum OS_TYPE {
        UNKOWN_OS((short) 0),
        WIN95((short) 100),
        WIN98((short) 110),
        WINME((short) 120),
        WINNT4((short) 130),
        WIN2K((short) 140),
        WINXP((short) 150),
        WIN7((short) 160),
        WIN8((short) 170),
        WIN9((short) 180),
        WIN2003((short) 200),
        WIN2008((short) 210),
        LINUX((short) 300),
        MAC((short) 400),
        UNIX((short) 500),
        IOS((short) 600),
        ANDROID((short) 700),
        SUNOS((short) 800),
        OTHER((short) 900);
        private short value;

        OS_TYPE(short value) {
            this.value = value;
        }

        public short getValue() {
            return this.value;
        }
    }

    /**
     * ROLE TYPE Refer to : [role_setting].name
     */
    public enum ROLE_TYPE {
        SystemAdmin(1),
        ClientManager(2),
        Client(3);
        //        LQADashboardManager(4),
        //        CompanyAssistant(5),
        //        CompanyUser(6),
        //        ProductManager(7),
        //        AUTOLead(8),
        //        AutoEngineer(9),
        //        LQALead(10),
        //        LQA(11),
        //        ProductUser(12),
        //        AnonymousUser(13),
        //        LS(14),
        //        ICM(15),
        //        CompanySwitch(16);

        private int id;
        private int value;

        ROLE_TYPE(int id) {
            this.id = id;
            this.value = 0;
        }

        public int getValue() {
            return this.value;
        }

        public int getID() {
            return this.id;
        }

        public String getUpperName() {
            return this.name().toUpperCase();
        }
    }

    /**
     * [TestSet.type]Test Set Type == [TestCase.case_type]Case Type
     */
    public enum TEST_SET_TYPE implements IDictEnum {
        AUTOMATION(1, "Automation Test"),
        MANUAL(2, "Manual Test"),
        API(4, "API Test"),
        PERFORMANCE(8, "Performance Test");

        private Integer code;
        private String value;

        TEST_SET_TYPE(int code, String value) {
            this.code = code;
        }

        @Override
        public Integer getCode() {
            return this.code;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    /**
     * [TestCase.case_type]Case Type == Unknown + [TestSet.type]Test Set Type
     */
    public enum CASE_TYPE implements IDictEnum {
        UNKNOWN(0, "Unknown"),

        AUTOMATION(1, "Automation Test"),

        MANUAL(2, "Manual Test"),

        API(4, "API Test"),

        PERFORMANCE(8, "Performance Test");

        private Integer code;
        private String value;

        CASE_TYPE(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public Integer getCode() {
            return this.code;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    /**
     * Priority
     */
    public enum CASE_PRIORITY implements IDictEnum {
        Blank(0, ""),
        P1(1, "P1"),
        P2(2, "P2"),
        P3(3, "P3"),
        P4(4, "P4"),
        P5(5, "P5");

        private Integer code;
        private String value;

        CASE_PRIORITY(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public Integer getCode() {
            return this.code;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    /**
     * Lists that support Operation
     */
    @Getter
    public enum OPERATION_TYPE implements IDictEnum {
        PRODUCT(1, "Product"),
        RELEASE(2, "Release"),
        TestSet(3, "Test Set"),
        TestRun(4, "Test Run");

        private int id;
        private String page;

        OPERATION_TYPE(int id, String page) {
            this.id = id;
            this.page = page;
        }

        @Override
        public Integer getCode() {
            return this.id;
        }

        @Override
        public String getValue() {
            return this.page;
        }

        @Override
        public String toString() {
            return String.valueOf(this.id);
        }
    }

    public static OPERATION_TYPE getOPERATIONTypeById(Integer id) {
        EnumSet<OPERATION_TYPE> currEnumSet = EnumSet.allOf(OPERATION_TYPE.class);
        for (OPERATION_TYPE item : currEnumSet) {
            if (item.getId() == id) return item;
        }
        return null;
    }

    public enum MENU_TYPE {
        PAGE_ELEMENT(1, "Page_Element"),
        TEP_LIST(2, "teplist"),
        SETTING(3, "SETTING"),
        FILE_INFO(20, "File Information"),
        SERVER_INFO(20, "Server Information");

        //        COMPANY(1, "Company"),
        //        PRODUCT(2, "product"),
        //        Release(3, "Release"),
        //        TestResult(12, "Test Result"),
        //        Bug(14, "Bug"),
        //        Report(16, "Report"),
        //        Issue(18, "Issue Type"),
        //        FileInfo(20, "File Information"),
        //        Setting(22, "SETTING"),
        //        Teplist(24, "teplist");

        private int id;
        private String title;

        MENU_TYPE(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public int getID() {
            return this.id;
        }
    }

    public static MENU_TYPE getMenuById(Integer id) {
        EnumSet<MENU_TYPE> currEnumSet = EnumSet.allOf(MENU_TYPE.class);
        for (MENU_TYPE item : currEnumSet) {
            if (item.getID() == id.intValue()) return item;
        }
        return null;
    }

    /**
     * Automation Job actual status
     */
    public enum JENKINS_AUTO_STATUS implements IDictEnum {
        NotRun(0, "Not Run"),
        AutoSuccess(1, "Passed"),
        AutoFailure(2, "Failed"),
        AutoSkip(3, "Skipped"),
        Waiting(4, "waiting"),
        Running(5, "running"),
        Error(6, "error");

        private int code;
        private String value;

        JENKINS_AUTO_STATUS(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
        }

        @Override
        public Integer getCode() {
            return this.code;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // End of New Dashboard definition
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    public enum DICT_TYPE {
        QA_STATUS("QAStatus"),
        AUTO_STATUS("AutoStatus"),
        LOCALES("Locales"),
        ENVIRONMENT("Environment"),
        QA_CLUSTER("QACluster"),
        E2E_CLUSTER("E2ECluster"),
        PRIORITY("Priority"),
        BROWSERS("Browsers"),
        SKUs("SKUs"),
        METHOD_OF_EXECUTION("MethodofExecution"),
        RELEASE_STATUS("ReleaseStatus");

        private String type;

        DICT_TYPE(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    public enum QA_ERRORTYPE {
        COND_PASS("conditional pass"),
        FAIL("fail"),
        TNR("Testing Not Required"),
        LQA_SUPPORT("LQA Support");
        private String type;

        QA_ERRORTYPE(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    /**
     * TestCase level
     */
    public enum CASE_LEVEL {
        LVL3(3),
        LVL4(4);
        private int value;

        CASE_LEVEL(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }



    public enum LOCALE {
        EN_US("en", "us", "en_US"),
        ZH_CN("zh", "cn", "zh_CN");

//        ES_UN("es", "un", "es_UN"),
//        FR_CA("fr", "ca", "fr_CA"),
//        FR_FR("fr", "fr", "fr_FR"),
//        FR_UN("fr", "UN", "fr_UN"),
//        PT_BR("pt", "BR", "pt_BR");

        private String language;
        private String region;
        private String value;

        public String getRegion() {
            return region;
        }

        public String getValue() {
            return value;
        }

        LOCALE(String language, String region, String value) {
            this.language = language;
            this.region = region;
            this.value = value;
        }

        public String getLanguage() {
            return language;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum ENVIRONMENT_STATUS {
        InProgress(0, "In Progress"),
        Completed(1, "Completed");
        private Integer code;
        private String value;

        ENVIRONMENT_STATUS(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return name();
        }

        public Integer getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * TestCase status
     */
    public enum CASE_STATUS {
        NORMAL(0),
        DISABLE(1),
        DELETE(4);
        private int value;

        CASE_STATUS(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    // report type
    public enum REPORT_TYPE {
        Pass(1, "Pass"),
        conditionPass(2, "Conditional Pass"),
        Fail(4, "Fail"),
        TestNotRequired(5, "Test Not Required"),
        AutoSuccess(1, "AUTOMATION SUCCESS"),
        AutoFailure(2, "AUTOMATION FAILURE"),
        AutoSkip(3, "AUTOMATION SKIP"),
        notTested(0, "Not Run"),
        lqaSupport(6, "LQA Support");
        private int id;
        private String title;

        REPORT_TYPE(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public int getID() {
            return this.id;
        }
    }

    public enum PRODUCT_IDENTIFYKEY {
        SYMBIO("SYMBIO"),
        ALL("");
        private String name;

        PRODUCT_IDENTIFYKEY(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public enum QB_API_STATUS implements IDictEnum {
        UNKnown(0, ""),
        SUCCESS(1, "success"),
        LQA_FAIL(4, "LQA Failure"),
        AUTO_FAIL(5, "Auto Failure");
        private int code;
        private String desp;

        QB_API_STATUS(int code, String desp) {
            this.code = code;
            this.desp = desp;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
        }

        @Override
        public Integer getCode() {
            return this.code;
        }

        @Override
        public String getValue() {
            return this.desp;
        }
    }

    public enum QB_BROWSERS implements IDictEnum {
        UNKnown(0, ""),
        Firefox(1, "Firefox"),
        Chrome(2, "Chrome");
        private int code;
        private String desp;

        QB_BROWSERS(int code, String desp) {
            this.code = code;
            this.desp = desp;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
        }

        @Override
        public Integer getCode() {
            return this.code;
        }

        @Override
        public String getValue() {
            return this.desp;
        }
    }

    public static QB_BROWSERS getBrowsersByCode(int code) {
        EnumSet<QB_BROWSERS> currEnumSet = EnumSet.allOf(QB_BROWSERS.class);
        for (QB_BROWSERS item : currEnumSet) {
            if (item.getCode() == code) return item;
        }
        return null;
    }

    public enum QB_QAStatus implements IDictEnum {
        BLANK(0, ""),
        Pass(1, "pass"),
        Conditional_Pass(2, "conditional pass"),
        Fail(4, "fail"),
        TestNotReq(5, "Testing Not Required");
        private int code;
        private String desp;

        QB_QAStatus(int code, String desp) {
            this.code = code;
            this.desp = desp;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }

        @Override
        public Integer getCode() {
            return this.code;
        }

        @Override
        public String getValue() {
            return this.desp;
        }
    }

    public enum QB_AutoStatus implements IDictEnum {
        NotRun(0, ""),
        Success(1, "Automation SUCCESS"),
        Failure(2, "Automation FAILURE"),
        Skip(3, "Automation SKIP");
        private int code;
        private String desp;

        QB_AutoStatus(int code, String desp) {
            this.code = code;
            this.desp = desp;
        }

        @Override
        public String toString() {
            // return this.desp;
            return String.valueOf(this.code);
        }

        @Override
        public Integer getCode() {
            return this.code;
        }

        @Override
        public String getValue() {
            return this.desp;
        }
    }

    public static String getStatusByTypeCode(int code, String type) {
        if ("qa".equalsIgnoreCase(type)) {
            EnumSet<QB_QAStatus> currEnumSet = EnumSet.allOf(QB_QAStatus.class);
            for (QB_QAStatus item : currEnumSet) {
                if (item.getCode() == code) return item.getValue();
            }
        } else if ("auto".equalsIgnoreCase(type)) {
            EnumSet<QB_AutoStatus> currEnumSet = EnumSet.allOf(QB_AutoStatus.class);
            for (QB_AutoStatus item : currEnumSet) {
                if (item.getCode() == code) return item.getValue();
            }
        }
        return null;
    }

    public enum QB_METHOD implements IDictEnum {
        AUTOMATION(0, "Automation"),
        MANUAL(1, "Manual");
        private int code;
        private String desp;

        QB_METHOD(int code, String desp) {
            this.code = code;
            this.desp = desp;
        }

        @Override
        public String toString() {
            // return this.desp;
            return String.valueOf(code);
        }

        @Override
        public Integer getCode() {
            return this.code;
        }

        @Override
        public String getValue() {
            return this.desp;
        }
    }

    public enum QB_SKUS implements IDictEnum {
        UNKNOW(0, ""),
        SIMPLE(1, "Simple Start"),
        ESSENTIALS(2, "Essentials"),
        PLUS(3, "Plus");

        private int code;
        private String desp;

        QB_SKUS(int code, String desp) {
            this.code = code;
            this.desp = desp;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
        }

        @Override
        public Integer getCode() {
            return this.code;
        }

        @Override
        public String getValue() {
            return this.desp;
        }
    }

    public static QB_METHOD getMethodByCode(int code) {
        EnumSet<QB_METHOD> currEnumSet = EnumSet.allOf(QB_METHOD.class);
        for (QB_METHOD item : currEnumSet) {
            if (item.getCode() == code) return item;
        }
        return null;
    }

    public static String getMethodStrByCode(int code) {
        QB_METHOD ret = getMethodByCode(code);
        return ret == null ? null : ret.getValue();
    }

    public static QB_SKUS getSKUsByCode(int code) {
        EnumSet<QB_SKUS> currEnumSet = EnumSet.allOf(QB_SKUS.class);
        for (QB_SKUS item : currEnumSet) {
            if (item.getCode() == code) return item;
        }
        return null;
    }

    public static String getSkusStrByCode(int code) {
        QB_SKUS ret = getSKUsByCode(code);
        return ret == null ? null : ret.getValue();
    }

    public static <T extends Enum<T>> T getEnumTypeBy(Class<T> enumType, int code) {
        EnumSet<T> currEnumSet = EnumSet.allOf(enumType);
        for (T item : currEnumSet) {
            try {
                if (Integer.parseInt(item.toString()) == code) return item;
            } catch (NumberFormatException e) {
                System.out.println(
                        ">>>>>> ERROR!!! "
                                + enumType.getName()
                                + ".toString() is uncorrect.  item.code = ["
                                + item.toString()
                                + "]");
            }
        }

        return null;
    }

    public static <T extends Enum<T>> T getEnumTypeByCode(Class<T> enumType, int code) {
        EnumSet<T> currEnumSet = EnumSet.allOf(enumType);
        for (T item : currEnumSet) {
            try {
                if (Integer.parseInt(item.toString()) == code) return item;
            } catch (NumberFormatException e) {
                System.out.println(
                        ">>>>>> ERROR!!! "
                                + enumType.getName()
                                + ".toString() is uncorrect.  item.code = ["
                                + item.toString()
                                + "]");
            }
        }

        return null;
    }

    public static <T extends Enum<T>> String getEnumTypeValueByCode(Class<T> enumType, int code) {
        T t = getEnumTypeByCode(enumType, code);
        if (t == null) return null;

        String retStr = null;

        try {
            // Reflect
            Method method = enumType.getMethod("getValue", new Class[]{});
            retStr = method.invoke(t, new Object[]{}).toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("getEnumTypeValueByCode() ERROR!!!" + e.getMessage());
        }
        return retStr;
    }

    public static void main(String[] args) {
        System.out.println(getEnumTypeValueByCode(OPERATION_TYPE.class, 1));
    }
}