package com.symbio.dashboard.enums;

public enum DictionaryType {
  TemplateCategory("TemplateCategory"),
  DataType("DataType"),
  HtmlType("HtmlType"),
  UI_List_Count("UI_List_Count"),
  ProductStatus("ProductStatus"),
  ReleaseStatus("ReleaseStatus"),
  Page_Element_Setting("Page_Element_Setting"),
  SYS_LIST_SETTING("sys_list_setting"),
  ColumnType("ColumnType"),
  TestSetType("TestSetType"),
  TestSetStatus("TestSetStatus");

  private DictionaryType(String type){
    this.type = type;
  }
  private String type;

  public String getType() {
    return this.type;
  }

  @Override
  public String toString() {
    return type;
  }
}
