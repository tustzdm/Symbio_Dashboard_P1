package com.symbio.dashboard.enums;

public enum Locales {
  EN_US("en_US"),
  ZH_CN("zh_CN");

  private Locales(String locale){   //   必须是private的，否则编译错误
    this.locale = locale;
  }
  private String locale;

  public String getKey() {
    return this.locale.toLowerCase();
  }

  @Override
  public String toString() {
    return locale;
  }

  public static void main(String[] args) {
    System.out.println(Locales.EN_US.ordinal());  //  0
    System.out.println(Locales.ZH_CN.ordinal());  //  1
    System.out.println(Locales.values()[0]);      //  en_US
    System.out.println(Locales.values()[1]);      //  zh_CN

    System.out.println(Locales.EN_US);  //  en_US
    System.out.println(Locales.ZH_CN);  //  zh_CN
  }
}
