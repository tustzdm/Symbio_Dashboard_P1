package com.symbio.dashboard.report.service;

import com.symbio.dashboard.report.dto.saveQualityViewLeyout.SaveQualityViewLeyout;
import com.symbio.dashboard.report.repository.SettingLayoutRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 本类实现SaveQualityViewLeyoutService接口，主要做用是将页面信息存放到数据库setting_layout中
 *  然后将存储情况返回给前端
 *
 */

@Service
@Data
public class SaveQualityViewLeyoutServiceImpl implements SaveQualityViewLeyoutService {


    private final SettingLayoutRepository settingLayoutRepository;

    /**
     * 实现接口，将前端页面布局存储到数据库中，然后根据存储结果将反馈信息返回给前端
     *
     * @return 将反馈对象返回给前端
     */
    @Override
    public SaveQualityViewLeyout getSaveFeedback(String token, String locale, List listChartCommon,
                                                 List listChartOther, List listRowChart, List listList) {
        return null;
    }


    /**
     *返回一个存储反馈信息对象
     *
     * @return SaveQualityViewLeyout类型的object
     */
    private SaveQualityViewLeyout setSaveQualityViewLeyout(String token, String locale, List listChartCommon,
                                                           List listChartOther, List listRowChart, List listList){

        SaveQualityViewLeyout saveQualityViewLeyout = new SaveQualityViewLeyout();

        //暂时将返回信息写死
        saveQualityViewLeyout.setEc("0");
        saveQualityViewLeyout.setEm("success");

        return saveQualityViewLeyout;
    }


    /**
     * 本方法用于将信息存储到setting_layout中
     *
     * @return
     */
    private int saveLayout(String token, String locale, List listChartCommon,
                           List listChartOther, List listRowChart, List listList){
        int flag = 0;



        return flag;
    }

}
