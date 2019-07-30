package com.symbio.dashboard.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.dao.ReleaseDao;
import com.symbio.dashboard.data.dao.UserDao;
import com.symbio.dashboard.data.repository.ReleaseRep;
import com.symbio.dashboard.data.repository.ResultMessageRep;
import com.symbio.dashboard.data.repository.SysListSettingRep;
import com.symbio.dashboard.data.repository.UserRep;
import com.symbio.dashboard.enums.EntityDisplay;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.Release;
import com.symbio.dashboard.model.User;
import com.symbio.dashboard.util.CommonUtil;
import com.symbio.dashboard.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    @Autowired
    private ReleaseDao releaseDao;

    @Autowired
    private UserRep userRep;

    @Autowired
    private UserDao userDao;

    @Override
    public Result getReleaseList(Integer productId, Integer pageIndex, Integer pageSize) {
        return getReleaseList(null, Locales.EN_US.toString(), productId, pageIndex, pageSize);
    }

    @Override
    public Result getReleaseList(Integer userId, String locale, Integer productId, Integer pageIndex, Integer pageSize) {
        Result retResult = releaseDao.getReleaseList(locale, productId, pageIndex, pageSize);
        if(retResult.hasError()) {
            logger.info(String.format("ec:%s, em:%s", retResult.getEc(), retResult.getEm()));
        }
        return retResult;
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
        logger.trace("ReleaseServiceImpl.updateRelease() Enter");

        Result result;
        Release release;
        String strMsg = null;
        Integer id = releaseInfo.getId();

        try {
            release = releaseInfo;
            Integer userId = release.getUpdateUser();
            User userInfo = userDao.getUserById(userId);

            // If id is null, add new Product
            if (id == null) {
                strMsg = "Added";
                result = verifyInfo(releaseInfo);
                if (result.hasError()) {
                    return result;
                }
                release.setStatus(0); // 0: Pending
                release.setDisplay(EntityDisplay.SHOW.getValue());
                release.setCreateTime(new Date());

                if (userInfo != null) {
                    release.setCreateUser(userInfo.getId());
                    release.setCreateUserName(userInfo.getFullName());
                }
            } else {
                strMsg = "Updated";
                // Get existed Product object
                // ToDo: fetch entity, then set the value from UI
                // release = releaseRep.getById(id);

                if (release.getDisplay() == null) {
                    release.setDisplay(EntityDisplay.SHOW.getValue());
                }
                if (release.getStatus() == null) {
                    release.setStatus(0);
                }

            }
            strMsg = String.format("Release %s", strMsg);
            release.setUpdateTime(new Date());

            if (userInfo != null) {
                release.setUpdateUser(userInfo.getId());
                release.setUpdateUserName(userInfo.getFullName());
            }

            try {
                // Save or update
                releaseRep.saveAndFlush(release);
                result = new Result(strMsg);
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getMessage().contains("release_productid_name")) {
                    result = new Result("000123", "Release Name is duplicated. Name = " + releaseInfo.getName());
                } else {
                    result = new Result("000102", "Exception happened while operation on " + strMsg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("000002", strMsg + " Exception! " + e.getMessage());
        }

        logger.trace("ReleaseServiceImpl.updateRelease() Exit");
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

    @Override
    public Result getReleaseUiInfo(Integer userId, String locale, Integer uiInfo, Integer id) {
        Result result = releaseDao.getReleaseUiInfo(userId, locale, uiInfo, id);
        if (result.hasError()) {
            logger.info(String.format("ec:%s, em:%s", result.getEc(), result.getEm()));
        }
        return result;
    }

    private Result verifyInfo(Release releaseInfo) {

        if (StringUtil.isEmpty(releaseInfo.getName())) {
            return new Result("000101", "Name cannot be empty");
        }
        if (CommonUtil.isEmpty(releaseInfo.getProductId())) {
            return new Result("000101", "Product Id cannot be empty");
        }

        return new Result("Info verified");
    }

    private User getUserById(Integer userId) {
        User retUser = null;
        try {
            retUser = userRep.getOne(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retUser;
    }
}
