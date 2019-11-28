package com.symbio.dashboard.business;

import com.symbio.dashboard.model.BugInfo;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.EntityUtils;

import java.util.Date;
import java.util.List;

public class BugInfoFactory {

    public static BugInfo cloneNewBugInfo(BugInfo data) {
        BugInfo newBugInfo = new BugInfo();

        // Key info
        if (!CommonUtil.isEmpty(data.getId())) {
            newBugInfo.setId(data.getId());
        }
        if (!CommonUtil.isEmpty(data.getTestResultId())) {
            newBugInfo.setTestResultId(data.getTestResultId());
        }
        newBugInfo.setScreenShotId(data.getScreenShotId());

        newBugInfo.setValidation(1);
        newBugInfo.setCreateTime(new Date());

        return newBugInfo;
    }

    public static BugInfo cloneNewBugInfo(BugInfo data, List<String> listField) {
        BugInfo newBugInfo = new BugInfo();

        if (!CommonUtil.isEmpty(data.getId())) {
            newBugInfo.setId(data.getId());
        }
        newBugInfo.setScreenShotId(data.getScreenShotId());

        EntityUtils.clonePropertiesByFieldSpecified(newBugInfo, data, listField);

        newBugInfo.setValidation(1);
        newBugInfo.setCreateTime(new Date());

        return newBugInfo;
    }


}
