package com.symbio.dashboard.report.dto.barLabelRotation;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本类为返回给前端的BarLabelRotation对象的具体数据内容
 *
 * @author daizongheng
 */

@Data
public class BarLabelRotationData {
    /**
     * color用于表示颜色，暂定用list接收数据库color信息
     */
    private List color;

    /**
     * legend暂定使用list接收后台legend信息
     */
    private List legend;

    /**
     * xData用于表示年份，暂定用list接收后台数据库XData信息
     */
    private List xData;

    /**
     * groupby用于表示图表显示的方向
     *
     * 暂定为1
     */
    private Integer groupby = 1;

    /**
     * 用于表示图表内容信息
     */
    private Map seriesData;

    /**
     * 定义无参构造函数，用于初始化对象
     *
     * 为了测试json串，暂时将数据写死
     */
    public BarLabelRotationData(){
        super();

        //初始化颜色列表，暂时写死
        color = new ArrayList();
        color.add("#003366");
        color.add("#0453e6");
        color.add("#0eaf26");
        color.add("#eaf366");

        //初始化legend列表，暂时写死
        legend = new ArrayList();
        legend.add("Forest");
        legend.add("Steppe");
        legend.add("Desert");
        legend.add("Wetland");

        //初始化xData列表，暂时写死
        xData = new ArrayList();
        xData.add("2015");
        xData.add("2016");
        xData.add("2017");
        xData.add("2018");



        //测试将数据放到map中，暂定写死
        List list = new ArrayList();
        list.add(145);
        list.add(352);
        list.add(604);
        list.add(744);
        list.add(78);

        //完成seriesData的初始化，暂时写死
        seriesData = new HashMap();
        if(groupby == 1) {
            for (int i = 0; i < legend.size(); i++) {
                seriesData.put(legend.get(i),list);
            }
        }
    }


}
