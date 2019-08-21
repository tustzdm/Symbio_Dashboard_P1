package com.symbio.dashboard.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * Constants defined
 */
@Component
public class CommonDef {

    @Value("${app.file.root}")
    private String rootDirectory;

    @PostConstruct
    public void setProjectConfigService() {
        manualInitProperty();
        checkDirectories();
    }

    public static final String FOLDER_NAME_ZIP_ROOT = "ziproot";
    public static final String FOLDER_NAME_WORK_ROOT = "testrunroot";
    public static final String FOLDER_NAME_ZIP_BACKUP = "bakzip";
    public static final String FOLDER_NAME_IMAGE = "image";
    public static final String FOLDER_NAME_LOG = "logo";

    public static String enclosedAllFolder(String pathName) {
        return String.format("/%s/", pathName);
    }

    public static String concatEnclosedAllFolder(String pathName, String subPathName) {
        return String.format("/%s/%s/", pathName, subPathName);
    }

    private void manualInitProperty() {
        LOGIN_ENV = "dev";
        // LOGIN_ENV = "angular_debug";
        COOKIE_DOMAIN = "autodash";
        USER_EXPIRE_TIME = 24 * 60;
        PIC_ROOT = rootDirectory;
        PIC_UPLOAD = PIC_ROOT + "/upload/";
        FOLDER_PATH_IMPORT = PIC_ROOT + "/import/";
        FOLDER_PATH_IMPORT_TESTCASE = FOLDER_PATH_IMPORT + "testcase";
        FOLDER_PATH_EXPORT = PIC_ROOT + "/export/";
        FOLDER_PATH_DASHBOARD_ZIP_ROOT = PIC_ROOT + enclosedAllFolder(FOLDER_NAME_ZIP_ROOT);
        FOLDER_PATH_TESTRUN_ROOT = PIC_ROOT + enclosedAllFolder(FOLDER_NAME_WORK_ROOT);
        FOLDER_PATH_TESTRUN_BAKZIP_ROOT = PIC_ROOT + concatEnclosedAllFolder(FOLDER_NAME_WORK_ROOT, FOLDER_NAME_ZIP_BACKUP);
        DIR_HTTP_SCREENSHOT = PIC_ROOT + enclosedAllFolder(FOLDER_NAME_IMAGE);
        PIC_LOGO_ROOT = PIC_ROOT + concatEnclosedAllFolder(FOLDER_NAME_IMAGE, FOLDER_NAME_LOG);
    }

