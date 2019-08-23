package com.symbio.dashboard.service;

import com.symbio.dashboard.data.dao.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName - TestResultServiceImpl
 * @Author - Shawn
 * @Description - TestResult Service
 * @Date - 2019/8/23
 * @Version 1.0
 */

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class TestResultServiceImpl implements TestResultService {

    @Autowired
    private CommonDao commonDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ReleaseDao releaseDao;
    @Autowired
    private TestSetDao testSetDao;
    @Autowired
    private TestRunDao testRunDao;

}
