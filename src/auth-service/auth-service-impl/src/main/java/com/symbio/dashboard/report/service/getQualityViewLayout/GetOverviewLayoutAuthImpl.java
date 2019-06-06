package com.symbio.dashboard.report.service.getQualityViewLayout;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.report.service.getQualityViewLayout.GetOverviewLayoutAuth;

public class GetOverviewLayoutAuthImpl implements GetOverviewLayoutAuth {
    @Override
    public Result getLayoutAuth(String token) {
        return getRoleOverviewLayout(token);
    }

    /**
     * 此方法通过调用获得权限的方法，通过token得到相应的内容
     * @param token 上送的token
     * @return 返回一个结果集
     */
    private Result getRoleOverviewLayout(String token) {
        Result result = setRole(token);
        return result;
    }


    /**
     * 此方法是用于通过token获得一个相应的role并返回
     * @param token 上送的token
     * @return cd中装的是权限的map
     */
    private Result setRole(String token){
        Result result = new Result();

        Integer role = 1;

        result.setCdAndRightEcAndEm(role);
        return result;
    }
}
