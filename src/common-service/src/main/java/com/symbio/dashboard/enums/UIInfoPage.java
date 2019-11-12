package com.symbio.dashboard.enums;

public enum UIInfoPage {
  QualityOverviewLayout("QualityOverviewLayout", ""),
  Product("product", "product"),
  Release("release","release"),
  TestSet("testset", "test_set"),
  BugReport("BugReport", "bug_info");

  private UIInfoPage(String page, String table){
    this.page = page;
    this.table = table;
  }

  private String page;
  private String table;

  public String getTableName() {
    return table;
  }

  public String getKey() {
    return page;
  }

  public static boolean hasTable(String tableName) {
    boolean bRet = false;
    for (UIInfoPage item : UIInfoPage.values()) {
      if(item.getTableName().equals(tableName)) {
        bRet = true;
        break;
      }
    }

    return bRet;
  }

  public static String getTableName(String page) {
    String retTableName = "";
    for (UIInfoPage item : UIInfoPage.values()) {
      if(item.getKey().equals(page)) {
        retTableName = item.getTableName();
        break;
      }
    }

    return retTableName;
  }

  public static UIInfoPage getUiInfo(String page) {
    UIInfoPage retUiInfo = null;
    for (UIInfoPage item : UIInfoPage.values()) {
      if(item.toString().equals(page)) {
        retUiInfo = item;
        break;
      }
    }

    if(retUiInfo == null) {
      throw new IllegalArgumentException("No UIInfoPage Enum element matches " + page);
    }

    return retUiInfo;
  }

  @Override
  public String toString() {
    return page;
  }
}
