package com.symbio.dashboard.data.charts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName - Pie
 * @Author - admin
 * @Description - TODO
 * @Date - 2019/7/31 14:59
 * @Version 1.0
 */

@Service
public class PieChart {

    private static Logger logger = LoggerFactory.getLogger(PieChart.class);

    public Map getProductPieChartInfo(Integer userId, String locale, Integer... productId) {
        Map<String, Object> map = new HashMap<>();
        List<Object> legend = new ArrayList<>();
        List<Object> series = new ArrayList<>();
        try {
            map.put("title", "Pie Chart");
            map.put("legend", legend);
            map.put("series", series);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}
