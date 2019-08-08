package com.symbio.dashboard.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class YZYDateFormat {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH.mm.ss";


    private SimpleDateFormat dateFormat;


    private Locale locale;


    private static ThreadLocal<Map<Key, YZYDateFormat>> formatMapThreadLocal = new ThreadLocal<Map<Key, YZYDateFormat>>();


    private YZYDateFormat(SimpleDateFormat dateFormat, Locale locale) {
        this.dateFormat = dateFormat;
        this.locale = locale;
    }

    /**
     * ,"yyyy-MM-dd HH.mm.ss",
     *
     * @return
     */
    public static YZYDateFormat getInstance() {
        return getInstance(DEFAULT_FORMAT);
    }

    /**
     * @param pattern
     * @param timeZone
     * @param locale
     * @return
     */
    public static YZYDateFormat getInstance(String pattern, TimeZone timeZone, Locale locale) {
        Map<Key, YZYDateFormat> formatMap = formatMapThreadLocal.get();
        if (formatMap == null) {
            formatMap = new HashMap<Key, YZYDateFormat>();
            formatMapThreadLocal.set(formatMap);
        }

        Key key = new Key(pattern, locale);

        YZYDateFormat format = formatMap.get(key);
        if (format == null) {
            format = new YZYDateFormat(new SimpleDateFormat(pattern, locale), locale);
            formatMap.put(key, format);
        }
        format.setTimeZone(timeZone);
        return format;
    }


    public static YZYDateFormat getInstance(String pattern) {
        return getInstance(pattern, TimeZone.getDefault(), Locale.getDefault());
    }

    public String format(Date date) {
        return dateFormat.format(date);
    }


    public String format(long date) {
        return dateFormat.format(new Date(date));
    }


    public Date parse(String source) throws ParseException {
        return dateFormat.parse(source);
    }


    public long parseToLong(String source) throws ParseException {
        return parse(source).getTime();
    }


    public String getPattern() {
        return dateFormat.toPattern();
    }


    public TimeZone getTimeZone() {
        return dateFormat.getTimeZone();
    }


    public void setTimeZone(TimeZone zone) {
        dateFormat.setTimeZone(zone);
    }


    public Locale getLocale() {
        return this.locale;
    }


    private static class Key {
        String pattern;

        Locale locale;

        Key(String pattern, Locale locale) {
            this.pattern = pattern;
            this.locale = locale;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((locale == null) ? 0 : locale.hashCode());
            result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Key other = (Key) obj;
            if (locale == null) {
                if (other.locale != null)
                    return false;
            } else if (!locale.equals(other.locale))
                return false;
            if (pattern == null) {
                if (other.pattern != null)
                    return false;
            } else if (!pattern.equals(other.pattern))
                return false;
            return true;
        }
    }

    public static void main(String[] args) {

        YZYDateFormat format = YZYDateFormat.getInstance("yyyy-MM-dd HH.mm.ss");
        String d = format.format(new Date());
        System.out.println(d);

        // SimpleDateFormat format = new
        // SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SZ");

        // String dateStr = "2010-10-13-09.17.07.099+480";
        // try {
        // Date date = format.parse(dateStr);
        // System.out.println(date.getTime());
        // } catch (ParseException e) {
        // e.printStackTrace();
        // }

        // Date date = new Date();
        // System.out.println(format.format(date));
        // // format.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        // System.out.println(format.format(date));
    }

}
