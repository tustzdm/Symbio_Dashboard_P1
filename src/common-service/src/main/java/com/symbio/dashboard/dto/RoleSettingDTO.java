package com.symbio.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 本类作为role setting info的接口数据类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleSettingDTO implements Serializable {

    private String locale;
    private Integer role;
    private Integer totalFunctionValue;

    private List<Map<String, Object>> roleList;
    private List<Map<String, Object>> menuList;
    private List<Map<String, Object>> functionList;
}
