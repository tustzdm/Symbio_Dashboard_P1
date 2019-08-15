package com.symbio.dashboard.business;

import com.symbio.dashboard.bean.JenkinsJobArgs;
import com.symbio.dashboard.enums.JenkinsParameter;
import com.symbio.dashboard.model.JenkinsJobParameter;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.WebUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JenkinsJobArgsFactory {

    /**
     * Get choice data
     *
     * @param jjp
     * @param choiceSymbol
     * @return
     */
    private static List<String> getChoiceListData(JenkinsJobParameter jjp, String choiceSymbol) {
        List<String> retList = new ArrayList<>();

        String type = jjp.getRefType();
        String content = jjp.getChoiceContent();
        if (!JenkinsParameter.ChoiceType.toString().equals(type) || CommonUtil.isEmpty(content)) {
            return retList;
        }

        try {
            String symbol = (choiceSymbol == null) ? "," : choiceSymbol.trim();
            String[] arrContent = content.split(symbol);
            for (String item : arrContent) {
                retList.add(item.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retList;
    }

    /**
     * Get JJA bean
     *
     * @param jjp
     * @param choiceSymbol
     * @return
     */
    public static JenkinsJobArgs createNewJJA(JenkinsJobParameter jjp, String choiceSymbol) {
        JenkinsJobArgs jja = new JenkinsJobArgs();

        jja.setId(jjp.getId());
        jja.setName(jjp.getParamName());
        jja.setType(jjp.getRefType());
        jja.setDefaultValue(WebUtil.getItemValue(jjp.getDefaultValue()));
        jja.setDescription(WebUtil.getItemValue(jjp.getDescription()));
        if (JenkinsParameter.ChoiceType.toString().equals(jja.getType())) {
            //List<String> listChoice = getChoiceListData(jjp, choiceSymbol);
            //jja.setChoiceList(listChoice.toArray(new String[listChoice.size()]));
            jja.setChoiceList(getChoiceListData(jjp, choiceSymbol));
        }
        jja.setLastRunValue(WebUtil.getItemValue(jjp.getLastRunValue()));

        return jja;
    }

    /**
     * JJP -> JJA
     *
     * @param data
     * @return
     */
    public static List<JenkinsJobArgs> convertToJJA(List<JenkinsJobParameter> data, String choiceSymbol) {
        List<JenkinsJobArgs> retList = new ArrayList<>();

        for (JenkinsJobParameter item : data) {
            retList.add(createNewJJA(item, choiceSymbol));
        }

        return retList;
    }

    private static String getActualRunValue(String runValue, String defaultValue) {
        if (CommonUtil.isEmpty(runValue)) {
            return defaultValue;
        } else {
            return runValue;
        }
    }

    /**
     * Get job run Map
     *
     * @param data
     * @return
     */
    public static Map<String, String> buildRunMap(List<JenkinsJobArgs> data, String jobToken) {
        Map<String, String> map = new HashMap<>();

        for (JenkinsJobArgs item : data) {
            String key = item.getName();
            String type = item.getType();
            String runValue = item.getLastRunValue();
            String defaultValue = item.getDefaultValue();

            if (!JenkinsParameter.FileType.toString().equals(type)) {
                map.put(key, getActualRunValue(runValue, defaultValue));
            } else {
                // File Type
                map.put(key, getActualRunValue(runValue, defaultValue));
            }
        }

        // Append job token
        if (!CommonUtil.isEmpty(jobToken)) {
            map.put("token", jobToken);
        }

        return map;
    }
}
