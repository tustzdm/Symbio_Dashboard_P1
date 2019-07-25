package com.symbio.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 本类作为数据库product表的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO implements Serializable {

    // Product
//    private Integer id;
//    private String name;
//    private Integer owner;
//    private Integer manager;
//    private Integer qaLead;
//    private Integer devLead;
//    private Integer logoId;
//    private String logoUrl;
//    // 0-normal,1-abnormal,2-completed,3-archived,4-blocked
//    private Integer status;
//    // 0-hide, 1-show
//    private Integer display;
//    private String description;
//    private String locale;
//
//    private Integer prodfldInt1;
//    private Integer prodfldInt2;
//    private Integer prodfldInt3;
//    private Integer prodfldInt4;
//    private Integer prodfldInt5;
//    private Integer prodfldStr1;
//    private Integer prodfldStr2;
//    private Integer prodfldStr3;
//    private Integer prodfldStr4;
//    private Integer prodfldStr5;
//    private Integer prodfldStr6;
//    private Integer prodfldStr7;
//    private Integer prodfldStr8;
//    private Integer prodfldStr9;
//    private Integer prodfldStr10;
//
//    private Date createTime;
//    private Integer createUser;
//    private String createUserName;
////    private Date updateTime;
////    private Integer updateUser;
////    private String updateUserName;
//
////    private Product product;
//
//    // JSON format
//    private String progress;

  private Integer pageIndex;
  private Integer pageSize;
  private Integer totalRecord;
  private String locale;
  private List columns;
  private String fields;
  private List data;

  public ProductDTO(String locale, Integer pageIndex, Integer pageSize){
    this.locale = locale;
    this.pageIndex = pageIndex == null ? 0 : pageIndex;
    this.pageSize = pageSize == null ? 20 : pageIndex;
  }

}
