package com.symbio.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 本类作为数据库product表的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonListDTO implements Serializable {

  private Integer pageIndex;
  private Integer pageSize;
  private Integer totalRecord;
  private Integer dataType;  // 1: Array[Array], 2: Array[Map]
  private String locale;
  private List columns;
  private List<String> fields;
  private List data;

  public CommonListDTO(String locale, Integer pageIndex, Integer pageSize){
    this.locale = locale;
    this.pageIndex = pageIndex == null ? 0 : pageIndex;
    this.pageSize = pageSize == null ? 20 : pageIndex;
  }

}
