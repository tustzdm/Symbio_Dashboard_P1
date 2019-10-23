package com.symbio.dashboard.business;

import com.symbio.dashboard.model.IssueCategory;
import com.symbio.dashboard.model.IssueReason;
import com.symbio.dashboard.util.CommonUtil;

import java.util.List;

public class IssueFactory {

    public static IssueReason cloneNewIssueRease(Integer newId, IssueReason reason) {
        IssueReason newReason = new IssueReason();

        newReason.setIssueCategoryId(newId);
        newReason.setReason(reason.getReason());
        newReason.setIssueLevel(reason.getIssueLevel());
        newReason.setIdx(reason.getIdx());
        newReason.setDescription(reason.getDescription());
        newReason.setValidation(1);

        return newReason;
    }


    public static boolean isFindSimilarIssueCategory(IssueCategory base, IssueCategory current) {
        boolean bFind = false;

        if (CommonUtil.isEmpty(base) || CommonUtil.isEmpty(current)) {
            return bFind;
        }

        if (base.getCaseType() == current.getCaseType() && base.getCategory().equals(current.getCategory())) {
            bFind = true;
        }

        return bFind;
    }

    private static Integer getAppropriateCategoryId(IssueCategory base, List<IssueCategory> data) {
        Integer retId = null;

        try {
            for (IssueCategory item : data) {
                if (isFindSimilarIssueCategory(base, item)) {
                    retId = item.getId();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retId;
    }

    public static Integer findAppropriateCategoryId(Integer id, List<IssueCategory> common, List<IssueCategory> data) {
        Integer newCategoryId = null;

        try {
            for (IssueCategory commItem : common) {
                if (commItem.getId() == id) {
                    newCategoryId = getAppropriateCategoryId(commItem, data);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newCategoryId;
    }
}
