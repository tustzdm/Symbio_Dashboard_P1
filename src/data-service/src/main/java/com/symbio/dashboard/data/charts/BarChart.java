package com.symbio.dashboard.data.charts;

import com.symbio.dashboard.constant.ProjectConst;
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
 * @ClassName - BarChart
 * @Author - admin
 * @Description
 * @Date - 2019/7/31 16:32
 * @Version 1.0
 */

@Service
public class BarChart {

    private static Logger logger = LoggerFactory.getLogger(BarChart.class);

    public Map getBarCategoryStackChart(Integer userId, String locale, Map<String, Object> data) {

        Map<String, Object> map = new HashMap<>();
        try {
            map.put("key", "BarCategoryStack");
            map.put("data", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map setChartData() {

        Map<String, Object> map = new HashMap<>();
        try {
            String path = PieChart.class.getResource(ProjectConst.BAR_Y_CATEGORY_STACK_CHART).getPath();
            File file = new File(path);
            String content = FileUtil.readAsString(file);
            JSONObject jsonObject = new JSONObject(content);
            map = JSONUtil.toMap(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}