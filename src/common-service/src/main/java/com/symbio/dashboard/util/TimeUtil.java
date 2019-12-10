package com.symbio.dashboard.util;

import java.text.DecimalFormat;

public class TimeUtil {

    public TimeUtil() {
        m_startTime = System.currentTimeMillis();
    }

    private long m_startTime;

    public String runMSTime() {
        long endTime = System.currentTimeMillis();

        DecimalFormat df = new DecimalFormat();
        df.applyPattern(",###.##");
        return String.format("Run time: %s ms", df.format(endTime - m_startTime));
    }

    public String runTime() {
        long endTime = System.currentTimeMillis();
        return String.format("Run time: %.3f Secs", (endTime - m_startTime) / 1000.0);
    }

    public static void main(String[] args) {
        TimeUtil timeUtil = new TimeUtil();
        try {
            Thread.sleep(1234);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(timeUtil.runMSTime());
    }

}
