package com.symbio.dashboard.business;

import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.enums.GroupType;
import com.symbio.dashboard.enums.RoleType;
import com.symbio.dashboard.model.Group;
import com.symbio.dashboard.util.CommonUtil;

import java.util.Date;

public class AuthorityFactory {

    ///////////////////////////////////////////////////////////////////////////////////
    // Group relevant Authentication
    ///////////////////////////////////////////////////////////////////////////////////
    public static Group createProductGroup(Integer companyId, Integer productId) {
        Group groupInfo = new Group();

        groupInfo.setName(RoleType.PRODUCT_USER.getRoleName());
        groupInfo.setIsSysGroup(EnumDef.ENTITY_BOOL.NO.getCode());
        groupInfo.setGroupType(GroupType.PRODUCT.getCode());
        groupInfo.setCompanyId(CommonUtil.isEmpty(companyId) ? 0 : companyId);
        groupInfo.setProductId(productId);
        groupInfo.setValidation(EnumDef.ENTITY_VALIDATION.VALID.getCode());
        groupInfo.setCreateTime(new Date());

        return groupInfo;
    }

    public static Group createCompanyGroup(Integer companyId) {
        Group groupInfo = new Group();

        groupInfo.setName("CompanyUser");
        groupInfo.setIsSysGroup(EnumDef.ENTITY_BOOL.NO.getCode());
        groupInfo.setGroupType(GroupType.COMPANY.getCode());
        groupInfo.setCompanyId(CommonUtil.isEmpty(companyId) ? 0 : companyId);
        groupInfo.setProductId(0);
        groupInfo.setValidation(EnumDef.ENTITY_VALIDATION.VALID.getCode());
        groupInfo.setCreateTime(new Date());

        return groupInfo;
    }

    public static Group createRoleGroup(RoleType role) {
        Group groupInfo = new Group();

        groupInfo.setName(role.getRoleName());
        groupInfo.setIsSysGroup(EnumDef.ENTITY_BOOL.YES.getCode());
        groupInfo.setGroupType(GroupType.SYSTEM.getCode());
        groupInfo.setCompanyId(0);
        groupInfo.setProductId(0);
        groupInfo.setValidation(EnumDef.ENTITY_VALIDATION.VALID.getCode());
        groupInfo.setCreateTime(new Date());

        return groupInfo;
    }

}
