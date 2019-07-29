package com.symbio.dashboard.enums;

// UiInfo.type
public enum HtmlType {
  Text("text", "Text"),
  Int("int", "Number"),
  Label("label", "Label"),
  Bool("bool", "Option"),
  CheckBox("checkbox", "CheckBox"),
  TextArea("textarea", "TextArea"),
  SelectList("list", "SelectList"),
  DropdownList("dropdownlist", "Dropdown List"),
  Calendar("calendar", "Calendar"),
  Picture("picture", "Picture"),
  User("user", "User"),
  UserList("userlist", "UserList"),
  Product("product", "Product"),
  ProductList("productlist", "ProductList"),
  Release("release", "Release"),
  ReleaseList("releaselist", "ReleaseList"),
  TestSet("testset", "TestSet"),
  TestSetList("", "TestSetList"),
  Search("search", "Search"),
  Link("link", "Link");

  private HtmlType(String code, String value){
    this.code = code;
    this.value = value;
  }

  private String code;
  private String value;

  public String getCode() {
    return this.code;
  }

  public String getValue() {
    return this.value;
  }
}
