package com.symbio.dashboard.util;

import com.symbio.dashboard.model.UiInfo;
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

    // Not Test
    @Deprecated
    public static <T> List<T> castOneFieldEntity(List<Object> list, Class<T> clazz, Object model) {
        List<T> returnList = new ArrayList<T>();
        if (list.isEmpty()) {
            return returnList;
        }
        //获取每个数组集合的元素个数
        Object co = list.get(0);

        //获取当前实体类的属性名、属性值、属性类别
        List<Map> attributeInfoList = getFieldsInfo(model);
        //创建属性类别数组
        Class[] c2 = new Class[attributeInfoList.size()];
        //如果数组集合元素个数与实体类属性个数不一致则发生错误
//        if (attributeInfoList.size() != co.length) {
//            return returnList;
//        }
        //确定构造方法
        for (int i = 0; i < attributeInfoList.size(); i++) {
            c2[i] = (Class) attributeInfoList.get(i).get("type");
        }
        try {
            for (Object o : list) {
                Constructor<T> constructor = clazz.getConstructor(c2);
                returnList.add(constructor.newInstance(o));
            }
        } catch (Exception ex) {
            logger.error("castOneFieldEntity() 实体数据转化为实体类发生异常：异常信息：{}", ex.getMessage());
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
    public static Object getFieldValueByName(String fieldName, Object model) {
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

        //获取当前实体类的属性名、属性值、属性类别


        try {
            List<Map> attributeInfoList = getFieldsInfo(clazz.newInstance());
            arrFields = getFieldMethodNames(arrFields, attributeInfoList);

            String fieldName = null;
            for (Object[] item : list) {
                T objModel = clazz.newInstance();
                for (int i = 0; i < item.length; i++) {
                    fieldName = arrFields[i].trim().replace("_","");
                    PropertyUtil.setProperty(objModel, fieldName, item[i]);
                }
                returnList.add(objModel);
            }
        } catch (Exception ex) {
            logger.error("EntityUtils.castJAPModel() Exception!!!", ex.getMessage());
            return returnList;
        }
        return returnList;
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
    public static <T> List<Map<String, Object>> castMap(List<Object[]> list, Class<T> clazz, String fields) {
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
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

        //获取当前实体类的属性名、属性值、属性类别
        try {
            List<Map> attributeInfoList = getFieldsInfo(clazz.newInstance());
            arrFields = getFieldMethodNames(arrFields, attributeInfoList);

            String fieldName = null;
            Map<String, Object> mapData = null;
            Object objData = null;
            String strCamelField = null;
            for (Object[] item : list) {
                //T objModel = clazz.newInstance();
                //for (int i = 0; i < item.length; i++) {
                //    fieldName = arrFields[i].trim().replace("_","");
                //    PropertyUtil.setProperty(objModel, fieldName, item[i]);
                //}
                mapData = new HashMap<String, Object>();
                for (int i = 0; i < item.length; i++) {
                    fieldName = arrFields[i].trim().replace("_","");
                    strCamelField = CommonUtil.getCamelField(fieldName);
                    //objData = PropertyUtil.getProperty(item[i], fieldName);
                    mapData.put(strCamelField, item[i]);
                }
                returnList.add(mapData);
            }
        } catch (Exception ex) {
            logger.error("EntityUtils.castJAPModel() Exception!!!", ex.getMessage());
            return returnList;
        }
        return returnList;
    }


    /**
     * 将对象按 uiInfo 组成Map对象
     *
     * @param <T>        实体类
     * @param entityObj  实例化的实体类
     * @param listUiInfo UiInfo列表信息
     * @return 实体类集合
     */
    public static <T> Map<String, Object> castMap(T entityObj, List<UiInfo> listUiInfo) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        if (CommonUtil.isEmpty(listUiInfo)) {
            return retMap;
        }

        //获取当前实体类的属性名、属性值、属性类别
        try {
            List<Map> attributeInfoList = getFieldsInfo(entityObj);
            List<String> arrFields = new ArrayList<>();
            for (UiInfo uiInfo : listUiInfo) {
                if(!StringUtil.isEmpty(uiInfo.getDbField())) {
                    arrFields.add(uiInfo.getDbField());
                }
            }
            arrFields.add("id"); // fix column

            arrFields = getFieldListMethodNames(arrFields, attributeInfoList);
            for (String methodName : arrFields) {
                retMap.put(methodName, getFieldValueByName(methodName, entityObj));
            }
        } catch (Exception ex) {
            logger.error("EntityUtils.castMap() Exception!!!", ex.getMessage());
            return retMap;
        }
        return retMap;
    }

    public static List<String> getDTOFields(List<String> data) {
        List<String> retList = new ArrayList<>();
        for (String field : data) {
            retList.add(getMapFieldKey(field));
        }
        return retList;
    }

    public static String getMapFieldKey(String strDbField) {
        String key = strDbField;

        if (strDbField.contains(".")) {
            String[] arrFields = strDbField.split("\\.");
            if (arrFields.length == 2) {
                key = CommonUtil.getCamelField(arrFields[1].trim());
            }
        } else {
            key = CommonUtil.getCamelField(strDbField.trim());
        }

        return key;
    }

    private static List<String> getMapKeys(String[] arrFields) {
        List<String> retList = new ArrayList<>();
        for (int i = 0; i < arrFields.length; i++) {
            retList.add(getMapFieldKey(arrFields[i]));
        }
        return retList;
    }

    public static List<String> getCamelFieldList(String entityFields) {
        return getMapKeys(entityFields.split(","));
    }

    /**
     * 直接将字段转成Map 对象
     *
     * @param list
     * @param fields
     * @return
     */
    public static List<Map<String, Object>> castQuerytoMap(List<Object[]> list, String fields) {
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        if (list.isEmpty() || StringUtil.isEmpty(fields)) {
            return returnList;
        }

        try {
            String[] arrFields = fields.split(",");
            List<String> listKeys = getMapKeys(arrFields);

            // 如果数组集合元素个数与实体类属性个数不一致则发生错误
            if (listKeys.size() != 1 && list.get(0).length != listKeys.size()) {
                return returnList;
            }

            Map<String, Object> mapData;
            if (listKeys.size() > 1) {
                for (Object[] item : list) {
                    mapData = new HashMap<String, Object>();
                    for (int i = 0; i < listKeys.size(); i++) {
                        mapData.put(listKeys.get(i), WebUtil.getItemValue(item[i]));
                    }
                    returnList.add(mapData);
                }
            } else {
                for (Object item : list) {
                    mapData = new HashMap<String, Object>();
                    for (int i = 0; i < listKeys.size(); i++) {
                        mapData.put(listKeys.get(i), WebUtil.getItemValue(item));
                    }
                    returnList.add(mapData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;
    }

    /**
     * Check sql field and its method name
     * @param fields
     * @param map
     * @return
     */
    private static String[] getFieldMethodNames(String[] fields, List<Map> map) throws Exception{
        String[] retData = fields;

        String strField = null;
        for(int i =0;i<fields.length;i++) {
            strField = convertFieldToMethName(fields[i],map);
            if (strField == null) {
                String msg = "Could not find exact method name. field = " + fields[i];
                System.out.println(msg);
                logger.error(msg);
                throw new Exception("EntityUtils !!!ERROR!!!" + msg);
            }
            retData[i] = strField;
        }
        return retData;
    }

    private static List<String> getFieldListMethodNames(List<String> fields, List<Map> map) throws Exception{
        List<String> retData = new ArrayList<>();

        String strField = null;
        for(String field : fields) {
            strField = convertFieldToMethName(field, map);
            if (strField == null) {
                String msg = "Could not find exact method name. field = " + field;
                System.out.println(msg);
                logger.error(msg);
                throw new Exception("EntityUtils !!!ERROR!!!" + msg);
            }
            retData.add(strField);
        }
        return retData;
    }

    private static String convertFieldToMethName(String field, List<Map> mapAttr) {
        if(field == null || field.length() == 0) {
            return null;
        }

        String methodName = null;
        String testField = field.trim().replace("_","");
        String fieldName = null;
        for (Map item : mapAttr) {
            fieldName = item.get("name").toString();
            if(testField.equalsIgnoreCase(fieldName)){
                methodName = fieldName;
                break;
            }
        }

        return methodName;
    }

    // Class clone
    public static void mergeObjectBase(Object dest, Object source) {
        Field[] fields = dest.getClass().getDeclaredFields();
        String fieldName = null;

        for (Field field : fields) {
            fieldName = field.getName();

            Object fieldValue = getFieldValueByName(fieldName, source);
            if (!CommonUtil.isEmpty(fieldValue)) {
                try {
                    String setter = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method method =
                            dest.getClass().getDeclaredMethod(setter, new Class[]{field.getType()});
                    method.invoke(dest, new Object[]{fieldValue});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void clonePropertiesByFieldSpecified(Object dest, Object source, List<String> listFields) {
        Field[] fields = dest.getClass().getDeclaredFields();
        String fieldName = null;

        for (Field field : fields) {
            fieldName = field.getName();

            if (listFields.contains(fieldName)) {
                Object fieldValue = getFieldValueByName(fieldName, source);
                if (!CommonUtil.isEmpty(fieldValue)) {
                    try {
                        String setter =
                                "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Method method =
                                dest.getClass().getDeclaredMethod(setter, new Class[]{field.getType()});
                        method.invoke(dest, new Object[]{fieldValue});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Object to Map
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

        // TODO: Field[] declaredFields =
        /*
            System.out.println(obj.getClass());
            obj.getClass().getDeclaredFields();
         */

        String fieldName = null;
        for (Field field : declaredFields) {
            fieldName = field.getName();
            map.put(fieldName, WebUtil.getItemValue(getFieldValueByName(fieldName, obj)));
        }
        return map;
    }
}
