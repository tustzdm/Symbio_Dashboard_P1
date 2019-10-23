package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public interface IssueService {

    Result addProductIssue(Integer productId);

    Result AddProductIssueInfo(Integer productId);

    Result addProdutIssueCategory(Integer productId);


}
