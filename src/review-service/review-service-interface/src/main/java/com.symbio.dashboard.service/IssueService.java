package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public interface IssueService {

    Result addProductIssue(Integer productId);

    Result addNewCategory(Integer productId, Integer caseType);
}
