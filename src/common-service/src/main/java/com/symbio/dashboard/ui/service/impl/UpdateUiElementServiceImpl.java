package com.symbio.dashboard.ui.service.impl;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.model.UiInfo;
import com.symbio.dashboard.repository.UiInfoRepository;
import com.symbio.dashboard.ui.dto.upload.UiInfoUpload;
import com.symbio.dashboard.ui.service.UpdateUiElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName - UpdateUiElementServiceImpl
 * @Author - Danny
 * @Description - 更新或添加页面元素接口实现类
 * @Date - 2019/7/5 18:13
 * @Version 1.0
 */

@Service
public class UpdateUiElementServiceImpl implements UpdateUiElementService {

    @Autowired
    private UiInfoRepository uiInfoRepository;

    @Override
    public Result updateUiElement(UiInfoUpload uiInfoUpload) {
        return updateUiElementResult(uiInfoUpload);
    }

    /**
     * @return com.symbio.dashboard.Result
     * @Author - Danny
     * @Description - 添加或更新页面元素信息
     * @Date - 2019/7/9
     * @Param - [uiInfoUpload]
     */
    private Result updateUiElementResult(UiInfoUpload uiInfoUpload) {

        Result result = new Result();
        Integer id = uiInfoUpload.getId();

        try {
            UiInfo uiInfo;

            // 如果id为空，则视为添加新元素
            if (id == null) {
                uiInfo = new UiInfo();
                uiInfo.setPage(uiInfoUpload.getPage());
                if (uiInfoUpload.getDbField() == null) {
                    return new Result("100010", "Value of db_field cannot be empty, save failed");
                }

                uiInfo.setDispStatus(1); // user-defined
                uiInfo.setValidation(1);
                uiInfo.setDisplay(1);

            } else {
                // 获取已有元素对象
                uiInfo = uiInfoRepository.getOne(id);
                uiInfo.setDisplay(uiInfoUpload.getDisplay());
            }

            uiInfo.setKey(uiInfoUpload.getKey());
            uiInfo.setType(uiInfoUpload.getType());
            uiInfo.setDbField(uiInfoUpload.getDbField());
            uiInfo.setData(uiInfoUpload.getData());
            uiInfo.setIsRequired(uiInfoUpload.getIsRequired());
            uiInfo.setIsDisable(uiInfoUpload.getIsDisable());
            uiInfo.setEnUs(uiInfoUpload.getEnUs());
            uiInfo.setZhCn(uiInfoUpload.getZhCn());
            uiInfo.setPlaceHolder(uiInfoUpload.getPlaceHolder());
            //uiInfo.setLabel(uiInfoUpload.getLabel());
            uiInfo.setDefaultValue(uiInfoUpload.getDefaultValue());
            uiInfo.setConstCondition(uiInfoUpload.getConstCondition());
            uiInfo.setIdx(uiInfoUpload.getIdx());
            //uiInfo.setVersion(uiInfoUpload.getVersion());
            //uiInfo.setValidation(uiInfoUpload.getValidation());
            //uiInfo.setDisplay(uiInfoUpload.getDisplay());

            int flag = 0;
            try {
                // Save or update
                uiInfoRepository.saveAndFlush(uiInfo);
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
