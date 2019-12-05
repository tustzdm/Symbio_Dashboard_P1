package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.bean.ListQueryVO;
import com.symbio.dashboard.bean.NavigatorQueryVO;
import com.symbio.dashboard.data.dao.BugReportDao;
import com.symbio.dashboard.data.dao.CommonDao;
import com.symbio.dashboard.data.service.DataCommonService;
import com.symbio.dashboard.data.utils.SQLUtils;
import com.symbio.dashboard.dto.CommonListDTO;
import com.symbio.dashboard.enums.EnumDef;
import com.symbio.dashboard.util.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    @Autowired
    private DataCommonService dataCommService;

    @Override
    public Result getList(Integer userId, ListQueryVO query) {

        Result<CommonListDTO> resultBugList = bugDao.getList(userId, query);
        if (resultBugList.hasError()) {
            return resultBugList;
        }

        return resultBugList;
    }

    @Override
    public Result getPieChartData(Integer userId, ListQueryVO query) {

        String strFields = "priority,count";
        NavigatorQueryVO queryVO = new NavigatorQueryVO(strFields, query);
        String sql = SQLUtils.buildSql(EnumDef.CHARTS.BUGS_PIE, queryVO);

        Result<List<Map<String, Object>>> resultQuery = dataCommService.executeSqlClause(sql, strFields);
        if (resultQuery.hasError()) {
            return resultQuery;
        }

        List<Map<String, Object>> listData = resultQuery.getCd();
        Map<String, List<Object>> mapData = EntityUtils.toChartData(listData, strFields);
        return new Result(mapData);

    }

    @Override
    public Result getBarChartData(Integer userId, ListQueryVO query) {
        return null;
    }
}
