package com.symbio.dashboard.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @Description: 处理Json的工具类
 * @Date: Created in 2019-07-26
 * @Modified By:
 */

@SuppressWarnings("unchecked")
public class JSONUtil {

  /**
   * 将JSONArray数据类型转换为List数据类型
   *
   * @param array
   * @return
   */
  public static List toList(JSONArray array) {
    List list = new ArrayList();
    Object value;
    for (int i = 0; i < array.length(); i++) {
      try {
        value = array.get(i);
        if (value instanceof JSONArray) {
          value = toList((JSONArray) value);
        } else if (value instanceof JSONObject) {
          value = toMap((JSONObject) value);
        }
        list.add(value);
      } catch (JSONException e) {
        System.out.println(e.getMessage());
      }
    }
    return list;
  }

  /**
   * 将JSONObject数据类型转换为Map数据类型
   *
   * @param object
   * @return
   */
  public static Map<String, Object> toMap(JSONObject object) {
    Map<String, Object> map = new HashMap<String, Object>();
    Object value;
    String key;
    for (Iterator<?> it = object.keys(); it.hasNext(); ) {
      key = (String) it.next();
      if (object.isNull(key)) {
        map.put(key, null);
      } else {
        try {
          value = object.get(key);
          if (value instanceof JSONArray) {
            value = toList((JSONArray) value);
          } else if (value instanceof JSONObject) {
            value = toMap((JSONObject) value);
          }
          map.put(key, value);
        } catch (JSONException e) {
          System.out.println(e.getMessage());
        }
      }
    }
    return map;
  }

  /**
   * 根据Map生成String串
   *
   * @param map
   * @return
   */
  public static String mapToString(Map map) {
    try {
      JSONObject orgJson = new JSONObject(map);
      return orgJson.toString();

      /**
       * net.sf.json.JSONObject sfJson = null;
       * sfJson = JSONObject.fromObject(map);
       * System.out.println("net.sf.json sfJson = " + sfJson.toString());
       */
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  /**
   * Map 转 Class
   *
   * @param map
   * @param beanClass
   * @return
   * @throws Exception
   */
  public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
    if (map == null)
      return null;

    Object obj = beanClass.newInstance();

    Field[] fields = obj.getClass().getDeclaredFields();
    for (Field field : fields) {
      int mod = field.getModifiers();
      if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
        continue;
      }

      field.setAccessible(true);
      field.set(obj, map.get(field.getName()));
    }

    return obj;
  }

  /**
   * Class 转 Map
   *
   * @param obj
   * @return
   * @throws Exception
   */
  public static Map<String, Object> objectToMap(Object obj) throws Exception {
    if (obj == null) {
      return null;
    }

    Map<String, Object> map = new HashMap<String, Object>();

    Field[] declaredFields = obj.getClass().getDeclaredFields();
    for (Field field : declaredFields) {
      field.setAccessible(true);
      map.put(field.getName(), field.get(obj));
    }

    return map;
  }

  public static JSONArray strToJSONArray(String text, String splitSymbol) {
    JSONArray retJSONArray = new JSONArray();

    if (text == null || text.length() == 0) {
      return retJSONArray;
    }

    try {
      String[] arrContent = text.split(splitSymbol);
      for (String item : arrContent) {
        retJSONArray.put(item);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return retJSONArray;
  }

}
