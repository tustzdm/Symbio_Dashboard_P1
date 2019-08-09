package com.symbio.dashboard.constant;

import java.util.Map;

public class ProjectConfigConst {
    // properties path
    public static Integer CommonList_Data_Format = 2;

    public static void syncupConfig(Map<String, Object> mapData) {

        if (mapData == null || mapData.isEmpty()) {
            return;
        }

        Object objValue = mapData.get(ProjectConst.COMMONLIST_DATA_FORMAT);
        if (objValue != null) {
            CommonList_Data_Format = Integer.parseInt(objValue.toString());
        }
    }
}
