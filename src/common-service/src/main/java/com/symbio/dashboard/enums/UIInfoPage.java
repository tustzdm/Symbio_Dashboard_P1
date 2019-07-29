package com.symbio.dashboard.enums;

public enum UIInfoPage {
  QualityOverviewLayout("QualityOverviewLayout"),
  Product("product"),
  Release("release"),
  TestSet("testset");

  private UIInfoPage(String page){
    this.page = page;
  }
  private String page;

  @Override
  public String toString() {
    return page;
  }
}
