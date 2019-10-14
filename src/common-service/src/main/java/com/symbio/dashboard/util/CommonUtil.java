package com.symbio.dashboard.util;

import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.enums.SystemListSetting;
import org.json.JSONObject;

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

    @Deprecated
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

        if ("key,type,status,release,search,data,".contains(name)) {
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

    private static String getListFieldName(SystemListSetting listType, String entityField) {
        String retFieldName = entityField;

        switch (listType) {
            default:
                break;
            case ImageCompare:
//                if ("thumbnailHttpPath,httpFilePath".contains(entityField)) {
//
//                } else {
//
//                }
                if (entityField.contains(".")) {
                    String[] arrField = entityField.split("\\.");
                    if (arrField.length == 2) {
                        retFieldName = arrField[1];
                    }
                } else if ("thumbnailHttpPath,httpFilePath,id,".contains(entityField + ",")) {
                    retFieldName = "";
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
     * @Author - Danny
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
}
