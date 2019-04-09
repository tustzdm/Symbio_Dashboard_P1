package com.symbio.dashboard.report.dto.QualityViewLeyout;

import lombok.Data;

/**
 * 本类主要根据得到的页面上送信息返回相应的页面布局信息的dto类
 *
 * @author daizongheng
 */

@Data
public class QualityViewLayout {

    /**
     * 返回错误码
     * 0表示正常
     */
    private String ec = "0";

    /**
     * 如果有错误时返回错误信息
     */
    private String em = "";

    /**
     * qv布局信息
     */
    private QualityViewLayoutCD cd = new QualityViewLayoutCD();

}
