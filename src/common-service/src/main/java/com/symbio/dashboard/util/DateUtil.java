package com.symbio.dashboard.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Shawn
 * @Description: 处理Date的工具类
 * @Date: Created in 2019-07-24
 * @Modified By:
 */
public class DateUtil {

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

    public static void main(String[] args) {
        System.out.println(getAgeByBirthday(strToFormatDate("1988年04月21日", "yyyy-mm-dd")) + "岁！！");
    }
}
