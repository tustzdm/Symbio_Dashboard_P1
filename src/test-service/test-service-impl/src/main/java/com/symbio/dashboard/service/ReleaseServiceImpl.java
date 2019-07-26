package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.repository.ReleaseRep;
import com.symbio.dashboard.data.repository.ResultMessageRep;
import com.symbio.dashboard.data.repository.SysListSettingRep;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.Release;
import com.symbio.dashboard.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName - ReleaseServiceImpl
 * @Author - admin
 * @Description - TODO
 * @Date - 2019/7/26 10:38
 * @Version 1.0
 */

@Service
@SuppressWarnings("unchecked")
public class ReleaseServiceImpl implements ReleaseService {

    private static Logger logger = LoggerFactory.getLogger(ReleaseServiceImpl.class);

    @Autowired
    private SysListSettingRep sysListSettingRep;
    @Autowired
    private ReleaseRep releaseRep;
    @Autowired
    private ResultMessageRep resultMessageRep;


    @Override
    public Result getReleaseList(Integer userId, String locale) {
        return null;
    }

    @Override
    public Result getReleaseList(Integer userId) {
        return getReleaseList(userId, Locales.EN_US.toString());
    }

    @Override
    public Result getReleasePageList(Integer userId, String locale, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public Result getReleasePageList(Integer userId, int pageIndex, int pageSize) {
        return getReleasePageList(userId, Locales.EN_US.toString(), pageIndex, pageSize);
    }

    @Override
    public Result getReleaseInfo(Integer userId, Integer id) {
        Release release;
        try {
            release = releaseRep.getById(id);
            if (release == null || "".equals(release)) {
                return new Result("000120", "Release Info is empty");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("200102", "Get Release Info Failed");
        }
        return new Result(release);
    }

    @Override
    public Result updateRelease(Release releaseInfo) {

        Result result;
        Release release;
        Integer id = releaseInfo.getId();

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String time = simpleDateFormat.format(date);
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            // If id is null, add new Product
            if (id == null) {
                result = verifyInfo(releaseInfo);
                if (result.hasError()) {
                    return result;
                }
                release = new Release(0, 1);

                release.setCreateTime(date);
                release.setCreateUser(releaseInfo.getCreateUser());
                release.setCreateUserName(releaseInfo.getCreateUserName());

            } else {
                // Get existed Product object
                release = releaseRep.getById(id);
                if (!releaseInfo.getName().equalsIgnoreCase(release.getName())) {
                    result = verifyInfo(releaseInfo);
                    if (result.hasError()) {
                        return result;
                    }
                }
            }

            release.setProductId(releaseInfo.getProductId());
            release.setName(releaseInfo.getName());

            release.setUpdateTime(date);
            release.setUpdateUser(releaseInfo.getUpdateUser());
            release.setUpdateUserName(releaseInfo.getUpdateUserName());

            int flag = 0;
            try {
                // Save or update
                releaseRep.saveAndFlush(release);
                if (id == null) {
                    flag++;
                }
                flag++;
            } catch (Exception e) {
                e.printStackTrace();
                return new Result("200110", "Release info cannot be saved");
            }

            if (flag == 1) {
                result = new Result("Release Updated");
            } else if (flag == 2) {
                result = new Result("Release Added");
            } else {
                return new Result("000002", "Error at flag" + flag);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result("000102", "Edit/Add Release error");
        }
        return result;
    }

    @Override
    public Result removeRelease(Integer id) {
        try {
            Release release = releaseRep.getById(id);
            release.setDisplay(0);
            releaseRep.saveAndFlush(release);
        } catch (Exception e) {
            e.printStackTrace();
            if (CommonUtil.match(e.getMessage(), "(No|entity|id|exists)")) {
                return new Result("000122", "Id does not exist");
            }
        }
        return new Result("Release Removed");
    }

    private Result verifyInfo(Release releaseInfo) {

        if (releaseInfo.getName() == null) {
            return new Result("000101", "Name cannot be empty");
        }
        if (releaseInfo.getProductId() == null) {
            return new Result("000101", "Release Id cannot be empty");
        }

        List<String> allNames = releaseRep.getAllNamesByProductId(releaseInfo.getProductId());
        if (allNames != null && !allNames.isEmpty()) {
            for (String name : allNames) {
                if (releaseInfo.getName().equalsIgnoreCase(name)) {
                    return new Result("000123", "Release name already exists");
                }
            }
        }

        return new Result("Info verified");
    }
}
