package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;

import java.util.Map;

public interface ZipProcessService {

    Result<Map<String, Object>> unZipFile(String fileName);
}
