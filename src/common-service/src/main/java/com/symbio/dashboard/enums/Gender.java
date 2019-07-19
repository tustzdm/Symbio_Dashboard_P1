package com.symbio.dashboard.enums;

import java.util.HashMap;

public enum Gender {
  MALE("Man", "男", 1),
  FAMALE("Woman","女", 2),
  SECRET("Unkown","未知", 3);

  private Gender(String enUs, String zhCn, Integer value){
    this.key = enUs;
    this.chinese = zhCn;
    this.value = value;
  }
  private String key;
  private String chinese;
  private Integer value;

  //将数值1,2,3和MALE,FAMALE,SECRET一起封装到HashMap中
  private static HashMap<Integer, Gender> valueMap = new HashMap<Integer, Gender>();
  //静态代码块
  static{
    for (Gender item : Gender.values()) {
      valueMap.put(item.getValue(), item);
    }
  }
  public String getKey() {
    return key;
  }
  public void setKey(String key) {
    this.key = key;
  }
  public String getLabelByLocal(String locale) {
    return locale == "en_US" ? key : chinese;
  }

  public Integer getValue() {
    return value;
  }
  public void setValue(Integer value) {
    this.value = value;
  }
  //前台传进来的值通过这个方法来转换为Gender类型
  public static Gender getByValue(int value) {
    Gender result = valueMap.get(value);
    if(result == null) {
      throw new IllegalArgumentException("No element matches " + value);
    }
    return result;
  }
}
