package com.symbio.dashboard.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyUtil {

  private static final String SET_PREFIX = "set";
  private static final String IS_PREFIX = "is";
  private static final String GET_PREFIX = "get";

  public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propertyName) {
    Method setMethod = null;
    Method getMethod = null;
    PropertyDescriptor pd = null;
    try {
      Field field = clazz.getDeclaredField(propertyName);
      if (field != null) {
        // 构建方法的后缀
        String methodEnd = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        setMethod = clazz.getDeclaredMethod(SET_PREFIX + methodEnd, new Class[] { field.getType() });
        // 构建get 方法
        getMethod = clazz.getDeclaredMethod(GET_PREFIX + methodEnd, new Class[] {});
        // 构建一个属性描述器 把对应属性 propertyName 的 get 和 set 方法保存到属性描述器中
        pd = new PropertyDescriptor(propertyName, getMethod, setMethod);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return pd;
  }

  /**
   * 使用 PropertyDescriptor 提供的 get和set方法
   * @param clazz
   * @param propertyName
   * @return
   */
  public static PropertyDescriptor getPropertyDescriptorEx(Class<?> clazz, String propertyName) {
    try {
      return new PropertyDescriptor(propertyName, clazz);
    } catch (IntrospectionException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 从属性描述器中获取 set 方法
   * @param obj
   * @param propertyName
   * @param value
   */
  public static void setProperty(Object obj, String propertyName, Object value) {
    Class<?> clazz = obj.getClass();
    propertyName = getFieldString(propertyName);
    PropertyDescriptor pd = getPropertyDescriptor(clazz, propertyName);
    Method setMethod = pd.getWriteMethod();
    try {
      setMethod.invoke(obj, new Object[] { value });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 从属性描述器中获取 get 方法
   * @param obj
   * @param propertyName
   * @return
   */
  public static Object getProperty(Object obj, String propertyName) {
    Class<?> clazz = obj.getClass();
    PropertyDescriptor pd = getPropertyDescriptor(clazz, propertyName);
    Method getMethod = pd.getReadMethod();
    Object value = null;
    try {
      value = getMethod.invoke(clazz, new Object[] {});
    } catch (Exception e) {
      e.printStackTrace();
    }
    return value;
  }

  public static String getFieldString(String string) {
    String temp = "";
    if (string.contains("_")) {
      String[] args = string.split("_");
      String first = args[0];
      for (int i = 0; i < args.length - 1; i++) {
        String check = args[i + 1];
        String letter = check.substring(0, 1).toUpperCase();
        temp += letter + check.substring(1);
      }
      return first + temp;
    }
    return string;
  }
}
