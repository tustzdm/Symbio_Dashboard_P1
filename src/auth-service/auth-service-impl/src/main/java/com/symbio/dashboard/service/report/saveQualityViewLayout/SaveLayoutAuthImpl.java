package com.symbio.dashboard.service.report.saveQualityViewLayout;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.service.report.saveQualityViewLayout.SaveLayoutAuth;

public class SaveLayoutAuthImpl implements SaveLayoutAuth {

    @Override
    public Result getRoleSaveLayout(String token) {
        return getRoleSaveLayoutResult(token);
    }

    /**
     * 此方法用于调用通过token获得权限的方法
     * @param token 上送的token
     * @return 返回一个结果集
     */
    private Result getRoleSaveLayoutResult(String token) {
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
        int id = 1;
        result.setCdAndRightEcAndEm(id);
        return result;
    }
}
