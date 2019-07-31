package com.symbio.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 本类作为 TestSet UI info的接口数据类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestSetUiDTO implements Serializable {

  private String locale;
  private Integer role;
  private List<Map<String, Object>> uiInfo;
  private Map<String, Object> data;

  private List<Map<String, Object>> productList;
  private List<Map<String, Object>> releaseList;
  private List<Map<String, Object>> userList;
}
