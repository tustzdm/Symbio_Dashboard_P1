package com.symbio.dashboard.business;

import com.symbio.dashboard.dto.CommonListDTO;
import com.symbio.dashboard.enums.ListColumns;
import com.symbio.dashboard.enums.Locales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonListDTOFactory {

    public static CommonListDTO createNewCommonListDTO(String locale, Integer pageIndex, Integer pageSize) {
        CommonListDTO dtoCommonList = new CommonListDTO();

        dtoCommonList.setLocale(locale == null ? Locales.EN_US.toString() : locale);
        dtoCommonList.setPageIndex(pageIndex == null ? 0 : pageIndex);
        dtoCommonList.setPageSize(pageSize == null ? 20 : pageIndex);

        return dtoCommonList;
    }

    // ColumnInfo.Operation
    public static Map<String, Object> createOperationColumnInfo(String locale) {
        Map<String, Object> mapColInfo = new HashMap<String, Object>();

        boolean isSupportColumnDisplay = false;

        mapColInfo.put(ListColumns.KEY.getKey(), "operation");
        String strColLabel = "Operation";
        try {
            if (Locales.EN_US.toString().equals(locale)) {
                mapColInfo.put(ListColumns.LABEL.getKey(), strColLabel);
            } else {
                mapColInfo.put(ListColumns.LABEL.getKey(), "操作");
            }
        } catch (Exception jsonE) {
            jsonE.printStackTrace();
        }
        mapColInfo.put(ListColumns.TYPE.getKey(), "button");
        // Change field to CamelField for UI
        mapColInfo.put(ListColumns.FIELD.getKey(), "operation");
        mapColInfo.put(ListColumns.ALIGN.getKey(), 2);
        mapColInfo.put(ListColumns.FORMAT.getKey(), "");
        if (isSupportColumnDisplay) {
            mapColInfo.put(ListColumns.DISPLAY.getKey(), -1);
        }
        return mapColInfo;
    }

    // data.operation
    public static List<String> createOperationData(Integer role) {
        List<String> listButton = new ArrayList<>();

        if (role == 7) {
            listButton.add("edit");
            listButton.add("remove");
        } else if (role == 3) {
            listButton.add("edit");
        } else {
            // nothing to do
        }

        return listButton;
    }
}
