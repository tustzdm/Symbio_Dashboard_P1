package com.symbio.dashboard.report.dto.QualityOverview.listCharts.stackedLineChart;


import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 本类为返回给前端的StackedLineChart对象的具体数据内容
 *
 * @author daizongheng
 */

@Data
public class StackedLineChartData {
    /**
     * legend用于表示图例，暂定用list接收后台数据库的legend信息
     *
     * 泛型未定义，暂不知道后台存储形式
     */
    private List legend;

    /**
     * name用于表示List内容给前台，暂定用list接收后台数据库name信息
     *
     * 泛型未定义，暂不知道后台存储形式
     */
    private List name;

    /**
     * groupby用于表示图表显示的方向（横纵轴的变换）
     *
     * 暂定的值只能为0或者1，需要处理
     */
    private Integer groupby;



    /**
     * data是内容对象中的表信息的数据具体内容
     */
//    private FormDataInformation data;



    /**
     * key为表格属性，value为该属性下的具体内容
     *
     * 暂定初始化一个空的hashMap
     */
    private Map data = new HashMap();


    /**
     * 此构造方法用于初始化对象内容
     *
     * 为了测试json串暂定将返回的数据写死
     */
    public StackedLineChartData(){
        super();

        //legend具体内容以后从数据库中获取
        legend = new ArrayList();
        legend.add("Pass");
        legend.add("Fail");
        legend.add("Skip");
        legend.add("Success");

        //name具体内容以后从数据库中获取
        name = new ArrayList();
        name.add("TestSet1");
        name.add("TestSet2");
        name.add("TestSet3");

        //groupby以后从前端获取到
        groupby = 1;


        List list = new ArrayList();
        list.add(1);
        list.add(3);
        list.add(6);
        list.add(7);
        if(groupby == 0){
            for (int i = 0; i < name.size(); i++) {
                data.put(name.get(i),list);
            }
        }else if (groupby == 1){
            for (int i = 0; i < legend.size(); i++) {
                data.put(legend.get(i),list);
            }
        }

    }


}
