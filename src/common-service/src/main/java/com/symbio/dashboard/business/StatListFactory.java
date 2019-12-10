package com.symbio.dashboard.business;

import com.symbio.dashboard.constant.ErrorConst;
import com.symbio.dashboard.entity.Progress;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.SystemListSetting;
import com.symbio.dashboard.model.StatList;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.Date;

@Slf4j
public class StatListFactory {

    public static StatList createStatList(SystemListSetting progressType, Integer fkId, EnumDef.STAT_LIST_FIELD fieldType, String value) {
        StatList data = new StatList();
        String funcName = "StatListFactory.createStatList()";

        Integer nType = 0;
        switch (progressType) {
            default:
                log.warn(ErrorConst.getWarningLogMsg(funcName, "Do not support this Type for the StatList: " + progressType.toString()));
                nType = -1;
                break;
            case Product:
            case Release:
            case TestSet:
                nType = progressType.ordinal();
                break;
        }

        switch (fieldType) {
            default:
                log.warn(ErrorConst.getWarningLogMsg(funcName, "Do not support this field for the StatList: " + fieldType.toString()));
                nType = -1;
                break;
            case PROGRESS:
                data.setField(fieldType.getValue());
                data.setValueType(EnumDef.ENTITY_VALUE_TYPE.JSON.getCode());
                break;
            case TEST_CASE:
                data.setField(fieldType.getValue());
                data.setValueType(EnumDef.ENTITY_VALUE_TYPE.INT.getCode());
                break;
        }

        if (nType == -1) {
            return null;
        }

        data.setTypeCode(nType);
        data.setFkId(fkId);
        data.setValidation(1);
        data.setValueContent(value);
        data.setCreateTime(new Date());

        return data;
    }

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
