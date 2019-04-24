package com.symbio.dashboard.report.dto.saveQualityViewLeyout;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 本类主要用于在保存页面信息之后，将相应的信息反馈给前台
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveQualityViewLeyout {

    /**
     * 错误码
     */
    private String ec;

    /**
     * 错误信息
     */
    private String em;
}
