package com.symbio.dashboard.monitor.service.impl;

import com.symbio.dashboard.service.FileServiceImpl;
import com.symbio.dashboard.service.ZipProcessServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProcessTestResultServiceImpl {

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ZipProcessServiceImpl zipService;

}
