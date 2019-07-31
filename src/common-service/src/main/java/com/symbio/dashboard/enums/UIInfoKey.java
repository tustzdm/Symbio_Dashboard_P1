package com.symbio.dashboard.enums;

public enum UIInfoKey {
  Key("key", "key", ""),
  DBField("dbField", "dbField", ""),
  Type("type", "type", ""),
  Data("data", "data", ""),
  DisplayStatus("dispStatus", "DispStatus", ""),
  Required("isRequired", "IsRequired", ""),
  Disable("isDisable", "isDisable", ""),
  Label("label", "EnUs|zhCn", ""),
  PlaceHolder("placeHolder", "placeHolder", ""),
  DefaultValue("defaultValue", "defaultValue", ""),
  ConstCondition("constCondition", "constCondition", ""),
  Order("idx", "idx", "");

  private UIInfoKey(String key, String methodName, String args){
    this.key = key;
    this.methodName = methodName;
    this.args = args;
  }

  private String key;
  private String methodName;
  private String args;

  public String getKey() {
    return key;
  }
  public String getMethodName() {
    return methodName;
  }
  public String getArgs() {
    return args;
  }

  @Override
  public String toString() {
    return key;
  }
}
