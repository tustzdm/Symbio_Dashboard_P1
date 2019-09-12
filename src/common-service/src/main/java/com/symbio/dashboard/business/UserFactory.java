package com.symbio.dashboard.business;

import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.RoleType;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.CommonUtil;

import java.util.Date;

public class UserFactory {

    public static User createNewUser(String locale, User user) {
        if (CommonUtil.isEmpty(user.getLocale())) {
            user.setLocale(locale);
        }

        if (!CommonUtil.isEmpty(user.getFirstName()) && !CommonUtil.isEmpty(user.getLastName())) {
            user.setFullName(String.format("%s %s", user.getFirstName(), user.getLastName()));
        }

        user.setStatus(EnumDef.USER_STATUS.ACTIVE.getCode());
        user.setDisable(EnumDef.ENTITY_BOOL.NO.getCode());

        if (CommonUtil.isEmpty(user.getChannel())) {
            user.setChannel(EnumDef.USER_CHANNEL.LOCAL.getCode());
        }

        user.setLevelId(RoleType.USER.getCode());
        user.setCreateTime(new Date());
        return user;
    }


}
