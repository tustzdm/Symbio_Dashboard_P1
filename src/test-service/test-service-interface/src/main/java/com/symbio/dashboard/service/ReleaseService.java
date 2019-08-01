package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.Release;
import org.springframework.stereotype.Service;

@Service
public interface ReleaseService {

    Result getReleaseList(Integer userId, String locale, Integer productId, Integer pageIndex, Integer pageSize);

    Result getReleaseList(Integer productId, Integer pageIndex, Integer pageSize);

    Result getReleaseInfo(Integer userId, Integer id);

    Result updateRelease(Release release);

    Result removeRelease(Integer id);

    Result getReleaseUiInfo(Integer userId, String locale, Integer uiInfo, Integer id);

    Result getNavigationList(Integer userId, String locale, Integer productId, Integer total);

    Result getReleaseChart(Integer userId, String locale);
}
