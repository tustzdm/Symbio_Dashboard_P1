package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.ListQueryVO;
import org.springframework.stereotype.Service;

@Service
public interface BugService {

//    Result<ResultReviewUiDTO> getList(Integer userId, String locale, Integer testRunId, String trLocale, Integer pageIndex, Integer pageSize);
//
//    Result changeScreenShotStatus(Integer userId, String locale, Integer screenShotId, String screenShotStatus);
//
//    Result<BugInfo> saveBugInfo(Integer userId, String locale, BugInfo data);

    Result getList(Integer userId, ListQueryVO query);

    Result getPieChartData(Integer userId, ListQueryVO query);

    Result getBarChartData(Integer userId, ListQueryVO query);

}
