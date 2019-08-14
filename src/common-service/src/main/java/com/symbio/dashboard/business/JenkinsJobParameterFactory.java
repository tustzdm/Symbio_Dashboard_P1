package com.symbio.dashboard.business;

import com.symbio.dashboard.bean.JenkinsBean;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.JenkinsParameter;
import com.symbio.dashboard.model.JenkinsJobParameter;
import com.symbio.dashboard.model.User;

import java.util.Date;

public class JenkinsJobParameterFactory {

    public static JenkinsJobParameter createNewJJP(User user, Integer jsiId, JenkinsBean data, Integer index) {
        JenkinsJobParameter jjp = new JenkinsJobParameter();

        jjp.setJsiId(jsiId);
        jjp.setParamName(data.getName());
        jjp.setRefType(data.getType());
        jjp.setDefaultValue(data.getDefaultValue());
        jjp.setDescription(data.getDescription());
        if (JenkinsParameter.ChoiceType.toString().equals(data.getType())) {
            jjp.setChoiceContent(data.getDefaultValue().replace("\n", ","));
            jjp.setDefaultValue(data.getDefaultValue().split("\n")[0]);
        }
        jjp.setIdx(index);
        // JJP default display mode. 0-not display, 1-display
        jjp.setDisplayMode(1);
        jjp.setValidation(EnumDef.ENTITY_VALIDATION.VALID.getCode());

        jjp.setCreateTime(new Date());
        jjp.setCreateUserId(user.getId());
        jjp.setCreateUserName(user.getFullName());

        return jjp;
    }

    public static void main(String[] args) {
        String test = "value1\nvalue2\nvalue3";

        System.out.println(test.replace("\n", ","));
    }

}
