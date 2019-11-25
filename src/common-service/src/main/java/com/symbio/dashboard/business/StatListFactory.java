package com.symbio.dashboard.business;

import com.symbio.dashboard.entity.Progress;
import com.symbio.dashboard.model.StatList;
import com.symbio.dashboard.util.CommonUtil;
import org.json.JSONObject;

public class StatListFactory {

    public static Progress getProgress(StatList data) {
        Progress retProgress = new Progress(0, 0);

        try {
            String strValue = getStatListValue(data);
            if (CommonUtil.isEmpty(strValue)) {
                return retProgress;
            } else {
                JSONObject jsonObject = new JSONObject(strValue);
                retProgress = new Progress(jsonObject.getInt("done"), jsonObject.getInt("total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retProgress;
    }

    private static String getStatListValue(StatList data) {
        String retStr = null;

//        try {
//            EnumDef.ENTITY_VALUE_TYPE enumValueType = EnumDef.getEnumTypeByCode(EnumDef.ENTITY_VALUE_TYPE.class, data.getValueType());
//            switch (enumValueType) {
//                default:
//                    break;
//                case INT:
//                    retStr = String.valueOf(data.getValueContent());
//                    break;
//                case JSON:
//                    retStr = data.getValueContent();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        if (data != null) {
            retStr = data.getValueContent();
        }

        return retStr;
    }
}
