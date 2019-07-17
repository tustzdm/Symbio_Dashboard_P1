package com.symbio.dashboard.common;

import com.symbio.dashboard.Result;
import org.springframework.stereotype.Service;

@Service
public interface CommonAuthService {
    Result getPageNamesDictionary(String token);
}
