package com.symbio.dashboard.data.charts;

import com.symbio.dashboard.util.JSONUtil;
import org.aspectj.util.FileUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
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

    public Map getPieScrollLegendChart(Integer userId, String locale, Map<String, Object> data) {

        Map<String, Object> map = new HashMap<>();
        try {
            map.put("key", "PieScrollLegend");
            map.put("data", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map setChartData() {

        Map<String, Object> map = new HashMap<>();
        try {
            File file = new File("data-service\\src\\main\\java\\com\\symbio\\dashboard\\data\\json\\PieScrollLegendChart.json");
            String content = FileUtil.readAsString(file);
            JSONObject jsonObject = new JSONObject(content);
            map = JSONUtil.toMap(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    private double getValues() {
        return Math.round(300 + Math.random() * 700) / 10;
    }
}
