package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.ListQueryVO;
import com.symbio.dashboard.data.dao.BugReportDao;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.dto.CommonListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName - BugServiceImpl
 * @Description - Bug Service
 * @Date - 2019/11/29
 * @Version 1.0
 */

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class BugServiceImpl implements BugService {

    @Autowired
    private CommonDao commonDao;
    @Autowired
    private BugReportDao bugDao;


    @Override
    public Result getList(Integer userId, ListQueryVO query) {
        Result<CommonListDTO> retResult = new Result<>();

        CommonListDTO bugListDTO = new CommonListDTO(query.getLocale(), query.getPageIndex(), query.getPageSize());

        Result<CommonListDTO> resultBugList = bugDao.getList(userId, query);

        retResult.setCd(bugListDTO);

        return retResult;
    }


}
