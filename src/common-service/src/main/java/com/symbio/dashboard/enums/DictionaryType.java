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
  TestSetStatus("TestSetStatus"),
  TestRunStatus("TestRunStatus"),
  JENKINS_JOB_STATUS("JenkinsJobStatus"),

  // Support locale in the field
  SCREEN_SHOT_STATUS_LOCALE("ScreenShotStatus"),
  TEST_RESULT_STATUS_LOCALE("TestResultStatus"),
  TEST_CASE_TYPE_LOCALE("TestCaseTypeLocale");

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

  public static DictionaryType getDicType(String dicType) {
    DictionaryType retDicType = null;
    for (DictionaryType item : DictionaryType.values()) {
      if(item.getType().equalsIgnoreCase(dicType)) {
        retDicType = item;
        break;
      }
    }

    if(retDicType == null) {
      throw new IllegalArgumentException("No DictionaryType enum element matches [" + dicType + "]");
    }

    return retDicType;
  }
}