    private void checkDirectories() {
        createDicertoryIfNotExist(PIC_ROOT);
        createDicertoryIfNotExist(PIC_UPLOAD);
        createDicertoryIfNotExist(FOLDER_PATH_IMPORT);
        createDicertoryIfNotExist(FOLDER_PATH_IMPORT_TESTCASE);
        createDicertoryIfNotExist(FOLDER_PATH_EXPORT);
        createDicertoryIfNotExist(FOLDER_PATH_DASHBOARD_ZIP_ROOT);
        createDicertoryIfNotExist(FOLDER_PATH_TESTRUN_ROOT);
        createDicertoryIfNotExist(FOLDER_PATH_TESTRUN_BAKZIP_ROOT);
        createDicertoryIfNotExist(DIR_HTTP_SCREENSHOT);
        createDicertoryIfNotExist(PIC_LOGO_ROOT);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createDicertoryIfNotExist(String directory) {
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static final long TIME_SETTING = 30 * 60 * 1000;

    public static final String EMAILSUBJECT = "[SYMBIO]Recover your account password";

    public static final String testLocale = "en_US";

    public static String MOBILEAPPLOCALE = "mobileAppLocale";
    public static String MOBILEAPPLANGUAGE = "mobileAppLanguage";
    public static String MOBILEDEVICE = "mobileDevice";
    public static String APPPATH = "appPath";

    public static final String REPORTFILENAME = "ReportFilename";

    public static final String TEST = "testName";

    public static final String TEST_LANG_LOCALE = "TEST_LANG_LOCALE";

    public static final String LOCALES = "Locales";

    public static final String CHANNEL_PC = "0";

    public static final String EXT = "zip";

    public static final String HTML = "RuntimeReporter";

    public static final String PNG = "png";

    public static final String IMG = "img/";

    public static final String EN_US = "en_US";

    public static final String IMAGE = "image/";

    public static final String ZIP = "zipTempFile";

    public static final String TARGET = "target/";

    public static final String SUREFIREREPORTS = "surefire-reports/";

    public static final String DOUBLEQUOTA = "\"";

    public static final String SCREENSHOTS = "screenshots/";

    public static final String SOURCES = "sources/";

    public static final String JSON = "report.json";


    public static final String COMMA = ",";

    public static final String HORIZONTALLINE = "-";

    public static final String WEBINF = "WEB-INF";

    public static final String WINDOWS = "WINDOWS";

    public static final String UPLOAD = "upload/";

    public static final String DASHBOARD = "dashboard";

    public static final String SCREENSHOT = "screenshot";

    public static final String THUMBNAIL = "thumbnail";

    public static final String LOGO = "logo/";

    public static final String LEFTDOUBLEBRACE = "[[";

    public static final String LEFTDOUBLEBRACESPLIT = "\\[\\[";

    public static final String RIGHTDOUBLEBRACE = "]]";

    public static final String RIGHTDOUBLEBRACESPLIT = "\\]\\]";

    public static final String LEFTPARENTHESIS = "(";
    public static final String RIGHTPARENTHESIS = ")";

    public static final String OR = "OR";

    public static final String AND = " AND ";

    public static final String RELEASETITLE = "Testing for build v";
    public static final String PRQAEFIXRELEASEPIETITLE = "QA v";
    public static final String SUFFIXRELEASEPIETITLE = " Automation Testing";
    public static final String WAVYLINES = "~";

    public static final String FORCEDOWNLOAD = "application/force-download";
    public static final String CONTENTDISPOSITION = "Content-Disposition";
    public static final String ATTACHMENT = "attachment;fileName=";
    public static final String EML = ".eml";

    // Use dev login method development, online using the online
    public static String LOGIN_ENV = "angular_debug";

    public static String EMAIL_SERVICE;

    public static String PIC_ROOT;
    public static String PIC_UPLOAD;
    // public static String PIC_HTTP_UPLOAD;
    public static String PIC_HTTP_ROOT;
    public static String PIC_HTTP_SCREENSHOT;
    public static String FOLDER_PATH_IMPORT;
    public static String FOLDER_PATH_IMPORT_TESTCASE;
    public static String FOLDER_PATH_EXPORT;
    public static String PIC_LOGO_ROOT;

    public static int USER_EXPIRE_TIME;

    public static String COOKIE_TIMEOUT;

    public static String COOKIE_DOMAIN;
    /**
     * Verify the switch
     */
    public static String SYS_PRODUCTMODE;

    public static String STRDEFAULTKEY;

    ///////////////////////////////////////////////////////
    // AutoDash
    ///////////////////////////////////////////////////////

    public static int TEST_CASE_LEVEL3 = 3; // 3: interview 4:common
    public static int TEST_CASE_LEVEL4 = 4; // 3: interview 4:common

    public static int TESTCASEID_FORMAT_LEN = 4;

    public static int TESTCASEID_SEQUENCE_INIT = 1;

    public static String FOLDER_PATH_DASHBOARD_ZIP_ROOT; // FTP -> WorkDir/
    public static String FOLDER_PATH_TESTRUN_ROOT; // TestRun/YYYYMMDDHHMMSSsss/
    public static String DIR_HTTP_SCREENSHOT;
    public static String DASHBOARD_PROJECT_ADDRESS;
    public static String FOLDER_PATH_TESTRUN_BAKZIP_ROOT;

    /**
     * Repeat submit a request of the switch
     */
    public final static String REQUEST_DUPLICATE_SWITCH = "_GLOBAL_SESSION_DUPLICATE_SWITCH";
    /**
     * The KEY of repeat submit a request
     */
    public final static String REQUEST_DUPLICATE_KEY = "_GLOBAL_REQUEST_DUPLICATE_KEY";
    /**
     * Repeat submitted to the session KEY
     */
    public final static String SESSION_DUPLICATE_KEY = "_GLOBAL_SESSION_DUPLICATE_KEY";
    /**
     * Repeat submitted to the session KEY
     */
    public final static String REQUEST_SUBMIT_TYPE = "_GLOBAL_SUBMIT_TYPE_KEY";
    /**
     * Repeat submitted to the session KEY
     */
    public final static String DUPLICATE_SUBMIT_PAGE = "common/dupsubmit";
    /**
     * Repeat submitted to the session KEY
     */
    public final static String DUPLICATE_ERRCD = "9999";
    /**
     * Stand inside letter category - - other investors
     */
    public static final short INNER_MAIL_TYPE_INVESTOR_OTHER = 199;
    /**
     * login token check
     */
    public final static String LOGIN_SESSION_TOKEN = "token";

    /**
     * login userVo
     */
    public final static String LOGIN_SESSION_USERINFO = "userVO";

    final public static String WEBSITE_NAME_CN = "SYMBIO";
    /**
     * Investment failure
     */
    public static final short TRADE_TYPE_INVEST_FAILURE = 180;

    /**
     * Withdrawal of failure
     */
    public static final short TRADE_TYPE_WITHDRAW_FAILURE = 190;

    // active mail overdue days
    public static final short USERREGISTER_ACTIVEMAIL_MAXDAYS = 7;
    public static final int USER_PROFILE_DETAIL_MODEL = 1;
}
