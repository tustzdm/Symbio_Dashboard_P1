package com.symbio.dashboard.business;

import com.symbio.dashboard.bean.JenkinsJobArgs;
import com.symbio.dashboard.enums.JenkinsParameter;
import com.symbio.dashboard.model.JenkinsJobParameter;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.WebUtil;

import java.util.ArrayList;
import java.util.List;

public class JenkinsJobArgsFactory {


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

    public static JenkinsJobArgs createNewJJA(JenkinsJobParameter jjp, String choiceSymbol) {
        JenkinsJobArgs jja = new JenkinsJobArgs();

        jja.setId(jjp.getId());
        jja.setName(jjp.getParamName());
        jja.setType(jjp.getRefType());
        jja.setDefaultValue(WebUtil.getItemValue(jjp.getDefaultValue()));
        jja.setDescription(WebUtil.getItemValue(jjp.getDescription()));
        jja.setChoiceList(getChoiceListData(jjp, choiceSymbol));
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
}
