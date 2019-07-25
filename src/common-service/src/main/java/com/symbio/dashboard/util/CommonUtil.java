package com.symbio.dashboard.util;

import java.lang.reflect.Method;

public class CommonUtil {

  public CommonUtil() {
  }

  public static String getCamelField(String dbField) {
    String retField = dbField;
    String[] arrField = dbField.trim().split("_");
    arrField[0] = arrField[0].toLowerCase();
    for (int i = 1; i < arrField.length; i++) {
      arrField[i] = arrField[i].substring(0, 1).toUpperCase() + arrField[i].substring(1);
    }

    retField = String.join("", arrField);
    return retField;
  }

  public static boolean isEmpty(Object obj) {
    if (obj == null) {
      return true;
    }

    boolean retValue = false;
    try {
      String strTypeName = obj.getClass().getSimpleName();
      switch (strTypeName) {
        default:
          System.out.println("Not support Type: " + strTypeName);
          break;
        case "String":
          retValue = String.valueOf(obj).trim().length() == 0;
          break;
        case "Integer":
          retValue = String.valueOf(obj).trim().equals("0");
        case "BigInteger":
        case "Double":
        case "Float":
        case "Long":
        case "Boolean":
          break;
        case "Date":
          break;
        case "HashMap":
        case "ArrayList":
          Method method = obj.getClass().getMethod("isEmpty", new Class[] {});
          retValue = (Boolean)method.invoke(obj, new Object[] {});
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return retValue;
  }

  public static void main(String[] args) {
    System.out.println(CommonUtil.getCamelField("spare_str100"));

    System.out.println(CommonUtil.getCamelField("qa_lead"));
    System.out.println(CommonUtil.getCamelField("prodfld_int1"));
    System.out.println(CommonUtil.getCamelField("create_user_name"));
  }
}
