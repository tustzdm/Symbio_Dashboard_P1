package com.symbio.dashboard.data.dao;

import com.symbio.dashboard.data.repository.TestSetRep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @ClassName - TestSetDao
 * @Author - admin
 * @Description - TODO
 * @Date - 2019/7/26 15:33
 * @Version 1.0
 */
public class TestSetDao {

    private static Logger logger = LoggerFactory.getLogger(TestSetDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TestSetRep testSetRep;
}
