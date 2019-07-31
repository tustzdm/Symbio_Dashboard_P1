package com.symbio.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 本类作为Release UI info的接口数据类
 * @ClassName - ReleaseUiDTO
 * @Author - admin
 * @Description - TODO
 * @Date - 2019/7/30 14:29
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReleaseUiDTO implements Serializable {

    private String locale;
    private Integer role;
    private List<Map<String, Object>> uiInfo;
    private Map<String, Object> data;
    private List productName;
    private List<Map<String, Object>> statusList;

}