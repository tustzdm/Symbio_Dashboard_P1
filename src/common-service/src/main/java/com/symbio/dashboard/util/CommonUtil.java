package com.symbio.dashboard.util;

import java.lang.reflect.Method;
import java.util.*;

public class CommonUtil {

  public CommonUtil() {
  }

  public static String getCamelField(String dbField) {
    String retField = dbField;
    String[] arrField = dbField.trim().split("_");
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

  public static String[] concat(String[] a, String[] b) {
    String[] c = new String[a.length + b.length];
    System.arraycopy(a, 0, c, 0, a.length);
    System.arraycopy(b, 0, c, a.length, b.length);
    return c;
  }


  public static void main(String[] args) {
    System.out.println(CommonUtil.getCamelField("spare_str100"));

    System.out.println(CommonUtil.getCamelField("qa_lead"));
    System.out.println(CommonUtil.getCamelField("prodfld_int1"));
    System.out.println(CommonUtil.getCamelField("create_user_name"));

    List<Integer> data = new ArrayList<>();
    data.add(1);
    data.add(2);
    System.out.println(join(data));
  }

  public static List<String> getListByMergeString(String entityFields, String statFields) {
    List<String> listFields = new ArrayList<String>();

    try {
      String[] arrEntityFields = entityFields.split(",");
      String[] arrStatFields = statFields.split(",");
      listFields = Arrays.asList(CommonUtil.concat(arrEntityFields, arrStatFields));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listFields;
  }

  public static void validateMapKey(Map<String, Object> map, List<String> listFields) throws Exception {

    Iterator iterMap = null;
    for (String field : listFields) {
      boolean bFindKey = false;

      iterMap = map.keySet().iterator();
      String mapKey = null;
      while (iterMap.hasNext()) {
        mapKey = (String) iterMap.next();
        if (field.equals(mapKey)) {
          bFindKey = true;
          break;
        }
      }

      if (!bFindKey) {
        throw new Exception(String.format("Could not find key[%s] in the Map.", field));
      }
    }
  }

  public static String join(List<Integer> data) {
    StringBuffer sb = new StringBuffer();
    for(Integer item : data) {
      sb.append(item).append(",");
    }

    String retData = sb.toString();
    if(!isEmpty(retData)) {
      retData = retData.substring(0,retData.length() -1);
    }

    return retData;
  }

}
