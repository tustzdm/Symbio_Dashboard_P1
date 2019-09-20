package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.repository.IssueCategoryRep;
import com.symbio.dashboard.data.repository.IssueReasonRep;
import com.symbio.dashboard.model.IssueCategory;
import com.symbio.dashboard.model.IssueReason;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName - IssueDao
 * @Author - admin
 * @Description
 * @Date - 2019/8/28 14:53
 * @Version 1.0
 */

@Repository
@Slf4j
@SuppressWarnings("unchecked")
public class IssueDao {

    @Autowired
    private IssueCategoryRep issueCategoryRep;

    @Autowired
    private IssueReasonRep issueReasonRep;


    public Result addProductIssue(Integer productId) {
        Result retResult = new Result();
        List<IssueCategory> retCategoryList = new ArrayList<>();
        List<IssueReason> retReasonList = new ArrayList<>();
        int categoryCount = 0;
        int reasonCount = 0;
        try {
            List<IssueCategory> issueCategoryList = issueCategoryRep.getAllDefault();
            if (CommonUtil.isEmpty(issueCategoryList)) {
                log.debug("There is no field to query");
                return retResult;
            }
            for (int i = 0; i < issueCategoryList.size(); i++) {
                IssueCategory issueCategory = new IssueCategory();
                issueCategory.setProductId(productId);
                issueCategory.setCaseType(issueCategoryList.get(i).getCaseType());
                issueCategory.setCategory(issueCategoryList.get(i).getCategory());
                issueCategory.setIdx(issueCategoryList.get(i).getIdx());
                issueCategory = issueCategoryRep.saveAndFlush(issueCategory);
                retCategoryList.add(issueCategory);

                List<IssueReason> issueReasonList = issueReasonRep.getByCategoryId(issueCategoryList.get(i).getId());
                if (!CommonUtil.isEmpty(issueReasonList)) {
                    for (int j = 0; j < issueReasonList.size(); j++) {
                        IssueReason issueReason = new IssueReason();
                        issueReason.setIssueCategoryId(issueReasonList.get(j).getIssueCategoryId());
                        issueReason.setReason(issueReasonList.get(j).getReason());
                        issueReason.setIssueLevel(issueReasonList.get(j).getIssueLevel());
                        issueReason.setIdx(issueReasonList.get(j).getIdx());
                        issueReason = issueReasonRep.saveAndFlush(issueReason);
                        retReasonList.add(issueReason);
                    }
                } else {
                    log.debug("There is no field to query in Issue Reason");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            retResult = new Result("000102", "Product Issue");
        }
        if (!CommonUtil.isEmpty(retCategoryList)) {
            categoryCount = retCategoryList.size();
        }
        if (!CommonUtil.isEmpty(retReasonList)) {
            reasonCount = retReasonList.size();
        }

        retResult.setCd(String.format("Issue Category added count: " + categoryCount + "\n" + "Issue Reason count: " + reasonCount));

        return retResult;
    }
}