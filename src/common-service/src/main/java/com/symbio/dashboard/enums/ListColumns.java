package com.symbio.dashboard.enums;

public enum ListColumns {
  KEY("key"),
  LABEL("label"),
  TYPE("type"),
  FIELD("field"),
  ALIGN("align"),
  FORMAT("formatter");

  private ListColumns(String name){
    this.name = name;
  }
  private String name;

  public String getKey(){
    return this.name;
  }

  @Override
  public String toString(){
    return this.name;
  }
}
