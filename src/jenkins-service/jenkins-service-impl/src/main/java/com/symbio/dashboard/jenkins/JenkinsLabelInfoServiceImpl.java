package com.symbio.dashboard.jenkins;

import com.symbio.dashboard.data.dao.CommonDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class JenkinsLabelInfoServiceImpl implements JenkinsLabelInfoService {

    @Autowired
    private CommonDao commonDao;

}
