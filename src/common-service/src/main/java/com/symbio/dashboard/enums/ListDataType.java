package com.symbio.dashboard.enums;

public enum ListDataType {
  JSONArray(1),
  Map(2);

  private ListDataType(int dataType){
    this.dataType = dataType;
  }
  private int dataType;

  public int getDataType(){
    return this.dataType;
  }

}
