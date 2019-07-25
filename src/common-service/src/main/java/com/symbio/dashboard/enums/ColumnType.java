package com.symbio.dashboard.enums;

/**
 * 必须与 Dictionary.type = ColumnType 对应
 */
public enum ColumnType {

  Hidden(200, "hidden", "Hidden"),
  Text(201, "text" ,"Text"),
  Number(202, "number" ,"Number"),
  Link(203, "link" ,"Link"),
  Button(204, "button" ,"Button"),
  Radio(205, "radio" ,"Radio"),
  CheckBox(206, "checkbox" ,"CheckBox"),
  Percentage(207, "percentage" ,"Percentage"),
  Formatter(208, "formatter" ,"Formatter"),
  UiLink(209, "uilink" ,"UiLink"),
  AutoStatus(210, "autoStatus" ,"Auto Status"),
  QAStatus(211, "qaStatus" ,"QA Status"),
  ProductStatus(212, "productStatus" ,"Product Status"),
  ReleaseStatus(213, "releaseStatus" ,"Release Status"),
  TestsetStatus(214, "testsetStatus" ,"TestSet Status"),
  DateTime(215, "datetime" ,"DateTime"),
  DateOnly(216, "date" ,"Date"),
  User(217, "user" ,"User");

  private ColumnType(int id, String code, String colType){
    this.id = id;
    this.code = code;
    this.colType = colType;
  }
  private int id;
  private String code;
  private String colType;

  public int getId(){
    return this.id;
  }

  public String getCode(){
    return this.code;
  }

  public String getColumnType(){
    return this.colType;
  }
}
