package com.symbio.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 本类作为 TestRun UI info的接口数据类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRunUiDTO implements Serializable {

  private String locale;
  private Integer role;

  // page info
  private Integer pageIndex;
  private Integer pageSize;
  private Integer totalRecord;
  private Integer dataType;  // 1: Array[Array], 2: Array[Map]

  private List columns;
  private List<String> fields;
  private List data;

  // Current selected item
  private Integer productId;
  private Integer releaseId;
  private Integer testSetId;
  private List<Map<String, Object>> productList;
  private List<Map<String, Object>> releaseList;
  private List<Map<String, Object>> testSetList;
}
