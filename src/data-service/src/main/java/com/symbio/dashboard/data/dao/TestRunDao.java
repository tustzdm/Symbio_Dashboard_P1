package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.repository.TestRunRep;
import com.symbio.dashboard.dto.TestRunUiDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @ClassName - TestRunDao
 * @Author - admin
 * @Description - TODO
 * @Date - 2019/8/5 16:53
 * @Version 1.0
 */

@Repository
@SuppressWarnings("unchecked")
public class TestRunDao {

    private static Logger logger = LoggerFactory.getLogger(TestSetDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private TestRunRep testRunRep;


    public Result<TestRunUiDTO> getList() {

        return null;
    }
}


