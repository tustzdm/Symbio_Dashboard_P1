package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.data.dao.UserDao;
import com.symbio.dashboard.data.repository.UiInfoRep;
import com.symbio.dashboard.data.repository.UserRep;
import com.symbio.dashboard.enums.Locales;
import com.symbio.dashboard.model.UiInfo;
import com.symbio.dashboard.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName - PageElementServiceImpl
 * @Author - Admin
 * @Description -
 * @Date - 2019/8/1
 * @Version 1.0
 */

@Service
@SuppressWarnings("unchecked")
public class PageElementServiceImpl implements PageElementService {

    private static Logger logger = LoggerFactory.getLogger(PageElementServiceImpl.class);

    @Autowired
    private UiInfoRep uiInfoRep;
    @Autowired
    private UserDao userDao;

    @Override
    public Result getUiInfoList(String locale, String page) {
        return getUiInfoListResult(locale, page);
    }

    /**
     * @return com.symbio.dashboard.Result
     * @Author - Danny
     * @Description - 获取对应页面元素列表
     * @Date - 2019/7/9
     * @Param - page
     */
    private Result getUiInfoListResult(String locale,String page) {
        Result result;

        try {
            // 根据页面查询元素
            List<UiInfo> uiInfoList = uiInfoRep.getUiInfoListByPageName(page);

            if (uiInfoList == null || uiInfoList.isEmpty()) {
                return new Result("100011", "查询失败");
            }

            result = new Result(uiInfoList);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result("100012", "SQL Error");
        }

        return result;
    }

    @Override
    public Result removeUiElement(String locale, Integer id) {
        return removeUiElementResult(locale, id);
    }

    /**
     * @return com.symbio.dashboard.Result
     * @Author - Danny
     * @Description - 根据id删除相应元素
     * @Date - 2019/7/9
     * @Param - [uiInfoUpload]
     */
    private Result removeUiElementResult(String locale, Integer id) {

        try {
            UiInfo uiInfo = uiInfoRep.getOne(id);
            uiInfo.setDisplay(4); // delete
        } catch (Exception e) {
            e.printStackTrace();
            if (match(e.getMessage())) {
                return new Result("100010", "Id does not exist");
            }
        }
        return new Result("Element removed");
    }

    private boolean match(String text) {
        Pattern pattern = Pattern.compile("(No|entity|id|exists)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    @Override
    public Result updateUiElement(Integer userId, String locale, UiInfo uiInfoUpload) {
        return updateUiElementResult(userId, locale, uiInfoUpload);
    }

    /**
     * @return com.symbio.dashboard.Result
     * @Author - Danny
     * @Description - 添加或更新页面元素信息
     * @Date - 2019/7/9
     * @Param - [uiInfoUpload]
     */
    private Result updateUiElementResult(Integer userId, String locale, UiInfo uiInfoUpload) {
        Result result = null;
        Integer id = uiInfoUpload.getId();

        try {
            UiInfo uiInfo = uiInfoUpload;
            User userInfo = userDao.getUserById(userId);

            // 如果id为空，则视为添加新元素
            if (id == null) {
//                uiInfo = new UiInfo();
                uiInfo.setPage(uiInfoUpload.getPage());
                if (uiInfoUpload.getDbField() == null) {
                    return new Result("100010", "Value of db_field cannot be empty, save failed");
                }

                uiInfo.setDispStatus(1); // user-defined
                uiInfo.setValidation(1);
                uiInfo.setDisplay(1);
                uiInfo.setCreateTime(new Date());
                if (userInfo != null) {
                    uiInfo.setCreateUser(userInfo.getId());
                    uiInfo.setCreateUserName(userInfo.getFullName());
                }

            } else {
                // 获取已有元素对象
//                uiInfo = uiInfoRep.getOne(id);
//                uiInfo.setDisplay(uiInfoUpload.getDisplay());
                uiInfo.setValidation(1);
            }

            if (userInfo != null) {
                uiInfo.setUpdateUser(userInfo.getId());
                uiInfo.setUpdateUserName(userInfo.getFullName());
            }

//            uiInfo.setKey(uiInfoUpload.getKey());
//            uiInfo.setType(uiInfoUpload.getType());
//            uiInfo.setDbField(uiInfoUpload.getDbField());
//            uiInfo.setData(uiInfoUpload.getData());
//            uiInfo.setIsRequired(uiInfoUpload.getIsRequired());
//            uiInfo.setIsDisable(uiInfoUpload.getIsDisable());
//            uiInfo.setEnUs(uiInfoUpload.getEnUs());
//            uiInfo.setZhCn(uiInfoUpload.getZhCn());
//            uiInfo.setPlaceHolder(uiInfoUpload.getPlaceHolder());
//            //uiInfo.setLabel(uiInfoUpload.getLabel());
//            uiInfo.setDefaultValue(uiInfoUpload.getDefaultValue());
//            uiInfo.setConstCondition(uiInfoUpload.getConstCondition());
//            uiInfo.setIdx(uiInfoUpload.getIdx());
            //uiInfo.setVersion(uiInfoUpload.getVersion());
            //uiInfo.setValidation(uiInfoUpload.getValidation());
            //uiInfo.setDisplay(uiInfoUpload.getDisplay());

            int flag = 0;
            try {
                // Save or update
                uiInfoRep.saveAndFlush(uiInfo);
                if (id == null) {
                    flag++;
                }
                flag++;
            } catch (Exception e) {
                e.printStackTrace();
                // 同一页面不能存在两个key相同的元素
                if (e.getMessage().contains("uniqe_ui_info_page_key")) {
                    return new Result("123456", "此页面key值相同的元素已存在");
                }
            }

            if (flag == 1) {
                result = new Result("元素信息更新成功");
            } else if (flag == 2) {
                result = new Result("元素添加成功");
            } else {
                return new Result("123456", "没有正确的操作" + flag);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result("123456", "更新或添加异常");
        }

        return result;
    }
}
