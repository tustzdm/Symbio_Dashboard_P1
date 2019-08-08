package com.symbio.dashboard.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @Author: Shawn
 * @Description: 处理Date的工具类
 * @Date: Created in 2019-07-24
 * @Modified By:
 */
public class DateUtil {

    public static final String WAVYLINES = "~";

    public static Date strToDatetime(String strDate) {
        return strToUtilDate(strDate, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date strToDatetime(Date dateTime) {
        String strFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sf = new SimpleDateFormat(strFormat);
        return strToUtilDate(sf.format(dateTime), strFormat);
    }

    public static Date strToUtilDate(String strDate, String dateFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = sf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String DateToStr(Date dateTime, String df) {
        String strFormat = df;
        if (StringUtil.isEmpty(df)) {
            strFormat = "yyyy-MM-dd HH:mm:ss";
        }
        try {
            SimpleDateFormat sf = new SimpleDateFormat(strFormat);
            return sf.format(dateTime);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date strToFormatDate(String strDate, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年mm月dd日");
        Date date = null;
        try {
            Date d = sdf.parse(strDate);
            sdf = new SimpleDateFormat(dateFormat);
            date = strToUtilDate(sdf.format(d), dateFormat);

            System.out.println(sdf.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int getAgeByBirthday(Date birthday) {
        int age = 0;
        try {
            Calendar cal = Calendar.getInstance();
            if (cal.before(birthday)) { //出生日期晚于当前时间，无法计算
                throw new IllegalArgumentException(
                        "The birthday is beyond today. It's illegal!");
            }
            int yearNow = cal.get(Calendar.YEAR);  //当前年份
            int monthNow = cal.get(Calendar.MONTH);  //当前月份
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
            cal.setTime(birthday);
            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH);
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            age = yearNow - yearBirth;   //计算整岁数
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
                } else {
                    age--;//当前月份在生日之前，年龄减一
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return age;
    }

    /**
     * "yyyy-MM-dd"
     *
     * @param date
     * @return
     */
    public static String formatToYYYYMMDD(Date date) {
        if (date != null) {
            return YZYDateFormat.getInstance("yyyy-MM-dd").format(date);
        } else {
            return "null";
        }

    }

    public static String formatToddMMMMyyyy(Date date) {
        YZYDateFormat format = YZYDateFormat.getInstance("dd MMM yyyy");
        return format.format(date);
    }

    public static String formatToMMMdForTimeLine(Date startTime, Date endTime) {
        if (startTime == null || endTime == null) {
            return "";
        }
        String timeLine = "";
        YZYDateFormat format = YZYDateFormat.getInstance("MMM .d", TimeZone.getTimeZone("Asia/Shanghai"),
                Locale.ENGLISH);
        String startTimeStr = format.format(startTime);
        String endTimeStr = format.format(endTime);
        timeLine = startTimeStr + WAVYLINES + endTimeStr;
        return timeLine;
    }

    public static String formatToYYYYMMDDMMHHSS(Date date) {
        YZYDateFormat format = YZYDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String formatToYYYYMMDDMMHHSSsss(Date date) {
        YZYDateFormat format = YZYDateFormat.getInstance("yyyyMMddHHmmssSSS");
        return format.format(date);
    }

    public static Date formatToYYYYMMDDMMHHSSsss(String s) throws ParseException {
        YZYDateFormat format = YZYDateFormat.getInstance("yyyyMMddHHmmssSSS");
        return format.parse(s);
    }

    public static String formatToYYYYMMDDMMHH(Date date) {
        YZYDateFormat format = YZYDateFormat.getInstance("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public static Date formatToDayByYYYYMMDD(String s) throws ParseException {
        YZYDateFormat format = YZYDateFormat.getInstance("yyyy-MM-dd");
        return format.parse(s);
    }

    public static Date formatToDayByUTC(String s) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return strYMD  yyyyMMdd String
     * @throws
     */
    public static String getYMD() {
        String strYMD = "";
        Date currentDateTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        strYMD = formatter.format(currentDateTime);
        return strYMD;
    }


    /**
     * @return strYMDHMS  yyyyMMddHHmmssString
     * @throws
     */
    public static String getHMS() {
        String strYMDHMS = "";
        Date currentDateTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
        strYMDHMS = formatter.format(currentDateTime);
        return strYMDHMS;
    }

    /**
     * @param date1
     * @param date2
     * @param stype
     * @return
     */
    public static int compareDate(String date1, String date2, int stype) {
        int n = 0;

        String[] u = {"天", "月", "年"};
        String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";

        date2 = date2 == null ? DateUtil.getCurrentDate() : date2;

        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
        } catch (Exception e3) {
            System.out.println("wrong occured");
        }
        //List list = new ArrayList();
        while (!c1.after(c2)) {
            //list.add(df.format(c1.getTime()));
            n++;
            if (stype == 1) {
                c1.add(Calendar.MONTH, 1);
            } else {
                c1.add(Calendar.DATE, 1);
            }
        }

        n = n - 1;

        if (stype == 2) {
            n = (int) n / 365;
        }
        return n;
    }


    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(date);

    }


    public static int getCurrentDateYYYY() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy");
        String year = simple.format(date);
        return Integer.parseInt(year);
    }

    public static String addNumDay(int day) {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        c.setTime(date);
        c.add(c.DATE, day);
        date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(date);
    }

    /**
     * @param currentDate
     * @param lastDate
     * @return
     */
    public static int getDifferDays(Date currentDate, Date lastDate) {
        if (currentDate == null || lastDate == null) {
            return 0;
        }
        SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd");
        int differDays = compareDate(sb.format(lastDate), sb.format(currentDate), 0);
        return differDays;
    }

    /**
     * @param currentDate
     * @param lastDate
     * @return
     */
    public static int getDifferSeconds(Date currentDate, Date lastDate) {
        if (currentDate == null || lastDate == null) {
            return 0;
        }
        long start = currentDate.getTime();
        long end = lastDate.getTime();
        int differSeconds = (int) (end - start) / 1000;
        return differSeconds;
    }

    public static String formatToMMDDForEnglishLocale(Date date) {
        String dt = "";
        if (date != null) {
            dt = new SimpleDateFormat("MMM d", Locale.ENGLISH).format(date);
        }
        return dt;

    }

    public static void main(String[] args) {
        System.out.println(getAgeByBirthday(strToFormatDate("1988年04月21日", "yyyy-mm-dd")) + "岁！！");

        System.out.println(formatToddMMMMyyyy(new Date()));
    }
}
