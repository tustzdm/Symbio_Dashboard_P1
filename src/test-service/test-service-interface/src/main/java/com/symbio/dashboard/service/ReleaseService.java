package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.Release;
import org.springframework.stereotype.Service;

@Service
public interface ReleaseService {

    Result getReleaseList(Integer userId, String locale);

    Result getReleaseList(Integer userId);

    Result getReleasePageList(Integer userId, String locale, int pageIndex, int pageSize);

    Result getReleasePageList(Integer userId, int pageIndex, int pageSize);

    Result getReleaseInfo(Integer userId, Integer id);

    Result updateRelease(Release release);

    Result removeRelease(Integer id);
}
