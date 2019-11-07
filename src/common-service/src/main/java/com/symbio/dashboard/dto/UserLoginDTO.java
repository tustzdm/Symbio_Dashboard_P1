package com.symbio.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本类作为 User Login 的接口数据类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginDTO implements Serializable {

    private String locale;
    private String token;
    private List<Map<String, Object>> listMenu;
    private Map<String, Object> userInfo;

    public UserLoginDTO(String locale, String token) {
        this.locale = locale;
        this.token = token;

        this.listMenu = new ArrayList<>();
        this.userInfo = new HashMap<>();
    }
}
