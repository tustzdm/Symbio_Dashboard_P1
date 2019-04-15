package com.symbio.dashboard.report.service;

import com.alibaba.fastjson.JSONObject;

import com.symbio.dashboard.report.dto.qualityViewLeyout.QualityViewLayout;
import com.symbio.dashboard.report.dto.qualityViewLeyout.QualityViewLayoutCD;
import com.symbio.dashboard.report.dto.qualityViewLeyout.QualityViewLayoutInformation;
import com.symbio.dashboard.report.modle.ReportChart;
import com.symbio.dashboard.report.repository.ReportChartRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 本类用于实现QualityViewLayoutService接口，将layout页面布局发送给前端
 *
 */

@Service
@Data
public class QualityViewLayoutServiceImpl implements QualityViewLayoutService {

    private final ReportChartRepository reportChartRepository;


    @Override
    public QualityViewLayout getQualityViewLayout(String locale) {
        return setQualityViewLayout(locale);
    }


    /**
     * 用于创建一个QualityViewLayout对象并返回
     *
     * @param locale 语种 language
     *
     * @return 返回一个创建好的QualityViewLayout对象
     */
    private QualityViewLayout setQualityViewLayout(String locale){
        QualityViewLayout qualityViewLayout = new QualityViewLayout();

        //暂时没有错误的返回信息
        qualityViewLayout.setEc("0");
        qualityViewLayout.setEm("成功");

        qualityViewLayout.setCd(setQualityViewLayoutCD(locale));
        return qualityViewLayout;
    }


    /**
     * 用于创建QualityViewLayout类中的cd对象并返回
     *
     * @param locale 语种
     *
     * @return 返回一个cd对象
     */
    private QualityViewLayoutCD setQualityViewLayoutCD(String locale){
        QualityViewLayoutCD qualityViewLayoutCD = new QualityViewLayoutCD();

        qualityViewLayoutCD.setLocale(locale);
        //菜单权限暂定写死
        qualityViewLayoutCD.setRole(7);

        //后面的list暂定写死
        qualityViewLayoutCD.setListSupport(setListSupport(locale));



        return qualityViewLayoutCD;
    }


    /**
     * 方法用于建立listSupport集合
     *
     * @param locale 语种
     *
     * @return 返回一个ListSupport列表，最终用来返回给前台
     */
    private List<QualityViewLayoutInformation> setListSupport(String locale){
        //获得实体类list
        List<ReportChart> list = reportChartRepository.findAll();
        //最终要生成的列表
        List<QualityViewLayoutInformation> lastList = new ArrayList<QualityViewLayoutInformation>();

        for(ReportChart reportChart : list){
            QualityViewLayoutInformation qualityViewLayoutInformation = new QualityViewLayoutInformation();
            //这几个是从数据库中能直接读取的
            qualityViewLayoutInformation.setIdx(reportChart.getIdx());
            qualityViewLayoutInformation.setKey(reportChart.getKey());
            qualityViewLayoutInformation.setType(reportChart.getType());
            //做一些key的判断，判断是否需要添加pos
            //TODO

            //根据locale获得相应的name值
            qualityViewLayoutInformation.setName(getNameByLocale(locale,reportChart.getName()));

            lastList.add(qualityViewLayoutInformation);

        }


        return lastList;
    }


    /**
     * 从数据库获得的json串，根据语种返回相应的name
     * @param locale 语种
     * @param name 从数据库中获得的json串
     * @return 返回一个根据语种的name值
     */
    private String getNameByLocale(String locale,String name){
        JSONObject jsonObject = JSONObject.parseObject(name);
        Map<String, String> map = (Map<String, String>) JSONObject.toJSON(jsonObject);
        return map.get(locale);
    }



}
