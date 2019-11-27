package com.symbio.dashboard.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间格式转换工具类(utc时间和本地时间两者的转换)
 *
 * <p>
 * Refer: https://blog.csdn.net/u013412772/article/details/73610803
 */

@Slf4j
public class TimeConverterUtil {

    private static final String UTC_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    public static Date utc2Date(String utcTime) {
        String localDateTimeStr = utc2Local(utcTime, UTC_TIME_PATTERN, DATE_FORMATTER);
        return DateUtil.strToDatetime(localDateTimeStr);
    }

    public static float getUTCTimeZone(String utcTime) {
        float timeZone = 1f;
        String strTimeZone = utcTime.substring("yyyy-MM-ddTHH:mm:ss.SSS".length());

        String sign = "+";
        if (strTimeZone.indexOf("-") != -1) {
            timeZone = -1.0f;
            sign = "-";
        }
        String[] splitTimeArrayOne = strTimeZone.split("\\" + sign);
        String[] splitTimeArrayTwo = splitTimeArrayOne[1].split(":");
        timeZone = timeZone * Integer.parseInt(splitTimeArrayTwo[0]);
        if (Integer.parseInt(splitTimeArrayTwo[1]) > 10) {
            timeZone = timeZone + (sign.equals("+") ? 0.5f : -0.5f);
        }
        return timeZone;
    }

    /**
     * 函数功能描述:UTC时间转本地时间格式
     *
     * @param utcTime         UTC时间
     * @param utcTimePatten   UTC时间格式
     * @param localTimePatten 本地时间格式
     * @return 本地时间格式的时间
     * eg:utc2Local("2017-06-14 09:37:50.788+08:00", "yyyy-MM-dd HH:mm:ss.SSSXXX", "yyyy-MM-dd HH:mm:ss.SSS")
     */
    public static String utc2Local(String utcTime, String utcTimePatten, String localTimePatten) {
        SimpleDateFormat utcFormatter = new SimpleDateFormat(utcTimePatten);
        utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormatter.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return utcTime;
        }
        SimpleDateFormat localFormatter = new SimpleDateFormat(localTimePatten);
        localFormatter.setTimeZone(TimeZone.getDefault());
        String localTime = localFormatter.format(gpsUTCDate.getTime());
        return localTime;
    }

    /**
     * 函数功能描述:UTC时间转本地时间格式
     *
     * @param utcTime          UTC时间
     * @param localTimePattern 本地时间格式(要转换的本地时间格式)
     * @return 本地时间格式的时间
     */
    public static String utc2Local(String utcTime, String localTimePattern) {
        String utcTimePattern = "yyyy-MM-dd";
        // UTC时间格式以 yyyy-MM-dd 开头,将utc时间的前10位截取掉,之后是含有多时区时间格式信息的数据
        String subTime = utcTime.substring(10);

        // 处理当后缀为:+8:00时,转换为:+08:00 或 -8:00转换为-08:00
        if (utcTime.indexOf("+") != -1) {
            subTime = changeUtcSuffix(subTime, "+");
        }
        if (utcTime.indexOf("-") != -1) {
            subTime = changeUtcSuffix(subTime, "-");
        }
        utcTime = utcTime.substring(0, 10) + subTime;

        //依据传入函数的utc时间,得到对应的utc时间格式
        //步骤一:处理 T
        if (utcTime.indexOf("T") != -1) {
            utcTimePattern = utcTimePattern + "'T'";
        }

        //步骤二:处理毫秒SSS
        if (utcTime.indexOf(".") != -1) {
            utcTimePattern = utcTimePattern + " HH:mm:ss.SSS";
        } else {
            utcTimePattern = utcTimePattern + " HH:mm:ss";
        }

        //步骤三:处理时区问题
        if (utcTime.indexOf("+") != -1 || subTime.indexOf("-") != -1) {
            utcTimePattern = utcTimePattern + "XXX";
        } else if (utcTime.indexOf("Z") != -1) {
            utcTimePattern = utcTimePattern + "'Z'";
        }

        if ("yyyy-MM-dd HH:mm:ss".equals(utcTimePattern) || "yyyy-MM-dd HH:mm:ss.SSS".equals(utcTimePattern)) {
            return utcTime;
        }

        SimpleDateFormat utcFormatter = new SimpleDateFormat(utcTimePattern);
        utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUtcDate = null;
        try {
            gpsUtcDate = utcFormatter.parse(utcTime);
        } catch (Exception e) {
            log.error("utcTime converter localTime failed!!!", e);
            return utcTime;
        }
        SimpleDateFormat localFormatter = new SimpleDateFormat(localTimePattern);
        localFormatter.setTimeZone(TimeZone.getDefault());
        String localTime = localFormatter.format(gpsUtcDate.getTime());
        return localTime;
    }

    /**
     * 函数功能描述:修改时间格式后缀
     * 函数使用场景:处理当后缀为:+8:00时,转换为:+08:00 或 -8:00转换为-08:00
     *
     * @param subTime
     * @param sign
     * @return
     */
    private static String changeUtcSuffix(String subTime, String sign) {
        String timeSuffix = null;
        String[] splitTimeArrayOne = subTime.split("\\" + sign);
        String[] splitTimeArrayTwo = splitTimeArrayOne[1].split(":");
        if (splitTimeArrayTwo[0].length() < 2) {
            timeSuffix = "+" + "0" + splitTimeArrayTwo[0] + ":" + splitTimeArrayTwo[1];
            subTime = splitTimeArrayOne[0] + timeSuffix;
            return subTime;
        }
        return subTime;
    }

    /**
     * 函数功能描述:获取本地时区的表示(比如:第八区-->+08:00)
     *
     * @return
     */
    public static String getTimeZoneByNumExpress() {
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();
        int rawOffset = timeZone.getRawOffset();
        int timeZoneByNumExpress = rawOffset / 3600 / 1000;
        String timeZoneByNumExpressStr = "";
        if (timeZoneByNumExpress > 0 && timeZoneByNumExpress < 10) {
            timeZoneByNumExpressStr = "+" + "0" + timeZoneByNumExpress + ":" + "00";
        } else if (timeZoneByNumExpress >= 10) {
            timeZoneByNumExpressStr = "+" + timeZoneByNumExpress + ":" + "00";
        } else if (timeZoneByNumExpress > -10 && timeZoneByNumExpress < 0) {
            timeZoneByNumExpress = Math.abs(timeZoneByNumExpress);
            timeZoneByNumExpressStr = "-" + "0" + timeZoneByNumExpress + ":" + "00";
        } else if (timeZoneByNumExpress <= -10) {
            timeZoneByNumExpress = Math.abs(timeZoneByNumExpress);
            timeZoneByNumExpressStr = "-" + timeZoneByNumExpress + ":" + "00";
        } else {
            timeZoneByNumExpressStr = "Z";
        }
        return timeZoneByNumExpressStr;
    }

}
