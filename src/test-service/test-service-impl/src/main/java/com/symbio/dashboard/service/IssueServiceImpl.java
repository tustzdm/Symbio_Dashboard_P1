package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.dao.IssueDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName - IssueServiceImpl
 * @Author - Shawn
 * @Description
 * @Date - 2019/10/23
 * @Version 1.0
 */

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueDao issueDao;

    @Override
    public Result addProductIssue(Integer productId) {
        return issueDao.addProductIssue(productId);
    }

    @Override
    public Result addProdutIssueCategory(Integer productId) {
        return null;
    }

    @Override
    public Result AddProductIssueInfo(Integer productId) {
        return null;
    }
}
