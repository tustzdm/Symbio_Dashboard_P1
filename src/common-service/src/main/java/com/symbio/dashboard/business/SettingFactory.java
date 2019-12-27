package com.symbio.dashboard.business;

import com.symbio.dashboard.model.FunctionInfo;
import com.symbio.dashboard.model.Menu;
import com.symbio.dashboard.model.RoleSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingFactory {

    public static List<Map<String, Object>> getRoleSettingListMap(List<RoleSetting> listRole) {
        List<Map<String, Object>> listData = new ArrayList<>();

        Map<String, Object> mapData = new HashMap<>();
        for (RoleSetting item : listRole) {
            mapData = new HashMap<>();
            mapData.put("id", item.getId());
            mapData.put("name", item.getName());
            listData.add(mapData);
        }

        return listData;

    }

    public static List<Map<String, Object>> getMenuListMap(List<Menu> listMenu) {
        List<Map<String, Object>> listData = new ArrayList<>();

        Map<String, Object> mapData = new HashMap<>();
        for (Menu item : listMenu) {
            mapData = new HashMap<>();
            mapData.put("id", item.getId());
            mapData.put("name", item.getName());
            mapData.put("group", item.getGroupName());
            listData.add(mapData);
        }

        return listData;

    }

    public static List<Map<String, Object>> getFunctionListMap(List<FunctionInfo> listFunc) {
        List<Map<String, Object>> listData = new ArrayList<>();

        Map<String, Object> mapData = new HashMap<>();
        for (FunctionInfo item : listFunc) {
            mapData = new HashMap<>();
            mapData.put("name", item.getName());
            mapData.put("value", item.getFvalue());
            listData.add(mapData);
        }
        return listData;

    }
}
