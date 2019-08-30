package com.symbio.dashboard.constant;

import com.symbio.dashboard.Result;

public class ErrorConst {

    // ERROR Code corresponds to [result_message]
    // public final static String ="";
    public final static String EXCEPTION = "000004";
    public final static String DB_NODATA_ARGS_TABLE_ID = "000016";
    public final static String ERROR_PARAMETER_EMPTY = "000101";
    public final static String MISSING_FUNCTION_PARAMETER = "000019";

    public final static String JENKINS_FETCH_JOBARGS_AUTOMATICALLY = "300503";
    public final static String JENKINS_JOB_NO_PARAMETER = "300504";

    // sucess
    public final static String SUCESS = "0";
    public final static String SUCESS_M = "";
    // failure
    public final static String ERROR = "NE0000";
    public final static String ERROR_M = "Failure";

    public final static String TESTCASE_IMPEXCEL_EXCEPTION = "N09011";
    public final static String TESTCASE_IMPEXCEL_EXCEPTION_M = "Import excel format Exception";

    public final static String TESTCASE_IMPEXCEL_NOTSUPPORT = "N09011";
    public final static String TESTCASE_IMPEXCEL_NOTSUPPORT_M = "Only support Excel file for importing Test Case";

    // Util - File
    public final static String FILE_UTIL_FILENAME_EMPTY = "002002";
    public final static String FILE_UTIL_FILENAME_EMPTY_M = "File Name is empty";
    public final static String FILE_UTIL_NOT_EXACT_FILE = "002001";
    public final static String FILE_UTIL_NOT_EXACT_FILE_M = "File does not exist or file is directory. fileName = [%s]";

    // Parse Report file
    public final static String PARSE_REPORT_MOVE_DIRECOTRY = "500104";
    public final static String PARSE_REPORT_MOVE_DIRECOTRY_M = "Move zip file to work directory failure";


    public static Result getExceptionResult(String strMsg) {
        return new Result(EXCEPTION, String.format("Exception happened while %s", strMsg));
    }

    public static Result getExceptionResult(String strMsg, Exception e) {
        return new Result(EXCEPTION, String.format("Exception happened while %s. Exception message: %s", strMsg, e.getMessage()));
    }

    public final static String LOG_SYMBOL_ERROR = "!!!ERROR!!!";
    public final static String LOG_SYMBOL_WARNING = "WARNING!!!";
    public final static String LOG_SYMBOL_EXCEPTION = "!!!EXCEPTION!!!";

    public static String getErrorLogMsg(String function, Result result) {
        return String.format("%s %s ec = %s, em = %s", function, LOG_SYMBOL_ERROR, result.getEc(), result.getEm());
    }

    public static String getWarningLogMsg(String function, Result result) {
        return String.format("%s %s ec = %s, em = %s", function, LOG_SYMBOL_WARNING, result.getEc(), result.getEm());
    }

    public static String getWarningLogMsg(String function, String msg) {
        return String.format("%s %s %s", function, LOG_SYMBOL_WARNING, msg);
    }

    public static String getExceptionLogMsg(String function, Exception e) {
        return String.format("Invoke %s %s ErrorMsg: %s", function, LOG_SYMBOL_EXCEPTION, e.getMessage());
    }
}
