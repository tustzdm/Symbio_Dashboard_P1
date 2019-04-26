package com.symbio.dashboard.service.report.getQualityView;

import com.symbio.dashboard.Result;

import java.util.HashMap;
import java.util.Map;

public class GetOverviewAuthImpl implements GetOverviewAuth {
    @Override
    public Result getOverview(String token) {
        return getRoleOverview(token);
    }

    /**
     * 此方法用于调用通过token获得权限的方法
     * @param token 上送的token
     * @return 返回一个结果集
     */
    private Result getRoleOverview(String token) {
        Result result = setRole(token);
        return result;
    }

    /**
     * 此方法用于通过token获得相应的权限角色
     * @param token 上送的token
     * @return cd中的对象是权限值
     */
    private Result setRole(String token) {
        Result result = new Result();
        Map<String,Integer> role = new HashMap<>();
        role.put("product",7);
        role.put("release",7);
        role.put("testset", 7);

        result.setCdAndRightEcAndEm(role);
        return result;
    }
}
