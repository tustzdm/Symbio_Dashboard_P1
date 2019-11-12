package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.data.repository.IssueCategoryRep;
import com.symbio.dashboard.data.repository.IssueReasonRep;
import com.symbio.dashboard.model.IssueCategory;
import com.symbio.dashboard.model.IssueReason;
import com.symbio.dashboard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Slf4j
@Repository
public class IssueDao {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private IssueCategoryRep issueCategoryRep;
  @Autowired
  private IssueReasonRep issueReasonRep;

  public List<IssueCategory> getIssueCategoryListByTestSetId(Integer testSetId) {
    List<IssueCategory> retList = issueCategoryRep.getListByTestSetId(testSetId);
    if (CommonUtil.isEmpty(retList)) {
      retList = new ArrayList<>();
    }
    return retList;
  }

  public List<IssueReason> getIssueReasonListByTestSetId(Integer testSetId) {
    List<IssueReason> retList = issueReasonRep.getListByTestSetId(testSetId);
    if (CommonUtil.isEmpty(retList)) {
      retList = new ArrayList<>();
    }
    return retList;
  }

  public List<Map<String, Object>> getIssueCategoryUiListByTestSetId(Integer testSetId) {
    List<Map<String, Object>> retList = new ArrayList<>();
    List<IssueCategory> listIssueCategory = getIssueCategoryListByTestSetId(testSetId);

    Map<String, Object> mapData = null;
    for (IssueCategory ic : listIssueCategory) {
      mapData = new HashMap<>();

      mapData.put("id", ic.getId());
      mapData.put("value", ic.getCategory());
      retList.add(mapData);
    }

    return retList;
  }

  public List<Map<String, Object>> getIssueReasonUiListByTestSetId(Integer testSetId) {
    List<Map<String, Object>> retList = new ArrayList<>();
    List<IssueReason> listIssueReason = getIssueReasonListByTestSetId(testSetId);

    Map<String, Object> mapData = null;
    for (IssueReason ir : listIssueReason) {
      mapData = new HashMap<>();

      mapData.put("id", ir.getId());
      mapData.put("categoryId", ir.getIssueCategoryId());
      mapData.put("value", ir.getReason());
      retList.add(mapData);
    }

    return retList;
  }

}
