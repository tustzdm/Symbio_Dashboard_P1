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

        @Override
        String toString();
    }

    ///////////////////////////////////////////////////////
    // Record relevant setting
    ///////////////////////////////////////////////////////

    /**
     * Common Setting for ${record}.validation
     * Same to EntityDisplay
     */
    @Getter
    public enum ENTITY_VALIDATION implements IDictEnum {
        INVALID(0, "invalid"),
        VALID(1, "valid"),
        DELETE(4, "removed");

        private Integer code;
        private String value;

        ENTITY_VALIDATION(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    /**
     * Common Setting for ${record}.validation
     * Same to EntityDisplay
     */
    @Getter
    public enum ENTITY_BOOL implements IDictEnum {
        NO(0, "false"),
        YES(1, "true");

        private Integer code;
        private String value;

        ENTITY_BOOL(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    /**
     * Common Setting for ${record}.validation
     * Same to EntityDisplay
     */
    @Getter
    public enum ENTITY_VALUE_TYPE implements IDictEnum {
        INT(0, "Int"),
        STRING(1, "String"),
        BOOLEAN(2, "Boolean"),
        JSON(10, "json"),
        JSON_ARRAY(11, "json array"),

        MAP(20, "Map"); // not use for db

        private Integer code;
        private String value;

        ENTITY_VALUE_TYPE(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }
    ///////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////
    // Logic relevant setting
    ///////////////////////////////////////////////////////
    @Getter
    public enum LOGIC_INTEGER implements IDictEnum {
        UN_KNOWN(-1, "UnKnown"),
        FALSE(0, "false"),
        TRUE(1, "true");

        private Integer code;
        private String value;

        LOGIC_INTEGER(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }
    ///////////////////////////////////////////////////////

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
    @Getter
    public enum TEST_SET_TYPE implements IDictEnum {
        AUTOMATION(1, "Automation Test"),
        MANUAL(2, "Manual Test"),
        API(4, "API Test"),
        PERFORMANCE(8, "Performance Test");

        private Integer code;
        private String value;

        TEST_SET_TYPE(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    /**
     * [TestCase.case_type]Case Type == Unknown + [TestSet.type]Test Set Type
     */
    @Getter
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
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    /**
     * Priority
     */
    @Getter
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

        private Integer code;
        private String value;

        OPERATION_TYPE(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    public static OPERATION_TYPE getOPERATIONTypeById(Integer id) {
        EnumSet<OPERATION_TYPE> currEnumSet = EnumSet.allOf(OPERATION_TYPE.class);
        for (OPERATION_TYPE item : currEnumSet) {
            if (item.getCode() == id) return item;
        }
        return null;
    }

    @Getter
    public enum MENU_TYPE implements IDictEnum {
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

        private Integer code;
        private String value;

        MENU_TYPE(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    public static MENU_TYPE getMenuById(Integer id) {
        EnumSet<MENU_TYPE> currEnumSet = EnumSet.allOf(MENU_TYPE.class);
        for (MENU_TYPE item : currEnumSet) {
            if (item.getCode() == id.intValue()) return item;
        }
        return null;
    }

    /**
     * Automation Job actual status
     */
    @Getter
    public enum JENKINS_JOB_STATUS implements IDictEnum {
        // Pending
        PENDING(0, ""),
        RUNNING(1, "Running"),
        // Result
        AUTO_SUCCESS(2, "SUCCESS"),
        AUTO_FAILURE(3, "FAILURE"),
        ABORTED(4, "ABORTED"),

        AUTO_SKIP(5, "Skipped"),
        WAITING(6, "waiting"),
        ERROR(7, "error");

        private Integer code;
        private String value;

        JENKINS_JOB_STATUS(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
        }
    }

    /**
     * Judge update Test Run's job weather or not if need
     *
     * @param enumItem
     * @return
     */
    public static boolean isUpdateJobWeatherStatus(JENKINS_JOB_STATUS enumItem) {
        boolean bRet = false;

        switch (enumItem) {
            default:
                break;
            case AUTO_SUCCESS:
            case AUTO_FAILURE:
            case ABORTED:
            case AUTO_SKIP:
            case ERROR:
                bRet = true;
                break;
        }

        return bRet;
    }

    /**
     * Jenkins job is terminated or not
     *
     * @param enumItem
     * @return
     */
    public static boolean isJenkinsEndStatus(JENKINS_JOB_STATUS enumItem) {
        boolean bRet = false;

        Integer itemCode = enumItem.getCode();
        for (JENKINS_JOB_STATUS item : JENKINS_JOB_STATUS.values()) {
            itemCode = item.getCode();

            if (itemCode >= JENKINS_JOB_STATUS.AUTO_SUCCESS.getCode()
                    && itemCode <= JENKINS_JOB_STATUS.ABORTED.getCode()) {
                bRet = true;
            }
        }

        return bRet;
    }

    /**
     * File extension defined for Automation Report file
     */
    @Getter
    public enum JENKINS_AUTO_REPORT_FILE_EXTENSION implements IDictEnum {
        ZIP(1, "zip"),
        TAR_GZ(2, "tar.gz"),
        TAR(3, "tar");

        private Integer code;
        private String value;

        JENKINS_AUTO_REPORT_FILE_EXTENSION(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
        }
    }

    @Getter
    public enum REPORT_FILE_PARSE_STATUS implements IDictEnum {
        UNDO(0, "Undo"),
        SUCCESS(2, "Success"),
        EXCEPTION(3, "Exception"),
        FAIL(4, "Fail"),
        EXTRACT(10, "Unzip"),
        TEST_RUN(15, "TestRun"),
        TEST_RESULT(20, "TestResult"),
        CLEAN(25, "Clean"),
        ACHIEVED(30, "Achieved");

        private Integer code;
        private String value;

        REPORT_FILE_PARSE_STATUS(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
        }
    }

    /**
     * Separated by Product or not
     */
    @Getter
    public enum TESTCASE_SEPARATED_PRODUCT implements IDictEnum {
        YES(1, "Yes"),
        NO(0, "No");

        private Integer code;
        private String value;

        TESTCASE_SEPARATED_PRODUCT(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
        }
    }

    /**
     * Separated by Product or not
     */
    @Getter
    public enum TEP_SEPARATED_PRODUCT implements IDictEnum {
        YES(1, "Yes"),
        NO(0, "No");

        private Integer code;
        private String value;

        TEP_SEPARATED_PRODUCT(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
        }
    }

//    /**
//     * Jenkins job status
//     */
//    @Getter
//    public enum JENKINS_JOB_STATUS implements IDictEnum {
//
//        NOT_RUN(0, "Not Run"),
//        AUTO_SUCCESS(1, "Passed"),
//        AUTO_FAILURE(2, "Failed"),
//        AUTO_SKIP(3, "Skipped"),
//        WAITING(4, "waiting"),
//        RUNNING(5, "running"),
//        ERROR(6, "error");
//
//        private Integer code;
//        private String value;
//
//        JENKINS_JOB_STATUS(int code, String value) {
//            this.code = code;
//            this.value = value;
//        }
//
//        @Override
//        public String toString() {
//            return String.valueOf(code);
//        }
//    }

    /**
     * @Author - Danny
     * @Description - Whether replace respective result in success of test run while importing test case
     * @Date - 2019/8/13
     * @Param -
     * @return
     */
    public enum TESTCASE_IMP_REPLACE_SUCC implements IDictEnum {
        IGNORE(0, "Neither update"),
        UPDATETC(1, "Update Test Case Only"),
        BOTHUPDATE(2, "Update Test Case and Test Run");

        private int code;
        private String value;

        TESTCASE_IMP_REPLACE_SUCC(int code, String value) {
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

    @Getter
    public enum TEST_RUN_STATUS implements IDictEnum {
        NOT_RUN(0, "Not Run"),
        SUCCESS(1, "Success"),
        FAIL(4, "Fail"),
        SKIP(5, "Skip")
        // ....  Manual Status, API status, Performance status
        ;

        private Integer code;
        private String value;

        TEST_RUN_STATUS(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    public static boolean isUpdateTestResultByTRunStatus(Integer trStatus) {
        if (trStatus != TEST_RUN_STATUS.SUCCESS.getCode()) {
            return true;
        } else {
            return false;
        }
    }

    @Getter
    public enum JENKINS_REPORT_FILE_STATUS implements IDictEnum {
        UN_KNOWN(0, "Unknown"),
        SUCCESS(1, "Passed"),
        FAIL(4, "Failed"),
        SKIP(5, "Skipped");

        private Integer code;
        private String value;

        JENKINS_REPORT_FILE_STATUS(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    @Getter
    public enum TEST_RESULT_STATUS implements IDictEnum {
        NOT_RUN(0, "Not Run"),
        AUTOMATION_SUCCESS(1, "Automation Success"),
        AUTOMATION_FAIL(4, "Automation Fail"),
        AUTOMATION_SKIP(5, "Automation Skip"),
        AUTOMATION_SUCCESS_WITHIN_PERCENTAGE(6, "Automation Success with Percentage"),
        AUTOMATION_STARTED(8, "Automation Started"),

        QA_CONDITION_PASS(11, "conditional pass"),
        QA_TEST_NOT_REQUIRED(10, "Test Not Required"),
        QA_SUPPORT(12, "QA Support"),
        QA_LQA_SUPPORT(13, "LQA Support"),
        QA_IQA_SUPPORT(14, "IQA Support")
        // ....  Manual Status, API status, Performance status
        ;

        private Integer code;
        private String value;

        TEST_RESULT_STATUS(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    @Getter
    public enum USER_STATUS implements IDictEnum {
        IN_ACTIVE(0, "inactive"),
        ACTIVE(1, "active"),
        DELETE(4, "removed");

        private Integer code;
        private String value;

        USER_STATUS(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    @Getter
    public enum USER_CHANNEL implements IDictEnum {
        LOCAL(0, "local"),
        LDAP(1, "LDAP");

        private Integer code;
        private String value;

        USER_CHANNEL(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

//    @Getter
//    public enum USER_LEVEL implements IDictEnum {
//        USER(0, "User"),
//
//        ADMIN(1, "Admin"),
//        MANAGER(2, "Manager"),
//
//        CLIENT_MANAGER(5, "Client Manager"),
//        CLIENT(6, "Client"),
//
//        QA_LEADER(10, "QA Leader"),
//        QA(11, "QA"),
//        IQA(12, "IQA"),
//        LQA(13, "LQA"),
//
//        ENGINEER_LEADER(20, "Engineer"),
//        ENGINEER(21, "Engineer"),
//        DEVELOPER(25, "Developer"),
//
//        SYSTEM_ADMIN(912, "System Admin");
//
//        private Integer code;
//        private String value;
//
//        USER_LEVEL(int code, String value) {
//            this.code = code;
//            this.value = value;
//        }
//
//        @Override
//        public String toString() {
//            return String.valueOf(this.code);
//        }
//    }

//    @Getter
//    public enum ROLE implements IDictEnum {
//        USER(0, "User"), // CompanyUser
//
//        ADMIN(1, "Admin"),
//        COMPANY_MANAGER(2, "CompanyManager"),
//        COMPANY_ASSISTANT(3, "CompanyAssistant"),
//        PRODUCT_MANAGER(5, "ProductManager"),
//        PRODUCT_USER(6, "ProductUser"),
//
//        CLIENT_MANAGER(7, "ClientManager"),
//        CLIENT(8, "Client"),
//
//        QA_LEADER(20, "QA Leader"),
//        QA(21, "QA"),
//
//        IQA_LEADER(22, "IQA Leader"),
//        IQA(23, "IQA"),
//
//        LQA_LEADER(24, "LQA Leader"),
//        LQA(25, "LQA"),
//
//        AUTOMATION_LEADER(30, "AutomationLeader"),
//        AUTOMATION(31, "Automation"),
//
//        ENGINEER_LEADER(35, "EngineerLeader"),
//        ENGINEER(36, "Engineer"),
//
//        DEVELOPER_LEADER(38, "DeveloperLeader"),
//        DEVELOPER(37, "Developer"),
//
//        ANONYMOUS_USER(1948, "AnonymousUser"),
//        SYSTEM_ADMIN(912, "SystemAdmin");
//
//        private Integer code;
//        private String value;
//
//        ROLE(int code, String value) {
//            this.code = code;
//            this.value = value;
//        }
//
//        @Override
//        public String toString() {
//            return String.valueOf(this.code);
//        }
//
//        public String getRoleName() {
//            return getValue();
//        }
//    }

    @Getter
    public enum SCREEN_FLAG_STATUS implements IDictEnum {
        UNKNOWN(0, "No"),
        YES(1, "Yes");

        private Integer code;
        private String value;

        SCREEN_FLAG_STATUS(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    @Getter
    public enum TEST_RESULT_QA_STATUS implements IDictEnum {
        UN_KNOWN(0, "Unknown"),
        PASS(1, "Pass"),
        FAIL(4, "Fail"),
        CONDITION_PASS(11, "conditional pass"),
        TNR(10, "Testing Not Required"),
        QA_SUPPORT(12, "QA Support");

        private Integer code;
        private String value;

        TEST_RESULT_QA_STATUS(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }


    // Refer to : DictionaryType.SCREEN_SHOT_STATUS_LOCALE
    @Getter
    public enum SCREEN_SHOT_STATUS implements IDictEnum {
        Pending(0, "Pending"),
        PASS(1, "Pass"),
        FAIL(4, "Failed"),
        CONDITION_PASS(11, "Conditional Pass");

        private Integer code;
        private String value;

        SCREEN_SHOT_STATUS(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    // Used to : [comment_info].comment_type
    @Getter
    public enum COMMENT_INFO_TYPE implements IDictEnum {
        SCREEN_SHOT(1, "screen_shot"); // try to use CommonUtil.getCamelField(..+ "_id") later

        private Integer code;
        private String value;

        COMMENT_INFO_TYPE(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
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

    @Getter
    public enum QB_API_STATUS implements IDictEnum {
        UNKnown(0, ""),
        SUCCESS(1, "success"),
        LQA_FAIL(4, "LQA Failure"),
        AUTO_FAIL(5, "Auto Failure");
        private Integer code;
        private String value;

        QB_API_STATUS(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
        }

    }

    @Getter
    public enum QB_BROWSERS implements IDictEnum {
        UNKnown(0, ""),
        Firefox(1, "Firefox"),
        Chrome(2, "Chrome");
        private Integer code;
        private String value;

        QB_BROWSERS(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
        }
    }

    public static QB_BROWSERS getBrowsersByCode(int code) {
        EnumSet<QB_BROWSERS> currEnumSet = EnumSet.allOf(QB_BROWSERS.class);
        for (QB_BROWSERS item : currEnumSet) {
            if (item.getCode() == code) return item;
        }
        return null;
    }

    @Getter
    public enum QB_QAStatus implements IDictEnum {
        BLANK(0, ""),
        Pass(1, "pass"),
        Conditional_Pass(2, "conditional pass"),
        Fail(4, "fail"),
        TestNotReq(5, "Testing Not Required");
        private Integer code;
        private String value;

        QB_QAStatus(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
        }
    }

    @Getter
    public enum QB_AutoStatus implements IDictEnum {
        NotRun(0, ""),
        Success(1, "Automation SUCCESS"),
        Failure(2, "Automation FAILURE"),
        Skip(3, "Automation SKIP");
        private Integer code;
        private String value;

        QB_AutoStatus(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.code);
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

    @Getter
    public enum QB_METHOD implements IDictEnum {
        AUTOMATION(0, "Automation"),
        MANUAL(1, "Manual");

        private Integer code;
        private String value;

        QB_METHOD(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            // return this.desp;
            return String.valueOf(code);
        }
    }

    @Getter
    public enum QB_SKUS implements IDictEnum {
        UNKNOW(0, ""),
        SIMPLE(1, "Simple Start"),
        ESSENTIALS(2, "Essentials"),
        PLUS(3, "Plus");

        private Integer code;
        private String value;

        QB_SKUS(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
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

    public static <T extends Enum<T>> T getEnumTypeByValue(Class<T> enumType, String value) {
        if (value == null || value.trim().length() == 0) {
            return null;
        }

        try {
            EnumSet<T> currEnumSet = EnumSet.allOf(enumType);
            String strValue = null;

            for (T item : currEnumSet) {
                try {
                    // Reflect
                    Method method = enumType.getMethod("getValue", new Class[]{});
                    strValue = method.invoke(item, new Object[]{}).toString();
                    if ((value.equals(strValue))) {
                        return item;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(
                            ">>>>>> ERROR!!! "
                                    + enumType.getName()
                                    + ".toString() is uncorrect.  item.code = ["
                                    + item.toString()
                                    + "]");
                } catch (NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T extends Enum<T>> T getEnumTypeByCode(Class<T> enumType, int code) throws Exception {
        EnumSet<T> currEnumSet = EnumSet.allOf(enumType);
        for (T item : currEnumSet) {
            try {
                if (Integer.parseInt(item.toString()) == code) return item;
            } catch (NumberFormatException e) {
                String strMsg = ">>>>>> ERROR!!! "
                        + enumType.getName()
                        + ".toString() is uncorrect.  item.code = ["
                        + item.toString()
                        + "]";
                System.out.println(strMsg);
                throw new Exception(strMsg);
            }
        }

        return null;
    }

    public static <T extends Enum<T>> String getEnumTypeValueByCode(Class<T> enumType, int code) throws Exception {
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
        try {
            System.out.println(getEnumTypeValueByCode(ENTITY_VALIDATION.class, 1));

            EnumSet<ENTITY_VALIDATION> currEnumSet = EnumSet.allOf(ENTITY_VALIDATION.class);
            for (ENTITY_VALIDATION item : currEnumSet) {
                try {
                    System.out.println(String.format("code=%s, value=%s, toString=%s", item.getCode(), item.getValue(), item.toString()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}