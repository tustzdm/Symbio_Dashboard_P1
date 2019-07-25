package com.symbio.dashboard.util;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class WebUtil {

  /**
   * 将数据转成页面显示的字符串.
   * null, 空串都显示为空
   * @param param
   * @return
   */
  public static String getItemValue(Object param) {
    if (param == null) {
      return "";
    }

    String retValue = "";
    try {
      String strTypeName = param.getClass().getSimpleName();
      switch (strTypeName) {
        default:
          print(OutputErrorMsg("Not support Type: " + strTypeName));
          break;
        case "String":
        case "Integer":
        case "BigInteger":
        case "Double":
        case "Float":
        case "Long":
        case "Boolean":
          retValue = String.valueOf(param);
          break;
        case "Date":
          SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          try {
            retValue = String.valueOf(df.format(param));
          } catch(Exception edf) {
          }
          break;
        case "HashMap":
        case "ArrayList":
          Method method = param.getClass().getMethod("isEmpty", new Class[] {});
          Boolean bEmpty = (Boolean)method.invoke(param, new Object[] {});
          if (bEmpty) {
            retValue = "";
          } else {
            retValue = "("+ strTypeName +")";
          }
          break;
      }
      print(OutputDebugMsg("retValue = [" + retValue) + "]");
    } catch (Exception e) {
      e.printStackTrace();
      print(OutputWarningMsg(e.getMessage()));
    }
    return retValue;
  }

  /**
   * 将Map内对象显示
   * @param map
   * @param key
   * @return
   */
  public static String getMapItemValue(Map map, String key) {
    if (map == null || map.isEmpty()) {
      return "";
    } else {
      return getItemValue(map.get(key));
    }
  }

  /**
   *
   * @param inputData
   * @return
   */
  public static String deCodeQueryString(String inputData) {
    try {
      return URLDecoder.decode(inputData, "utf-8"); //将中文转码
    } catch (Exception e) {
      return "";
    }
  }

  public static String OutputWarningMsg(String msg) {
    return String.format("!!!WARNING!!! %s", msg);
  }

  public static String OutputErrorMsg(String msg) {
    return String.format("!!!ERROR!!! %s", msg);
  }

  public static String OutputDebugMsg(String msg) {
    return String.format(">>>>>> %s", msg);
  }

  public static void print(Object msg) {
    //System.out.println(msg);
  }

  public static void main(String[] args) {
    WebUtil test = new WebUtil();
    Map<String, Object> a = new HashMap<String, Object>();
    a.put("a", 1);
    a.put("b", null);
    test.getItemValue(a);
    List obj2 = new ArrayList();
    test.getItemValue(obj2);

    BigInteger bi = new BigInteger("10");
    test.getItemValue(bi);

    Date dTest = new Date();
    test.getItemValue(dTest);

    Map<String, Object> c = new HashMap<String, Object>();
    test.getItemValue(c);

    test.getMapItemValue(a,"a");
    return ;
  }
}
