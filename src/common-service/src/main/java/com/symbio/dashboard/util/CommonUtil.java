package com.symbio.dashboard.util;

import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.enums.*;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
public class CommonUtil {

    public CommonUtil() {
    }

    public static String getMapKey(Map<String, Object> data, String key) {
        return getMapKey(data, key, "");
    }

    public static String getMapKey(Map<String, Object> data, String key, String defaultValue) {
        String strData = defaultValue;
        try {
            if (data != null) {
                strData = data.get(key).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strData;
    }

    public static String getCamelField(String dbField) {
        String retField = decodeKeyFileName(dbField);
        String[] arrField = retField.trim().split("_");
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
                    Method method = obj.getClass().getMethod("isEmpty", new Class[]{});
                    retValue = (Boolean) method.invoke(obj, new Object[]{});
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retValue;
    }

    public static boolean isTRUEStr(String data) {
        boolean ret = false;

        if (data == null) return false;
        String chkData = data.toLowerCase().trim();

        // Only 1 or 'true'
        if (EnumDef.ENTITY_BOOL.YES.getCode().toString().equals(chkData)
                || EnumDef.ENTITY_BOOL.YES.getValue().equals(chkData)) {
            ret = true;
        }

        return ret;
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

        String test = CommonUtil.getPageChartTitle(OpsPage.PRODUCT, ChartsType.PIE_REFER, "zh_CN");
        System.out.println(test);
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

    public static List<String> getListByMergeString(String entityFields) {
        List<String> listFields = new ArrayList<String>();

        try {
            String[] arrEntityFields = entityFields.split(",");
            listFields = Arrays.asList(arrEntityFields);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listFields;
    }

    public static String checkKeyFieldName(String name) {
        String retFieldName = name;

        if ("key,type,release,search,data,name".contains(name)) {//status,
            retFieldName = String.format("`%s`", name);
        }

        return retFieldName;
    }

    public static String decodeKeyFileName(String name) {
        String retFieldName = name;

        if (!isEmpty(name) && name.contains("`")) {
            retFieldName = name.replace("`", "");
        }

        return retFieldName;
    }

    public static String getAliasTableName(SystemListSetting listType, String table) {
        String retFieldName = table;

        switch (listType) {
            default:
                break;
            case ResultReview:
                retFieldName = table.replace("test_case", "tc")
                        .replace("jenkins", "jjhm");
                break;
            case BugList: // Equals to EnumDef.DASHBOARD_PAGE.BUGS_OVERVIEW
                retFieldName = table.replace("test_set", "ts")
                        .replace("product", "prod")
                        .replace("release", "rel");
                break;
        }

        return retFieldName;
    }

    private static String getListFieldName(SystemListSetting listType, String entityField) {
        String retFieldName = entityField;
        String prefix = "";

        switch (listType) {
            default:
                break;
            case ImageCompare:
                if (entityField.contains(".")) {
                    String[] arrField = entityField.split("\\.");
                    if (arrField.length == 2) {
                        retFieldName = arrField[1];
                    }
                } else if ("thumbnailHttpPath,httpFilePath,id,".contains(entityField + ",")) {
                    retFieldName = "";
                }
                break;
            case BugList: // Equals to EnumDef.DASHBOARD_PAGE.BUGS_OVERVIEW
                prefix = "bug.";
                if (entityField.contains(".")) {
                    String[] arrField = entityField.split("\\.");
                    if (arrField.length == 2) {
                        if ("test_set".equalsIgnoreCase(arrField[0])) {
                            retFieldName = "ts." + arrField[1];
                        } else {
                            retFieldName = entityField;
                        }
                    }
                } else {
                    retFieldName = prefix + entityField;
                }
                break;
        }

        return decodeKeyFileName(retFieldName);
    }

    public static List<String> getListByMergeString(SystemListSetting listType, String entityFields) {
        List<String> listFields = new ArrayList<String>();

        try {
            String[] arrEntityFields = entityFields.split(",");
            switch (listType) {
                default:
                    listFields = Arrays.asList(arrEntityFields);
                    break;
                case ImageCompare:
                case BugList: // Equals to EnumDef.DASHBOARD_PAGE.BUGS_OVERVIEW
                    String fieldName = null;
                    for (int i = 0; i < arrEntityFields.length; i++) {
                        fieldName = getListFieldName(listType, arrEntityFields[i]);
                        if (!CommonUtil.isEmpty(fieldName)) {
                            listFields.add(fieldName);
                        }
                    }
                    break;
            }

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
        for (Integer item : data) {
            sb.append(item).append(",");
        }

        String retData = sb.toString();
        if (!isEmpty(retData)) {
            retData = retData.substring(0, retData.length() - 1);
        }

        return retData;
    }

    /**
     * @return boolean
     * @Description - Approximate string match
     * @Date - 2019/7/26
     * @Param - [text - The String you are going to match
     * regex - The regex you have set]
     */
    public static boolean match(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public static String getJSONLocaleValue(String value, String locale) {
        String retValue = "";
        if (isEmpty(value)) return retValue;

        try {
            String strLocale = locale;
            if (isEmpty(locale)) {
                strLocale = Locales.EN_US.toString();
            }
            JSONObject jsonLabel = new JSONObject(value);
            retValue = jsonLabel.get(strLocale.toLowerCase()).toString();
        } catch (Exception jsonE) {
            jsonE.printStackTrace();
        }

        return retValue;
    }

    public static synchronized Map<String, Object> setMapKeyValue(Map<String, Object> data, String key, Object value) {
        Map<String, Object> retMap = data;
        try {
            if (retMap == null || retMap.isEmpty()) {
                return retMap;
            }

            if (key.contains(".")) {
                int nFirstIdx = key.indexOf(".");
                String firstKey = key.substring(0, nFirstIdx);
                String newKey = key.substring(nFirstIdx + 1);

                Object objData = data.get(firstKey);
                if (objData instanceof Map) {
                    Map<String, Object> mapSubData = (Map<String, Object>) objData;
                    mapSubData = setMapKeyValue(mapSubData, newKey, value);
                    data.put(firstKey, mapSubData);
                }
            } else if (data.containsKey(key)) {
                data.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retMap;
    }

    public static synchronized Object getRecursiveKeyValue(Map<String, Object> data, String key) {
        Object retObj = null;
        try {
            if (data == null || data.isEmpty()) {
                return retObj;
            }

            if (key.contains(".")) {
                int nFirstIdx = key.indexOf(".");
                String firstKey = key.substring(0, nFirstIdx);
                String newKey = key.substring(nFirstIdx + 1);

                Object objData = data.get(firstKey);
                if (objData instanceof Map) {
                    retObj = getRecursiveKeyValue((Map<String, Object>) objData, newKey);
                }
            } else if (data.containsKey(key)) {
                retObj = data.get(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retObj;
    }

    public static String getPageChartTitle(OpsPage page, ChartsType chart, String locale) {
        String key = String.format("%s.%s.title.%s", page.getChartPrefix(), chart.getValue(), locale);
        return WebUtil.getItemValue(getPageChartKeyValue(key));
    }

    public static Object getPageChartKeyValue(String key) {
        Map<String, Object> mapChartResource = null;
        if (mapChartResource == null) {
            mapChartResource = FileUtil.ReadResourceJsonFile("/chart_resource.json");
        }

        Object retObj;
        if (CommonUtil.isEmpty(mapChartResource)) {
            System.out.println(ErrorConst.getWarningLogMsg("CommonUtil.getPageChartKeyValue()", "Could not find chart resource."));
            return "";
        } else {
            retObj = getRecursiveKeyValue(mapChartResource, key);
        }

        return retObj;
    }

    public static List<String> getListString(List<Object> data) {
        List<String> listData = new ArrayList<>();

        try {
            for (Object obj : data) {
                listData.add(obj.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public static List<Integer> getListInteger(List<Object> data) {
        List<Integer> listData = new ArrayList<>();

        try {
            for (Object obj : data) {
                listData.add(Integer.parseInt(obj.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public static String[] convertToStringArray(List<Object> data) {
        String[] retArray = {};
        List<String> listData = getListString(data);
        if (!isEmpty(listData)) {
            retArray = listData.toArray(new String[listData.size()]);
        }
        return retArray;
    }

    public static Integer[] convertToIntArray(List<Object> data) {
        Integer[] retArray = {};
        List<Integer> listData = getListInteger(data);
        if (!isEmpty(listData)) {
            retArray = listData.toArray(new Integer[listData.size()]);
        }
        return retArray;
    }

    public static <T> T[] convertToArray(Class<T> type, List<Object> data) {
        if (isEmpty(data)) {
            return null;
        }

        //T[] retArray = (T[]) new Object[data.size()];
        T[] retArray = (T[]) Array.newInstance(type, data.size());

        try {
            List<T> newData = new ArrayList<T>();
            for (Object item : data) {
                newData.add((T) item);
            }
            newData.toArray(retArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retArray;
    }

}
