package com.symbio.dashboard.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings("unchecked")
public class EntityUtils {
    private static Logger logger = LoggerFactory.getLogger(EntityUtils.class);

    /**
     * 将数组数据转换为实体类
     * 此处数组元素的顺序必须与实体类构造函数中的属性顺序一致
     *
     * @param list  数组对象集合
     * @param clazz 实体类
     * @param <T>   实体类
     * @param model 实例化的实体类
     * @return 实体类集合
     */
    public static <T> List<T> castEntity(List<Object[]> list, Class<T> clazz, Object model) {
        List<T> returnList = new ArrayList<T>();
        if (list.isEmpty()) {
            return returnList;
        }
        //获取每个数组集合的元素个数
        Object[] co = list.get(0);

        //获取当前实体类的属性名、属性值、属性类别
        List<Map> attributeInfoList = getFieldsInfo(model);
        //创建属性类别数组
        Class[] c2 = new Class[attributeInfoList.size()];
        //如果数组集合元素个数与实体类属性个数不一致则发生错误
        if (attributeInfoList.size() != co.length) {
            return returnList;
        }
        //确定构造方法
        for (int i = 0; i < attributeInfoList.size(); i++) {
            c2[i] = (Class) attributeInfoList.get(i).get("type");
        }
        try {
            for (Object[] o : list) {
                Constructor<T> constructor = clazz.getConstructor(c2);
                returnList.add(constructor.newInstance(o));
            }
        } catch (Exception ex) {
            logger.error("实体数据转化为实体类发生异常：异常信息：{}", ex.getMessage());
            return returnList;
        }
        return returnList;
    }

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName 属性名
     * @param model     实体类
     * @return 属性值
     */
    private static Object getFieldValueByName(String fieldName, Object model) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = model.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(model, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取属性名数组
     */
    private String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            // System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     *
     * @param model 实体类
     * @return list集合
     */
    private static List<Map> getFieldsInfo(Object model) {
        Field[] fields = model.getClass().getDeclaredFields();
        List<Map> list = new ArrayList(fields.length);
        Map infoMap = null;
        for (int i = 0; i < fields.length; i++) {
            infoMap = new HashMap(3);
            infoMap.put("type", fields[i].getType());
            infoMap.put("name", fields[i].getName());
            infoMap.put("value", getFieldValueByName(fields[i].getName(), model));
            list.add(infoMap);
        }
        return list;
    }

    //获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
    private static List<Map> getFieldsInfo(Object model, List<String> dbFields) {
        Field[] fields = getAllFields(model);
        List<Map> list = new ArrayList(fields.length);
        Map infoMap;

        for (int i = 0; i < fields.length; i++) {
            for (String s : dbFields) {
                if (s.contains("_")) {
                    s = s.replace("_", "");
                }
                if (s.equals(fields[i].getName().toLowerCase())) {
                    infoMap = new HashMap(3);
                    infoMap.put("type", fields[i].getType());
                    infoMap.put("name", fields[i].getName());
                    infoMap.put("value", getFieldValueByName(fields[i].getName(), model));
                    list.add(infoMap);
                    break;
                }
            }
        }
        return list;
    }

    private static Field[] getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * 将数组数据转换为实体类
     * 此处数组元素的顺序必须与实体类构造函数中的属性顺序一致
     *
     * @param list  数组对象集合
     * @param <T>   实体类
     * @param clazz 实例化的实体类
     * @param fields 查询字段
     * @return 实体类集合
     */
    public static <T> List<T> castModel(List<Object[]> list, Class<T> clazz, String fields) {
        List<T> returnList = new ArrayList<T>();
        if (list.isEmpty()) {
            return returnList;
        }

        String[] arrFields = fields.split(",");
        if(arrFields == null || arrFields.length == 0) {
            return returnList;
        }

        // 获取每个数组集合的元素个数
        Object[] co = list.get(0);

        // 如果数组集合元素个数与实体类属性个数不一致则发生错误
        if (arrFields.length != co.length) {
            return returnList;
        }

        try {
            for (Object[] item : list) {
                T objModel = clazz.newInstance();
                for (int i = 0; i < item.length; i++) {
                    PropertyUtil.setProperty(objModel, arrFields[i].trim(), item[i]);
                }
                returnList.add(objModel);
            }
        } catch (Exception ex) {
            logger.error("EntityUtils.castJAPModel() Exception!!!", ex.getMessage());
            return returnList;
        }
        return returnList;
    }
}
