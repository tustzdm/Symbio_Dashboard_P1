package com.symbio.dashboard.enums;

public enum SystemListSetting {
  Product("product"),
  Release("release"),
  TestSet("testset"),
  TestCase("testcase"),
  ResultReview("resultreview"),
  ImageCompare("ImageComp"),
  BugReport("BugReport"),
  BugList("BugList");

  private SystemListSetting(String page){
    this.page = page;
  }
  private String page;

  @Override
  public String toString() {
    return page;
  }

  public static void main(String[] args) {
    System.out.println(SystemListSetting.Product.ordinal());  //  0
    System.out.println(SystemListSetting.Release.ordinal());  //  1
    System.out.println(SystemListSetting.values()[0]);      //  en_US
    System.out.println(SystemListSetting.values()[1]);      //  zh_CN

    System.out.println(SystemListSetting.Product);  //  en_US
    System.out.println(SystemListSetting.Release);  //  zh_CN
  }
}
